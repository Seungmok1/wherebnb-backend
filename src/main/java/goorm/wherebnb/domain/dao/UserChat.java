package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class UserChat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userConversationsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    @Builder
    public UserChat(User user, Chat chat) {
        this.user = user;
        this.chat = chat;
    }

    public UserChat() {
    }

    //==연관관계 편의 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getUserChats().add(this);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getUserChats().add(this);
    }
}
