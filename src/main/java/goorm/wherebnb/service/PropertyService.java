package goorm.wherebnb.service;

import goorm.wherebnb.domain.dto.response.HostResponse;
import goorm.wherebnb.domain.dto.response.PropertyResponse;
import goorm.wherebnb.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.domain.dto.response.ManageYourSpaceResponseDto;
import goorm.wherebnb.repository.PropertyPhotoRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyResponse getProperty(Long propertyId) {
        Property property = propertyRepository.findByPropertyId(propertyId);

        return PropertyResponse.builder()
                .property(property)
                .build();
    }

    private final PropertyPhotoRepository propertyPhotoRepository;
    private final UserRepository userRepository;

    public void createProperty(BecomeAHostRequestDto requestDto, List<MultipartFile> photos) throws IOException {
//        User host = userRepository.findUserByUserId(requestDto.getUserId());
        PropertyDetail detail = PropertyDetail.builder()
                .maxPeople(requestDto.getMaxPeople())
                .bedroom(requestDto.getBedroom())
                .bed(requestDto.getBed())
                .bathroom(requestDto.getBathroom())
                .build();
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

        Property property = Property.builder()
                .host(null)
                .propertyName(requestDto.getPropertyName())
                .propertyType(requestDto.getPropertyType())
                .propertyExplanation(requestDto.getPropertyExplanation())
                .propertyDetail(detail)
                .address(address)
                .price(requestDto.getPrice())
                .amenities(requestDto.getAmenities())
                .build();

        propertyRepository.save(property);

        String path = "/Users/goorm/Downloads/uploadFiles/";
        for (MultipartFile photo : photos) {
            String originalName = photo.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            String savedName = uuid.toString() + "_" + originalName;

            File newFile = new File(path + savedName);
            photo.transferTo(newFile);

            PropertyPhoto propertyPhoto = PropertyPhoto.builder()
//                    .uuid(uuid)
                    .property(property)
                    .originalName(originalName)
                    .savedName(savedName)
                    .path(path)
                    .build();

            propertyPhotoRepository.save(propertyPhoto);
        }
    }

    public ManageYourSpaceResponseDto getPropertyEditor(Long propertyId) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        List<PropertyPhoto> propertyPhoto = propertyPhotoRepository.getPropertyPhotosByProperty(property);
        return ManageYourSpaceResponseDto.builder()
                .status(property.isStatus())
//                .photos(property.getPhotos())
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

//    public void updatePhotos(Long propertyId, List<String> photos) {
//        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
//        property.updatePhotos(photos);
//        this.propertyRepository.save(property);
//    }

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

    public void updateAmenities(Long propertyId, List<String> amenities) {
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
