package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private int amount;

    @Enumerated
    private PaymentMethod paymentMethod;

    @Enumerated
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentCompletionTime;

    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Booking booking;

    @Builder
    public Payment(int amount, PaymentMethod paymentMethod, String transactionId, User user, Booking booking) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.결제완료;
        this.paymentCompletionTime = LocalDateTime.now();
        this.transactionId = transactionId;
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
        if (booking.getPayment() != this) {
            booking.setPayment(this);
        }
    }
}
