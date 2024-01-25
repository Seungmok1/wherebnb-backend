package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "booking_datails")
public class BookingDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingDetailId;

    private LocalDate detailDate;

    private BookingStatus bookingStatus;

    @Builder
    public BookingDetail(Long bookingDetailId, LocalDate detailDate, BookingStatus bookingStatus) {
        this.bookingDetailId = bookingDetailId;
        this.detailDate = detailDate;
        this.bookingStatus = bookingStatus;
    }
}
