package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.PropertyPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyPhotoRepository extends JpaRepository<PropertyPhoto, Long> {
    List<PropertyPhoto> getPropertyPhotosByProperty(Property property);
}
