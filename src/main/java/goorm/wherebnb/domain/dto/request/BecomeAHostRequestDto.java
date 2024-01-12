package goorm.wherebnb.domain.dto.request;

import goorm.wherebnb.domain.dao.PropertyType;
import lombok.Getter;

import java.util.List;

@Getter
public class BecomeAHostRequestDto {

    private Long userId;

    private String propertyName;
    private PropertyType propertyType;
    private String propertyExplanation;

    private String country;
    private String state;
    private String city;
    private String street;
    private String details;
    private String zipcode;
    private Double latitude;
    private Double longitude;

    private int maxPeople;
    private int bedroom;
    private int bed;
    private int bathroom;

    private int price;

    private List<String> amenities;
}
