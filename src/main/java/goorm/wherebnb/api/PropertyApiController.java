package goorm.wherebnb.api;

import goorm.wherebnb.domain.dto.response.PagedResponse;
import goorm.wherebnb.domain.dto.response.PropertyResponse;
import goorm.wherebnb.domain.dto.response.PropertySearchResponse;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PropertyApiController {

    private final PropertyService propertyService;

    @GetMapping("/rooms/{propertyId}")
    public ResponseEntity<PropertyResponse> getProperty(@PathVariable("propertyId") Long propertyId) {
        return ResponseEntity.ok(propertyService.getProperty(propertyId));
    }

    @GetMapping("/")
    public ResponseEntity<Slice<PropertySearchResponse>> search(
            @RequestParam(value = "place", required = false) String place,
            @RequestParam(value = "checkInDate", required = false) LocalDate checkInDate,
            @RequestParam(value = "checkOutDate", required = false) LocalDate checkOutDate,
            @RequestParam(value = "adult", required = false, defaultValue = "0") int adult,
            @RequestParam(value = "children", required = false, defaultValue = "0") int children,
            @RequestParam(value = "infants", required = false, defaultValue = "0") int infants,
            @RequestParam(value = "pets", required = false, defaultValue = "0") int pets,
            @RequestParam(value = "price_min", required = false, defaultValue = "0") int price_min,
            @RequestParam(value = "price_max", required = false, defaultValue = "1000000") int price_max,
            @RequestParam(value = "min_bedrooms", required = false, defaultValue = "0") int min_bedrooms,
            @RequestParam(value = "min_beds", required = false, defaultValue = "0") int min_beds,
            @RequestParam(value = "min_bathrooms", required = false, defaultValue = "0") int min_bathrooms,
            @RequestParam(value = "guest_favorite", required = false) Boolean guest_favorite,
            @RequestParam(value = "property_type", required = false) Integer property_type,
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "amenity", required = false) List<Integer> amenity, Pageable pageable) {
            Slice<PropertySearchResponse> searchResponses = propertyService.unifiedPropertySearch(
                checkInDate, checkOutDate, place, adult, children, infants, pets,
                price_min, price_max, min_bedrooms, min_beds, min_bathrooms,
                guest_favorite, property_type, category, amenity, pageable
            );

        return ResponseEntity.ok(searchResponses);
    }





}
