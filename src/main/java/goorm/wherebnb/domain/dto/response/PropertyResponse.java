package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PropertyResponse {

    private String propertyName;

    private List<String> photos;

    private PropertyType propertyType;

    private PropertyDetail propertyDetail;

    private String propertyExplanation;

    private int price;

    private List<String> amenities;

    private boolean guestFavorite;

    private Map<String, Float> scores;

    private List<ReviewResponse> reviews;

    private Address address;

    private HostResponse host;

    @Builder
    public PropertyResponse(Property property) {
        List<Review> reviews = property.getReviews();

        this.propertyName = property.getPropertyName();
        this.photos = property.getPhotos();
        this.propertyType = property.getPropertyType();
        this.propertyDetail = property.getPropertyDetail();
        this.propertyExplanation = property.getPropertyExplanation();
        this.price = property.getPrice();
        this.amenities = property.getAmenities();
        this.guestFavorite = property.isGuestFavorite();
        this.scores = Map.of(
                "totalScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getTotalScore()).average().orElse(0)),
                "cleanScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getCleanScore()).average().orElse(0)),
                "communicationScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getCommunicationScore()).average().orElse(0)),
                "checkInScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getCheckInScore()).average().orElse(0)),
                "accuracyScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getAccuracyScore()).average().orElse(0)),
                "locationScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getLocationScore()).average().orElse(0)),
                "priceScore", roundToOneDecimal(reviews.stream().mapToDouble(r -> r.getScore().getPriceScore()).average().orElse(0))
        );
        this.reviews = reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
        this.address = property.getAddress();
        this.host = new HostResponse(property.getHost());
    }

    private float roundToOneDecimal(double value) {
        return (float) (Math.round(value * 10) / 10.0);
    }
}
