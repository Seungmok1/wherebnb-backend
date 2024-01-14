package goorm.wherebnb.api;

import goorm.wherebnb.domain.dto.response.HostingListingResponse;
import goorm.wherebnb.service.HostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hosting")
public class HostingApiController {

    private final HostingService hostingService;

    @GetMapping("/listing")
    public List<HostingListingResponse> hostingListing(Long userId) {
        return hostingService.hostingListing(userId);
    }

    @GetMapping("/reservations")
    public void hostingReservations(Long userId) {

    }
}
