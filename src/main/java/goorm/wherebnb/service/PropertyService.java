package goorm.wherebnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import goorm.wherebnb.domain.dto.response.PropertyBookingListResponse;
import goorm.wherebnb.domain.dto.response.PropertyResponse;
import goorm.wherebnb.repository.BookingRepository;
import goorm.wherebnb.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.domain.dto.response.HostingListingEditorResponse;
import goorm.wherebnb.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final String bucket = "wherebnb-property-photos";
    private final AmazonS3 amazonS3;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public PropertyResponse getProperty(Long propertyId) {
        Property findProperty = propertyRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        List<PropertyBookingListResponse> bookings = bookingRepository.findByPropertyAndCheckOutDateAfter(findProperty, LocalDate.now()).stream()
                .filter(booking -> !booking.getBookingStatus().equals(BookingStatus.예약실패))
                .map(booking -> PropertyBookingListResponse.builder()
                        .checkInDate(booking.getCheckInDate())
                        .checkOutDate(booking.getCheckOutDate())
                        .build())
                .collect(Collectors.toList());

        return PropertyResponse.builder()
                .property(findProperty)
                .bookings(bookings)
                .build();
    }

    public void createProperty(BecomeAHostRequestDto requestDto, List<MultipartFile> files) throws IOException {
        User host = userRepository.findUserByUserId(requestDto.getUserId());

        Address address = Address.builder()
                .country(requestDto.getCountry())
                .state(requestDto.getState())
                .city(requestDto.getCity())
                .street(requestDto.getStreet())
                .details(requestDto.getDetails())
                .zipcode(requestDto.getZipcode())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();

        PropertyDetail detail = PropertyDetail.builder()
                .maxPeople(requestDto.getMaxPeople())
                .selfCheckIn(requestDto.isSelfCheckIn())
                .petAvailable(requestDto.isPetAvailable())
                .smokeAvailable(requestDto.isSmokeAvailable())
                .checkInTime(requestDto.getCheckInTime())
                .checkOutTime(requestDto.getCheckOutTime())
                .bedroom(requestDto.getBedroom())
                .bed(requestDto.getBed())
                .bathroom(requestDto.getBathroom())
                .build();

        List<String> propertyPhotos = uploadS3(files);

        Property property = Property.builder()
                .host(host)
                .propertyName(requestDto.getPropertyName())
                .propertyType(requestDto.getPropertyType())
                .propertyExplanation(requestDto.getPropertyExplanation())
                .propertyDetail(detail)
                .address(address)
                .price(requestDto.getPrice())
                .photos(propertyPhotos)
                .amenities(requestDto.getAmenities())
                .build();

        propertyRepository.save(property);
    }

    private List<String> uploadS3(List<MultipartFile> files) throws IOException {
        List<String> propertyPhotos = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            File file = convertMultipartFileToFile(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> file convert fail"));

            String key = "property-photos/" + UUID.randomUUID();

            try {
                amazonS3.putObject(new PutObjectRequest(bucket, key, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (Exception e) {
                throw e;
            } finally {
                file.delete();
            }

            propertyPhotos.add(getS3(key));
        }
        return propertyPhotos;
    }

    private String getS3(String key) {
        return amazonS3.getUrl(bucket, key).toString();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public HostingListingEditorResponse getPropertyEditor(Long propertyId) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        return HostingListingEditorResponse.builder()
                .status(property.isStatus())
                .photos(property.getPhotos())
                .propertyName(property.getPropertyName())
                .propertyType(property.getPropertyType())
                .propertyDetail(property.getPropertyDetail())
                .propertyExplanation(property.getPropertyExplanation())
                .amenities(property.getAmenities())
                .address(property.getAddress())
                .hostPicture(property.getHost().getPicture())
                .hostName(property.getHost().getName())
                .build();
    }

    public void updateStatus(Long propertyId, boolean status) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updateStatus(status);
        this.propertyRepository.save(property);
    }

    public void updatePhotos(Long propertyId, List<MultipartFile> files) throws IOException {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        deletePhotos(property);

        List<String> photos = uploadS3(files);
        property.updatePhotos(photos);
        this.propertyRepository.save(property);
    }

    public void deletePhotos(Property property) {
        for (String photo : property.getPhotos()) {
            String[] arr = photo.split("/");
            for (String st : arr) {
                System.out.println(st);
            }
            String key = arr[arr.length - 2] + "/" + arr[arr.length - 1];
            System.out.println("key"+key);
//            if (amazonS3.doesObjectExist(bucket, key)) {
//                amazonS3.deleteObject(bucket, key);
//            }
        }
    }

    public void updatePropertyName(Long propertyId, String propertyName) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePropertyName(propertyName);
        this.propertyRepository.save(property);
    }

    public void updatePropertyType(Long propertyId, PropertyType propertyType) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePropertyType(propertyType);
        this.propertyRepository.save(property);
    }

    public void updatePropertyDetail(Long propertyId, PropertyDetail propertyDetail) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePropertyDetail(propertyDetail);
        this.propertyRepository.save(property);
    }

    public void updatePropertyExplanation(Long propertyId, String propertyExplanation) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePropertyExplanation(propertyExplanation);
        this.propertyRepository.save(property);
    }

    public void updateAmenities(Long propertyId, List<Amenity> amenities) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updateAmenities(amenities);
        this.propertyRepository.save(property);
    }

    public void updateAddress(Long propertyId, Address address) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updateAddress(address);
        this.propertyRepository.save(property);
    }

    public void updatePrice(Long propertyId, int price) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePrice(price);
        this.propertyRepository.save(property);
    }
}
