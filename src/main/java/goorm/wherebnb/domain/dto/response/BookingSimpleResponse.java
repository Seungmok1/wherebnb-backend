package goorm.wherebnb.domain.dto.response;


import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.Property;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BookingSimpleResponse {

    private Long bookingId;

    private String hostName;

    private String propertyPhoto;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String country;
    private String state;
    private String city;
    private String street;
    private String details;

    @Builder
    public BookingSimpleResponse(Booking booking) {
        this.bookingId = booking.getBookingId();

        Property property = booking.getProperty();
        this.hostName = property.getHost().getName();

        List<String> photos = property.getPhotos();
        this.propertyPhoto = (photos != null && !photos.isEmpty()) ? photos.get(0) : null;

        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.country = property.getAddress().getCountry();
        this.state = property.getAddress().getState();
        this.city = property.getAddress().getCity();
        this.street = property.getAddress().getStreet();
        this.details = property.getAddress().getDetails();
    }
}
