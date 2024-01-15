//package goorm.wherebnb.api;
//
//import goorm.wherebnb.domain.dto.response.PropertyResponse;
////import goorm.wherebnb.service.PropertyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/rooms")
//public class PropertyApiController {
//
////    private  final PropertyService propertyService;
//
//    @GetMapping("/{propertyId}")
//    public ResponseEntity<PropertyResponse> getProperty(@PathVariable("propertyId") Long propertyId) {
//        return ResponseEntity.ok(propertyService.getProperty(propertyId));
//    }
//
//    @PostMapping("/{propertyId}/booking")
//    public ResponseEntity<?> createBooking(@PathVariable("propertyId") Long propertyId) {
//        return ResponseEntity.ok(null);
//    }
//}
