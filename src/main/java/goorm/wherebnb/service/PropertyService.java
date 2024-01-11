package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.PropertyDetail;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                .tags(new ArrayList<>())
                .build();
    }
}
