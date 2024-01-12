package goorm.wherebnb.domain.dto.request;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookingRequest {

    private Long propertyId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private int numberOfGuest;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfInfants;

    private int price;

    private PaymentRequest paymentRequest;

    private String message;

    @Builder
    public BookingRequest(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuest, int numberOfAdults, int numberOfChildren,
                          int numberOfInfants, int price, PaymentRequest paymentRequest, String message) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuest = numberOfGuest;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.numberOfInfants = numberOfInfants;
        this.price = price;
        this.paymentRequest = paymentRequest;
        this.message = message;
    }
}
