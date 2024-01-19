package goorm.wherebnb.domain.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import goorm.wherebnb.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "messages")
public class Message extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
//    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Enumerated
    private MessageStatus messageStatus;

    @Builder
    public Message(String content, User sender, User recipient, Chat chat, MessageStatus messageStatus) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.chat = chat;
        this.messageStatus = messageStatus;
    }

    // 연관관계 편의 메서드
    public void setSender(User sender) {
        this.sender = sender;
        sender.addSentMessage(this);
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
        recipient.addReceivedMessage(this);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getMessages().add(this);
    }

}
