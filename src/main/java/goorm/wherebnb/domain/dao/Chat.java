package goorm.wherebnb.domain.dao;

import goorm.wherebnb.domain.BaseTimeEntity;
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
public class Chat extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long charId;

    private String title;

    @OneToMany(mappedBy = "chat")//  유저 1-N 대화 방 1-N 대화 -> 최대한 다대다 관계 피하기
//    @JoinColumn(name = "user_id")
    private List<UserChat> userChats;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    @Builder
    public Chat(String title, List<UserChat> userChats, List<Message> message) {
        this.title = title;
        this.userChats = userChats;
        this.messages = message;
    }

    //==연관관계 메서드==//
    public void addUserChat(UserChat uerChat) {
        this.userChats.add(uerChat);
        uerChat.setChat(this);
    }
    public void addMessage(Message message) {
        this.messages.add(message);
        message.setChat(this);
    }

}
