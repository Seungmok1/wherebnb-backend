package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.Property;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookingDetailResponse {

    private String hostName;
    private String hostPhoto;
    private String hostPhoneNumber;

    private List<String> photos;

    private LocalDate checkInDate;
    private int checkInTime;
    private LocalDate checkOutDate;
    private int checkOutTime;

    private int numberOfGuest;
    private String guestPhoto;

    private Long bookingNumber;

    private Address address;

    private int totalPrice;

    @Builder
    public BookingDetailResponse(Booking booking) {
        Property property = booking.getProperty();
        this.hostName = property.getHost().getName();
        this.hostPhoto = property.getHost().getPicture();
        this.hostPhoneNumber = property.getHost().getPhoneNumber();
        this.photos = property.getPhotos();
        this.checkInDate = booking.getCheckInDate();
        this.checkInTime = property.getPropertyDetail().getCheckInTime();
        this.checkOutDate = booking.getCheckOutDate();
        this.checkOutTime = property.getPropertyDetail().getCheckOutTime();
        this.numberOfGuest = booking.getNumberOfGuest();
        this.guestPhoto = booking.getUser().getPicture();
        this.bookingNumber = booking.getBookingId();
        this.address = property.getAddress();
        this.totalPrice = booking.getPayment().getTotalPrice();
    }
}
