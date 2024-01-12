package goorm.wherebnb.domain.dao;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Score {

    private float totalScore;

    private int cleanScore;
    private int communicationScore;
    private int checkInScore;
    private int accuracyScore;
    private int locationScore;
    private int priceScore;

    @Builder
    public Score(int cleanScore, int communicationScore, int checkInScore, int accuracyScore, int locationScore, int priceScore) {
        this.totalScore = (float) Math.round((cleanScore + communicationScore + checkInScore + accuracyScore + locationScore + priceScore) / 6.0 * 10) / 10;
        this.cleanScore = cleanScore;
        this.communicationScore = communicationScore;
        this.checkInScore = checkInScore;
        this.accuracyScore = accuracyScore;
        this.locationScore = locationScore;
        this.priceScore = priceScore;
    }
}
