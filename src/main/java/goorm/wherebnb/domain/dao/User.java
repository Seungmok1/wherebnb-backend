package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "USERS")
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

}
