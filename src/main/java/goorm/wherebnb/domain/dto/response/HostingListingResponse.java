package goorm.wherebnb.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class HostingListingResponse {
    private Long propertyId;
    private boolean status;
    private String propertyName;
    private List<String> photos;

    @Builder
    public HostingListingResponse(Long propertyId, boolean status, String propertyName, List<String> photos) {
        this.propertyId = propertyId;
        this.status = status;
        this.propertyName = propertyName;
        this.photos = photos;
    }
}
