package goorm.wherebnb.domain.dto.request;

import goorm.wherebnb.domain.dao.Amenity;
import goorm.wherebnb.domain.dao.Category;
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
    private boolean selfCheckIn;
    private boolean petAvailable;
    private boolean smokeAvailable;
    private int checkInTime;
    private int checkOutTime;
    private int bedroom;
    private int bed;
    private int bathroom;

    private int price;

    private Category category;

    //    private List<String> amenities;
    private List<Amenity> amenities;
}
