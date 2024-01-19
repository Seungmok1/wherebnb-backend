package goorm.wherebnb.api;

import goorm.wherebnb.domain.dto.response.ReviewResponse;
import goorm.wherebnb.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/rooms/{propertyId}/reviews")
    public ResponseEntity<Slice<ReviewResponse>> getReviews (
            @PathVariable("propertyId") Long propertyId,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(reviewService.getPropertyReviews(propertyId, page));
    }
}
