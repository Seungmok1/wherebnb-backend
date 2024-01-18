package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@Table(name = "properties")
@NoArgsConstructor
public class Property extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @Column
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User host;

    @Column(nullable = false)
    private String propertyName;

    @Enumerated
    private PropertyType propertyType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String propertyExplanation;

    @Embedded
    private PropertyDetail propertyDetail;

    @Embedded
    private Address address;

    private int price;

    private boolean guestFavorite;

    private int favoriteNum;

    @ElementCollection
    private List<String> photos;

    @Enumerated
    private Amenity amenity;

    @ElementCollection
    private List<String> amenities;

    @OneToMany(mappedBy = "property")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "property")
    private List<Booking> bookings = new ArrayList<>();

    @Builder
    public Property(User host, String propertyName, PropertyType propertyType, String propertyExplanation, PropertyDetail propertyDetail,
                    Address address, int price, List<String> photos, List<String> amenities, Amenity amenity) {
        status = true;
        setHost(host);
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyExplanation = propertyExplanation;
        this.propertyDetail = propertyDetail;
        this.address = address;
        this.price = price;
        this.guestFavorite = false;
        this.favoriteNum = 0;
        this.photos = photos;
        this.amenities = amenities;
        this.amenity = amenity;
    }



    //== 연관관계 메서드 ==//
    public void setHost(User user) {
        this.host = user;
        user.addProperty(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    // 업데이트
    public void updateStatus(boolean status) {
        this.status = status;
    }

    public void updatePropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void updatePropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public void updatePropertyDetail(PropertyDetail propertyDetail) {
        this.propertyDetail = propertyDetail;
    }

    public void updatePropertyExplanation(String propertyExplanation) {
        this.propertyExplanation = propertyExplanation;
    }

    public void updateAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

}