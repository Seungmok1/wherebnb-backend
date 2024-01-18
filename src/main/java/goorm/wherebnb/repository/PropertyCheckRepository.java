package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyCheckRepository extends JpaRepository<Property, Long> {
    Page<Property> findByPropertyNameContaining(String keyword, Pageable pageable);
    Page<Property> findByPropertyType(PropertyType propertyType, Pageable pageable);
    Page<Property> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable);
    @Query(countQuery = "select count(*) from room r where (r.price between :minPrice and :maxPrice) and r.type = :propertyType", nativeQuery = true)
    Page<Property> findByPropertyTypeAndPriceBetween(@Param("minPrice") int minPrice,
                                                     @Param("maxPrice") int maxPrice,
                                                     @Param("propertyType") PropertyType propertyType,
                                                     Pageable pageable);

}
