package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.Review;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.response.ProgressReviewResponse;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public List<ProgressReviewResponse> progressReviews(Long userId) {
        User host = userRepository.findUserByUserId(userId);
        List<Review> reviews =
                propertyRepository.getPropertiesByHost(host)
                .stream()
                .map(Property::getReviews)
                .reduce((a, b) ->
                        Stream.concat(a.stream(), b.stream())
                                .collect(Collectors.toList()))
                .orElse(null);

        if (reviews == null) {
            System.out.println("null");
            return null;
        }

        return reviews.stream()
                .map(review ->
                        ProgressReviewResponse.builder()
                        .reviewId(review.getReviewId())
                        .content(review.getContent())
                        .score(review.getScore())
                        .userName(review.getUser().getName())
                        .userPicture(review.getUser().getPicture())
                        .propertyId(review.getProperty().getPropertyId())
                        .propertyName(review.getProperty().getPropertyName())
                        .build())
                .collect(Collectors.toList());
    }
}
