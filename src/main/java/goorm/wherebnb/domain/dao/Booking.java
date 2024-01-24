package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Property property;

    //    BaseTimeEntity 로 대체
//    private LocalDateTime createBookingTime;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated
    private BookingStatus bookingStatus;

    //    @OneToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @OneToOne
    private Payment payment;

    private int numberOfGuest;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private List<BookingDetail> bookingDetailList;

    @Builder
    public Booking(User user, Property property, LocalDate checkInDate,
                   LocalDate checkOutDate, Payment payment, int numberOfGuest, List<BookingDetail> bookingDetailList) {
        this.user = user;
        this.property = property;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = BookingStatus.이용전;
        this.payment = payment;
        this.numberOfGuest = numberOfGuest;
        this.bookingDetailList = bookingDetailList;
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
