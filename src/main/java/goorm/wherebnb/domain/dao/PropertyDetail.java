package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class PropertyDetail {

    private int maxPeople;

    private boolean selfCheckIn;

    private boolean petAvailable;

    private boolean smokeAvailable;

    private int checkInTime; // 0 ~ 23

    private int checkOutTime; // 0 ~ 23

    private int bedroom;

    private int bed;

    private int bathroom;

    @Builder
    public PropertyDetail(int maxPeople, boolean selfCheckIn, boolean petAvailable, boolean smokeAvailable,
                          int checkInTime, int checkOutTime, int bedroom, int bed, int bathroom) {
        this.maxPeople = maxPeople;
        this.selfCheckIn = selfCheckIn;
        this.petAvailable = petAvailable;
        this.smokeAvailable = smokeAvailable;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bathroom = bathroom;
    }
}
