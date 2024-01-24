package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.Address;
import goorm.wherebnb.domain.dao.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PropertySearchResponse {

    private Long propertyId;
    private List<String> photos;
    private Address address;
    private SearchUserDto searchUser;
    private int price;
    private double totalScore;
    private int reviews;
    private String propertyExplanation;
    private boolean guestFavorite;

    @Builder
    public PropertySearchResponse(Long propertyId, List<String> photos, Address address, SearchUserDto searchUser, int price, double totalScore, int reviews, String propertyExplanation, boolean guestFavorite) {
        this.propertyId = propertyId;
        this.photos = photos;
        this.address = address;
        this.searchUser = searchUser;
        this.price = price;
        this.totalScore = totalScore;
        this.reviews = reviews;
        this.propertyExplanation = propertyExplanation;
        this.guestFavorite = guestFavorite;
    }
}

