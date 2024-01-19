package goorm.wherebnb.domain.dto.response;


import goorm.wherebnb.domain.dao.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReviewResponse {

    private Long reviewId;

    private String photo;

    private String userName;

    private String nation;

    private float totalScore;

    private LocalDate writeDate;

    private String content;

    @Builder
    public ReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.photo = review.getUser().getPicture();
        this.userName = review.getUser().getName();
        this.nation = review.getUser().getAddress().getCountry();
        this.totalScore = review.getScore().getTotalScore();
        this.writeDate = review.getCreateDate().toLocalDate();
        this.content = review.getContent();
    }
}
