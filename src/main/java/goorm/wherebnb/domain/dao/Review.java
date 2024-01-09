package goorm.wherebnb.domain.dao;

import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

//    BaseTimeEntity 로 대체
//    private LocalDateTime createReviewTime;

    private float totalScore;

    private int cleanScore;
    private int communicationScore;
    private int checkInScore;
    private int accuracyScore;
    private int locationScore;
    private int priceScore;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    @Builder
    public Review(int cleanScore, int communicationScore, int checkInScore, int accuracyScore,
                  int locationScore, int priceScore, String content,  User user) {
        this.totalScore = (float) (cleanScore + communicationScore + checkInScore + accuracyScore + locationScore + priceScore) / 6;
        this.cleanScore = cleanScore;
        this.communicationScore = communicationScore;
        this.checkInScore = checkInScore;
        this.accuracyScore = accuracyScore;
        this.locationScore = locationScore;
        this.priceScore = priceScore;
        this.content = content;
        this.user = user;
    }

    //== 연관관계 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    public void setProperty(Property property) {
        this.property = property;
        property.getReviews().add(this);
    }
}
