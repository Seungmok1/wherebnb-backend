package goorm.wherebnb.domain.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookingRequest {

    private Long userId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private int numberOfGuest;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfInfants;

//    private int totalPrice;

    private PaymentRequest paymentRequest;

    private String message;

    @Builder
    public BookingRequest(Long userId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfAdults,
                          int numberOfChildren, int numberOfInfants, PaymentRequest paymentRequest, String message) {
        this.userId = userId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuest = numberOfAdults + numberOfChildren;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfInfants = numberOfInfants;
        this.paymentRequest = paymentRequest;
        this.message = message;
    }
}
