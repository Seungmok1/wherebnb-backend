package goorm.wherebnb.domain.dao;

import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "property_id")
    private Property property;

//    BaseTimeEntity 로 대체
//    private LocalDateTime createBookingTime;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated
    private BookingStatus bookingStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    private int numberOfGuest;

    @Builder
    public Booking(User user, Property property, LocalDate checkInDate,
                   LocalDate checkOutDate, Payment payment, int numberOfGuest) {
        this.user = user;
        this.property = property;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = BookingStatus.이용전;
        this.payment = payment;
        this.numberOfGuest = numberOfGuest;
    }

    public void setUser(User user) {
        this.user = user;
        user.addBooking(this);
    }

    public void setProperty(Property property) {
        this.property = property;
        property.addBooking(this);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void updateBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

}
