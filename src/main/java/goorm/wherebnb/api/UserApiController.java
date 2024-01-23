package goorm.wherebnb.api;


import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.BookingStatus;
import goorm.wherebnb.domain.dto.request.BookingRequest;
import goorm.wherebnb.domain.dto.response.BookingDetailResponse;
import goorm.wherebnb.domain.dto.response.BookingSimpleResponse;
import goorm.wherebnb.service.BookingService;
import goorm.wherebnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<BookingSimpleResponse>> getAllBookings(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bookingService.getAllBookings(userId));
    }

    @GetMapping("/{userId}/bookings/{bookingId}")
    public ResponseEntity<BookingDetailResponse> getBooking(@PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }
    @PostMapping("/rooms/booking/{propertyId}")
    public ResponseEntity<?> createBooking(@PathVariable("propertyId") Long propertyId,
                                                         @RequestBody BookingRequest bookingRequest) {

        Booking newBooking = bookingService.createBooking(propertyId, bookingRequest);

        if (newBooking.getBookingStatus().equals(BookingStatus.이용전)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("예약이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("예약에 실패했습니다. 결제처리 중 문제가 발생했습니다.");
        }

    }

    @GetMapping("/{userId}/wishlist")
    public ResponseEntity<List<Long>> getWishList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getWishList(userId));
    }

    @PostMapping("/{userId}/wishlist/{propertyId}")
    public ResponseEntity<List<Long>> addWishList(@PathVariable("userId") Long userId,
                                                  @PathVariable("propertyId") Long propertyId) {
        return ResponseEntity.ok(userService.addWishList(userId, propertyId));
    }

    @DeleteMapping("/{userId}/wishlist/{propertyId}")
    public ResponseEntity<List<Long>> deleteWishList(@PathVariable("userId") Long userId,
                                                  @PathVariable("propertyId") Long propertyId) {
        return ResponseEntity.ok(userService.removeWishList(userId, propertyId));
    }
}
