package goorm.wherebnb.domain.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BecomeAHostRequestDto {
    private String propertyName;
    private String propertyType;
    private String propertyExplanation;

    private Long userId;

    private int maxPeople;
    private int bedroom;
    private int bed;
    private int bathroom;

    private String country;
    private String state;
    private String street;
    private String details;
    private String zipcode;
    private Double latitude;
    private Double longitude;

    private int price;

    private List<String> photos;
    private List<String> amenities;
}
