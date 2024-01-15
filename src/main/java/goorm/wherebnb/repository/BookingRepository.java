package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Booking;
import goorm.wherebnb.domain.dao.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByProperty(Property property);
}
