package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dto.request.BookingRequest;
import goorm.wherebnb.domain.dto.response.BookingResponse;
import goorm.wherebnb.repository.BookingRepository;
import goorm.wherebnb.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;

//    public BookingResponse createBooking(BookingRequest bookingRequest) {
//
//        Booking newBooking = Booking.builder()
//                .property(propertyRepository.findByPropertyId(bookingRequest.getPropertyId()))
//                .checkInDate(bookingRequest.getCheckInDate())
//                .checkOutDate(bookingRequest.getCheckOutDate())
//                .build();
//    }
}
