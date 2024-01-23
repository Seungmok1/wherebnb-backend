package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.User;

import java.util.List;

public class PropertySearchResponse {
    private Address address;
    private User user;

    private Long propertyId;

    private int price;
    private float totalScore;
    private int reviewsNum; // review 개수 세기
    private String propertyExplanation;
    private  boolean gustFavorite;
    private List<String> photos;

}
