package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class PropertyDetail {

    private int standardPeople;

    private int maxPeople;

    private int bedroom;

    private int bed;

    private int bathroom;

    @Builder
    public PropertyDetail(int standardPeople, int maxPeople, int bedroom, int bed, int bathroom) {
        this.standardPeople = standardPeople;
        this.maxPeople = maxPeople;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bathroom = bathroom;
    }

}
