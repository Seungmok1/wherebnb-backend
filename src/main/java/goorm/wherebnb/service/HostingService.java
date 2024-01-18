package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.response.HostingListingResponse;
import goorm.wherebnb.domain.dto.response.HostingReservationsResponse;
import goorm.wherebnb.repository.BookingRepository;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HostingService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;

    public List<HostingListingResponse> hostingListing(Long userId) {
        User host = userRepository.findUserByUserId(userId);
        return propertyRepository.getPropertiesByHost(host)
                .stream()
                .map(property -> HostingListingResponse.builder()
                        .propertyId(property.getPropertyId())
                        .status(property.isStatus())
                        .propertyName(property.getPropertyName())
                        .photos(property.getPhotos())
                        .build())
                .collect(Collectors.toList());
    }

    public List<HostingReservationsResponse> hostingReservation(Long userId) {
        User host = userRepository.findUserByUserId(userId);
        List<Booking> bookings =
                propertyRepository.getPropertiesByHost(host)
                        .stream()
                        .map(Property::getBookings)
                        .reduce((a, b) ->
                                Stream.concat(a.stream(), b.stream())
                                        .collect(Collectors.toList()))
                        .orElse(null);

        if (bookings == null) {
            return null;
        }

        return bookings.stream()
                .map(booking -> HostingReservationsResponse.builder()
                        .bookingId(booking.getBookingId())
                        .propertyId(booking.getProperty().getPropertyId())
                        .propertyName(booking.getProperty().getPropertyName())
                        .bookingStatus(booking.getBookingStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
