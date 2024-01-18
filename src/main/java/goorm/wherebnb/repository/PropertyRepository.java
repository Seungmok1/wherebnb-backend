package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByPropertyId(Long propertyId);

    Property getPropertyByPropertyId(Long propertyId);

    List<Property> getPropertiesByHost(User host);
}
