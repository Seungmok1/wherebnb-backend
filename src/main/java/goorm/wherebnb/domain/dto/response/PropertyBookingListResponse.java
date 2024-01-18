package goorm.wherebnb.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PropertyBookingListResponse {
    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Builder
    public PropertyBookingListResponse(LocalDate checkInDate, LocalDate checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
