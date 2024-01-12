package goorm.wherebnb.api;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.PropertyDetail;
import goorm.wherebnb.domain.dao.PropertyType;
import goorm.wherebnb.domain.dto.response.ManageYourSpaceResponseDto;
import goorm.wherebnb.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage-your-space")
public class ManageYourSpaceController {

    private final PropertyService propertyService;

    @GetMapping("/{propertyId}")
    public ResponseEntity<?> manageYourSpace(@PathVariable Long propertyId) {
        ManageYourSpaceResponseDto responseDto = propertyService.getPropertyEditor(propertyId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{propertyId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long propertyId, @RequestBody boolean status) {
        try {
            propertyService.updateStatus(propertyId, status);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/propertyName")
    public ResponseEntity<?> updatePropertyName(@PathVariable Long propertyId, @RequestBody String propertyName) {
        try {
            propertyService.updatePropertyName(propertyId, propertyName);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/propertyType")
    public ResponseEntity<?> updatePropertyType(@PathVariable Long propertyId, @RequestBody String propertyType) {
        try {
            propertyService.updatePropertyType(propertyId, PropertyType.valueOf(propertyType));
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/propertyDetail")
    public ResponseEntity<?> updatePropertyDetail(@PathVariable Long propertyId, @RequestBody PropertyDetail propertyDetail) {
        try {
            propertyService.updatePropertyDetail(propertyId, propertyDetail);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/propertyExplanation")
    public ResponseEntity<?> updatePropertyExplanation(@PathVariable Long propertyId, @RequestBody String propertyExplanation) {
        try {
            propertyService.updatePropertyExplanation(propertyId, propertyExplanation);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

//    @PatchMapping("/{propertyId}/photos")
//    public ResponseEntity<?> updatePhotos(@PathVariable Long propertyId, @RequestBody List<String> photos) {
//        try {
//            propertyService.updatePhotos(propertyId, photos);
//            return ResponseEntity.ok("수정이 완료되었습니다.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
//        }
//    }

    @PatchMapping("/{propertyId}/amenities")
    public ResponseEntity<?> updateAmenities(@PathVariable Long propertyId, @RequestBody List<String> amenities) {
        try {
            propertyService.updateAmenities(propertyId, amenities);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/address")
    public ResponseEntity<?> updateAddress(@PathVariable Long propertyId, @RequestBody Address address) {
        try {
            propertyService.updateAddress(propertyId, address);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

    @PatchMapping("/{propertyId}/price")
    public ResponseEntity<?> updatePrice(@PathVariable Long propertyId, @RequestBody int price) {
        try {
            propertyService.updatePrice(propertyId, price);
            return ResponseEntity.ok("수정이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
