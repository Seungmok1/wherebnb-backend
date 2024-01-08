package goorm.wherebnb.domain.dao;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Price {

    private int basePrice;

    private int holidayPrice;

    private final int perWeekDiscountRate = 20;

    private final int perMonthDiscountRate = 35;

    @Builder
    public Price(int basePrice, int holidayPrice) {
        this.basePrice = basePrice;
        this.holidayPrice = holidayPrice;
    }

}
