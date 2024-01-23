package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HostingListingEditorResponse {

    private boolean status;
    private List<String> photos;
    private String propertyName;
    private PropertyType propertyType;
    private PropertyDetail propertyDetail;
    private String propertyExplanation;
    private List<Amenity> amenities;
    private Address address;
    private String hostPicture;
    private String hostName;
    private Category category;

    @Builder
    public HostingListingEditorResponse(boolean status, List<String> photos, String propertyName, PropertyType propertyType, PropertyDetail propertyDetail, String propertyExplanation, List<Amenity> amenities, Address address, String hostPicture, String hostName, Category category) {
        this.status = status;
        this.photos = photos;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyDetail = propertyDetail;
        this.propertyExplanation = propertyExplanation;
        this.amenities = amenities;
        this.address = address;
        this.hostPicture = hostPicture;
        this.hostName = hostName;
        this.category = category;
    }
}
