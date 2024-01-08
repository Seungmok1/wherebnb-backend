package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    private String picture;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String emergencyNumber;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "host")
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany( mappedBy = "users")
//    @JoinColumn(name = "conversation_id")
    private List<UserChat> userChats = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @Builder
    public User(String name, String email, String picture, String password, String phoneNumber, String emergencyNumber, Address address) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emergencyNumber = emergencyNumber;
        this.address = address;
    }

    //== 연관관계 메서드 ==//
    public void addProperty(Property property) {
        this.properties.add(property);
        property.setHost(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setUser(this);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setUser(this);
    }

    public void setUserChats(UserChat userChat) {
        this.userChats.add(userChat);
        userChat.setUser(this);
    }

    public void addSentMessage(Message message) {
        sentMessages.add(message);
        if (message.getSender() != this) {
            message.setSender(this);
        }
    }

    public void addReceivedMessage(Message message) {
        receivedMessages.add(message);
        if (message.getRecipient() != this) {
            message.setRecipient(this);
        }
    }
}
