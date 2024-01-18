package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HostingReservationsResponse {
    private Long bookingId;
    private Long propertyId;
    private String propertyName;
    private BookingStatus bookingStatus;
    // 예약한 유저에 대한 정보

    @Builder
    public HostingReservationsResponse(Long bookingId, Long propertyId, String propertyName, BookingStatus bookingStatus) {
        this.bookingId = bookingId;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.bookingStatus = bookingStatus;
    }
}
