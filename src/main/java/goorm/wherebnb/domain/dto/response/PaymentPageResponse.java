package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Property;
import goorm.wherebnb.domain.dao.PropertyType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentPageResponse {

    private String propertyName;

    private String propertyPhoto;

    private PropertyType propertyType;

    private int pricePerDay;

    @Builder
    public PaymentPageResponse(Property property) {
        this.propertyName = property.getPropertyName();
        this.propertyPhoto = property.getPhotos().get(0);
        this.propertyType = property.getPropertyType();
        this.pricePerDay = property.getPrice();
    }

}
