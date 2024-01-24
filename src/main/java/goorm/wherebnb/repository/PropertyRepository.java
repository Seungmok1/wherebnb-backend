package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.response.PropertySearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByPropertyId(Long propertyId);

    Property getPropertyByPropertyId(Long propertyId);

    List<Property> getPropertiesByHost(User host);

    List<Property> findAll();

    @Query("select p from Property p where p not in (select b.property from Booking b join b.bookingDetailList l where l.detailDate in :days)")
    List<Property> getAvailableProperties(@Param("days") List<LocalDate> days);

//
//    Slice<Property> findByProperty(Property property, Pageable pageable);
}
