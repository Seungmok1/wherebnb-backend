package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dto.response.HostResponse;
import goorm.wherebnb.domain.dto.response.PropertyResponse;
import goorm.wherebnb.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

}
