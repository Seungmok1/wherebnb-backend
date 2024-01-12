package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "properties")
@NoArgsConstructor
public class Property extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

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

    @ElementCollection
    private List<String> amenities;

    @OneToMany(mappedBy = "property")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "property")
    private List<Booking> bookings = new ArrayList<>();

    @Builder
    public Property(User host, String propertyName, PropertyType propertyType, String propertyExplanation,
                    PropertyDetail propertyDetail, Address address, int price, List<String> photos, List<String> amenities) {
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
        booking.setProperty(this);
    }

}