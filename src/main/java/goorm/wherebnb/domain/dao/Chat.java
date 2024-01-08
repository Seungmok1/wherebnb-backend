package goorm.wherebnb.domain.dao;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chats")
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long charId;

    private String title;

    @OneToMany(mappedBy = "chat")//  유저 1-N 대화 방 1-N 대화 -> 최대한 다대다 관계 피하기
//    @JoinColumn(name = "user_id")
    private List<UserChat> userChats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conversations")
    private List<Message> messages = new ArrayList<>();

    @Builder
    public Chat(String title, List<UserChat> userChats, List<Message> message) {
        this.title = title;
        this.userChats = userChats;
        this.messages = message;
    }

    //==연관관계 메서드==//
    public void setUserConversations(UserChat uerChat) {
        this.userChats.add(uerChat);
        uerChat.setChat(this);
    }
    public void setMessages(Message message) {
        this.messages.add(message);
        message.setChat(this);
    }

}
