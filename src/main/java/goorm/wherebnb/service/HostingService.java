package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.response.HostingListingResponse;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostingService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public List<HostingListingResponse> hostingListing(Long userId) {
        User host = userRepository.findUserByUserId(userId);
        return propertyRepository.getPropertiesByHost(host)
                .stream()
                .map(property -> HostingListingResponse.builder()
                        .propertyId(property.getPropertyId())
                        .status(property.isStatus())
                        .propertyName(property.getPropertyName())
                        .photos(property.getPhotos())
                        .build())
                .collect(Collectors.toList());
    }
}
