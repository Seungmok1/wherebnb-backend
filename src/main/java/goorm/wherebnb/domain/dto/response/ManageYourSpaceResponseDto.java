package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.PropertyDetail;
import goorm.wherebnb.domain.dao.PropertyType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ManageYourSpaceResponseDto {

    private boolean status;
    private List<String> photos;
    private String propertyName;
    private PropertyType propertyType;
    private PropertyDetail propertyDetail;
    private String propertyExplanation;
    private List<String> amenities;
    private Address address;
    private String hostPicture;
    private String hostName;

    @Builder
    public ManageYourSpaceResponseDto(boolean status, List<String> photos, String propertyName, PropertyType propertyType, PropertyDetail propertyDetail, String propertyExplanation, List<String> amenities, Address address, String hostPicture, String hostName) {
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
    }
}
