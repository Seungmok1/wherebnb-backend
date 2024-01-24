package goorm.wherebnb.service;

import lombok.extern.slf4j.Slf4j;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import goorm.wherebnb.domain.dto.response.*;
import goorm.wherebnb.repository.BookingRepository;
import goorm.wherebnb.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
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

@Slf4j
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


    public List<PropertySearchResponse> propertySearch(LocalDate checkInDate, LocalDate checkOutDate) {
     if(checkInDate==null && checkOutDate==null) {
        List<Property> all = propertyRepository.findAll();

        List<PropertySearchResponse> result = all.stream()
                .map(property -> {
                    int reviewCount = property.getReviews().size();
                    double totalScore = property.getReviews().stream()
                            .map(Review::getScore)
                            .mapToDouble(Score::getTotalScore)
                            .average()
                            .orElse(0.0);
                    SearchUserDto searchUserDto = SearchUserDto.builder()
                            .userId(property.getPropertyId())
                            .picture(property.getHost().getPicture())
                            .explanation(property.getHost().getExplanation())
                            .userName(property.getHost().getName())
                            .build();

                    return PropertySearchResponse.builder()
                            .propertyId(property.getPropertyId())
                            .photos(property.getPhotos())
                            .address(property.getAddress())
                            .searchUser(searchUserDto)
                            .price(property.getPrice())
                            .totalScore(totalScore)
                            .reviews(reviewCount)
                            .propertyExplanation(property.getPropertyExplanation())
                            .guestFavorite(property.isGuestFavorite())
                            .build();
                })
                .toList();

        return result;
    }
    List<LocalDate> days = new ArrayList<>();

        for (LocalDate date = checkInDate; date.isBefore(checkOutDate.plusDays(1)); date = date.plusDays(1)) {
        days.add(date);
    }
    List<Property> availableProperties = propertyRepository.getAvailableProperties(days);

    List<PropertySearchResponse> responses = availableProperties.stream()
            .map(property -> {
                int reviewCount = property.getReviews().size();
                double totalScore = property.getReviews().stream()
                        .map(Review::getScore)
                        .mapToDouble(Score::getTotalScore)
                        .average()
                        .orElse(0.0);
                SearchUserDto searchUserDto = SearchUserDto.builder()
                        .userId(property.getPropertyId())
                        .picture(property.getHost().getPicture())
                        .explanation(property.getHost().getExplanation())
                        .userName(property.getHost().getName())
                        .build();


                return PropertySearchResponse.builder()
                        .propertyId(property.getPropertyId())
                        .photos(property.getPhotos())
                        .address(property.getAddress())
                        .searchUser(searchUserDto)
                        .price(property.getPrice())
                        .totalScore(totalScore)
                        .reviews(reviewCount)
                        .propertyExplanation(property.getPropertyExplanation())
                        .guestFavorite(property.isGuestFavorite())
                        .build();
            })
            .collect(Collectors.toList());

        return responses;
}


    public Slice<PropertySearchResponse> propertySearch2(List<PropertySearchResponse> responses, String place, Integer adult, Integer children, Integer infants, Integer pets, Integer price_min, Integer price_max, Integer min_bedrooms, Integer min_beds, Integer min_bathrooms, Boolean guest_favorite, Integer property_type, Integer category, Pageable pageable) {
        adult = adult == null ? 0 : adult;
        children = children == null ? 0 : children;
        infants = infants == null ? 0 : infants;
        pets = pets == null ? 0 : pets;
        price_min = price_min == null ? 0 : price_min;
        price_max = price_max == null ? 1000000 : price_max;
        min_bedrooms = min_bedrooms == null ? 0 : min_bedrooms;
        min_beds = min_beds == null ? 0 : min_beds;
        min_bathrooms = min_bathrooms == null ? 0 : min_bathrooms;

        Integer finalAdult = adult;
        Integer finalChildren = children;
        Integer finalInfants = infants;
        Integer finalPets = pets;
        Integer finalMinPrice = price_min;
        Integer finalMaxPrice = price_max;
        Integer finalBedrooms = min_bedrooms;
        Integer finalBeds = min_beds;
        Integer finalBathrooms = min_bathrooms;

        Stream<PropertySearchResponse> filteredStream = responses.stream()
                .filter(r -> {
                    Property findProperty = propertyRepository.findByPropertyId(r.getPropertyId())
                            .orElseThrow(() -> new EntityNotFoundException("Property not found"));
                    PropertyDetail detail = findProperty.getPropertyDetail();
                    Category categories = findProperty.getCategory();
                    PropertyType propertyType = findProperty.getPropertyType();
                    boolean matchesPeople = detail.getMaxPeople() >= finalAdult + finalChildren + finalInfants;
                    boolean matchesPets = finalPets <= 0 || detail.isPetAvailable();
                    boolean matchesBedrooms = detail.getBedroom() >= finalBedrooms;
                    boolean matchesBeds = detail.getBed() >= finalBeds;
                    boolean matchesBathrooms = detail.getBathroom() >= finalBathrooms;
                    boolean matchesGuestFavorite = (guest_favorite == null) || r.isGuestFavorite() == guest_favorite;
                    boolean matchesPropertyType = (property_type == null) || propertyType.ordinal() == property_type;
                    boolean withinPriceRange = r.getPrice() >= finalMinPrice && r.getPrice() <= finalMaxPrice;
                    boolean matchesCategory = (category == null) || categories.ordinal() == category;

                    return matchesPeople && matchesPets && matchesBedrooms && matchesBeds && matchesBathrooms && withinPriceRange && matchesPropertyType && matchesGuestFavorite && matchesCategory;
                });

        if (place != null) {
            filteredStream = filteredStream.filter(propertyHomeResponse -> {
                Address address = propertyHomeResponse.getAddress();
                return address.getCity().contains(place) || address.getState().contains(place) ||
                        address.getCountry().contains(place) || address.getStreet().contains(place) ||
                        address.getDetails().contains(place);
            });
        }

        List<PropertySearchResponse> filteredList = filteredStream.collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());

        List<PropertySearchResponse> slicedData = filteredList.subList(start, end);
        boolean hasNext = end < filteredList.size();

        return new SliceImpl<>(slicedData, pageable, hasNext);
    }







}



