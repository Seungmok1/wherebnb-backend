package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}
