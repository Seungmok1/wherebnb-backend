package goorm.wherebnb.api;

import goorm.wherebnb.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/progress")
public class ProgressApiController {

    private final ProgressService progressService;

    @GetMapping("/reviews")
    public ResponseEntity<?> progressReviews(@RequestBody Map<String, Long> map) {
        return ResponseEntity.ok(progressService.progressReviews(map.get("userId")));
    }

    @GetMapping("/earnings")
    public void progressEarnings(Long userId) {

    }
}
