package goorm.wherebnb.api;

import goorm.wherebnb.domain.dto.request.BecomeAHostRequestDto;
import goorm.wherebnb.service.PropertyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/become-a-host")
public class BecomeAHostApiController {

    private final PropertyService propertyService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> becomeAHost(@RequestPart BecomeAHostRequestDto requestDto, HttpServletRequest request) throws IOException {
        try {
            propertyService.createProperty(requestDto, request);
            return ResponseEntity.status(HttpStatus.CREATED).body("숙소 등록이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
