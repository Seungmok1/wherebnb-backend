package goorm.wherebnb.domain.dao;

import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String email;

    private int totalPrice;

    @Enumerated
    private PaymentMethod paymentMethod;

    @Enumerated
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Booking booking;

    @Builder
    public Payment(String email, int totalPrice, PaymentMethod paymentMethod, User user, Booking booking) {
        this.email = email;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.결제대기;
        this.user = user;
        this.booking = booking;
    }

    //== 연관관계 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getPayments().add(this);
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
        booking.setPayment(this);
    }

    public void updatePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
