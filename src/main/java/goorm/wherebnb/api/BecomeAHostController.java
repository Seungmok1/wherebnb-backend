package goorm.wherebnb.api;

import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/become-a-host")
public class BecomeAHostController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<?> becomeAHost(@RequestBody BecomeAHostRequestDto requestDto) {
        try {
            propertyService.createProperty(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("숙소 등록이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
