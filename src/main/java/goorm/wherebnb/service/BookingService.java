package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BookingRequest;
import goorm.wherebnb.domain.dto.request.PaymentRequest;
import goorm.wherebnb.domain.dto.response.BookingDetailResponse;
import goorm.wherebnb.domain.dto.response.BookingSimpleResponse;
import goorm.wherebnb.repository.BookingRepository;
import goorm.wherebnb.repository.PaymentRepository;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public BookingDetailResponse getBooking(Long bookingId) {
        Booking findBooking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return BookingDetailResponse.builder()
                .booking(findBooking)
                .build();
    }

    public List<BookingSimpleResponse> getAllBookings(Long userId) {

        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return bookingRepository.findAllByUser(findUser).stream()
                .map(booking -> BookingSimpleResponse.builder()
                        .booking(booking)
                        .build())
                .collect(Collectors.toList());
    }

    public Booking createBooking(Long propertyId, BookingRequest bookingRequest) {

//        User findUser = userRepository.findByEmail(bookingRequest.getEmail())
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Property findProperty = propertyRepository.findByPropertyId(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        Payment newPayment = paymentService.createPayment(bookingRequest.getPaymentRequest());

        Booking newBooking = Booking.builder()
                .user(newPayment.getUser())
                .property(findProperty)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .payment(newPayment)
                .numberOfGuest(bookingRequest.getNumberOfGuest())
                .build();

        newPayment.setBooking(newBooking);
        if (newPayment.getPaymentId() % 3 != 0) {
            // 결제 성공
            // 예약 처리
            newPayment.updatePaymentStatus(PaymentStatus.결제완료);
        } else {
            // 결제 실패
            // 예약 처리 중단
            newPayment.updatePaymentStatus(PaymentStatus.결제실패);
            newBooking.updateBookingStatus(BookingStatus.예약실패);
        }

        bookingRepository.save(newBooking);
        paymentRepository.save(newPayment);

        return newBooking;
    }
}
