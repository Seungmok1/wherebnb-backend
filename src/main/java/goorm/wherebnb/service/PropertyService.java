package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.domain.dto.response.ManageYourSpaceResponseDto;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public void createProperty(BecomeAHostRequestDto requestDto) {
        User host = userRepository.findUserByUserId(requestDto.getUserId());
        PropertyDetail detail = PropertyDetail.builder()
                .maxPeople(requestDto.getMaxPeople())
                .bedroom(requestDto.getBedroom())
                .bed(requestDto.getBed())
                .bathroom(requestDto.getBathroom())
                .build();
        Address address = Address.builder()
                .country(requestDto.getCountry())
                .state(requestDto.getState())
                .street(requestDto.getStreet())
                .details(requestDto.getDetails())
                .zipcode(requestDto.getZipcode())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();

        Property property = Property.builder()
                .host(host)
                .propertyName(requestDto.getPropertyName())
                .propertyType(requestDto.getPropertyType())
                .propertyExplanation(requestDto.getPropertyExplanation())
                .propertyDetail(detail)
                .address(address)
                .price(requestDto.getPrice())
                .photos(requestDto.getPhotos())
                .amenities(requestDto.getAmenities())
                .build();

        propertyRepository.save(property);
    }

    public ManageYourSpaceResponseDto getPropertyEditor(Long propertyId) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        return ManageYourSpaceResponseDto.builder()
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

    public void updatePhotos(Long propertyId, List<String> photos) {
        Property property = propertyRepository.getPropertyByPropertyId(propertyId);
        property.updatePhotos(photos);
        this.propertyRepository.save(property);
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
