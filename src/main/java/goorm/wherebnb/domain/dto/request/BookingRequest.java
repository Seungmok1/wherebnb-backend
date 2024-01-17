package goorm.wherebnb.domain.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BookingRequest {

//    private String email;

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
    public BookingRequest(String email, LocalDate checkInDate, LocalDate checkOutDate, int numberOfAdults,
                          int numberOfChildren, int numberOfInfants, int totalPrice, PaymentRequest paymentRequest, String message) {
//        this.email = email;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuest = numberOfAdults + numberOfChildren;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfInfants = numberOfInfants;
//        this.totalPrice = totalPrice;
        this.paymentRequest = paymentRequest;
        this.message = message;
    }
}
