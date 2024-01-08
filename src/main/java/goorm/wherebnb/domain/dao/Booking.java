package goorm.wherebnb.domain.dao;

import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "property_id")
    private Property property;

    private LocalDateTime createBookingTime;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated
    private BookingStatus bookingStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @Builder
    public Booking(Property property, LocalDate checkInDate,
                   LocalDate checkOutDate, Payment payment) {
        this.property = property;
        this.createBookingTime = LocalDateTime.now();
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = BookingStatus.이용전;
        this.payment = payment;

    }

    public void setProperty(Property property) {
        this.property = property;
        property.getBookings().add(this);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment.getBooking() != this) {
            payment.setBooking(this);
        }
    }

}
