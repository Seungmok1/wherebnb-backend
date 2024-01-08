package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long charId;

    @ManyToMany
    @JoinTable(
            name = "chat_users", // 매핑 테이블 이름
            joinColumns = @JoinColumn(name = "chat_id"), // 현재 엔터티(Chat)의 조인 컬럼
            inverseJoinColumns = @JoinColumn(name = "user_id") // 반대쪽 엔터티(User)의 조인 컬럼
    )

    private List<User> users;


}
