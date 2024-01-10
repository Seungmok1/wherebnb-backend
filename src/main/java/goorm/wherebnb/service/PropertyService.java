package goorm.wherebnb.service;

import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public void createProperty(BecomeAHostRequestDto requestDto) {
    }
}
