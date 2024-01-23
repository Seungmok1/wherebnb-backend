package goorm.wherebnb.service;


import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.Review;
import goorm.wherebnb.domain.dto.response.ReviewResponse;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public Slice<ReviewResponse> getPropertyReviews(Long propertyId, int page) {
        Property findProperty = propertyRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createDate"));
        Slice<Review> reviews = reviewRepository.findByProperty(findProperty, pageRequest);

        return reviews.map(ReviewResponse::new);
    }

}
