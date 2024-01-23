package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Slice<Review> findByProperty(Property property, Pageable pageable);
}
