package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByProperty(Property property);

    Optional<Booking> findByBookingId(Long bookingId);

    List<Booking> findAllByUser(User user);

    List<Booking> findByPropertyAndCheckOutDateAfter(Property property, LocalDate checkOutDate);

}
