package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Score;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProgressReviewResponse {

    private Long reviewId;
    private String content;
    private Score score;

    private String userName;
    private String userPicture;

    private Long propertyId;
    private String propertyName;

    @Builder
    public ProgressReviewResponse(Long reviewId, String content, Score score, String userName, String userPicture, Long propertyId, String propertyName) {
        this.reviewId = reviewId;
        this.content = content;
        this.score = score;
        this.userName = userName;
        this.userPicture = userPicture;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
    }
}
