package hh.sof03.travelexp.domain;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @Size(min = 1, message = "Ei tyhjiä viestejä")
    @Column(length = 3000)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime messageTime;

    @ManyToOne
    private ForumThread forumThread;

    @ManyToOne
    private User user;

    
    public Message(String content, ForumThread forumThread, User user) {
        this.content = content;
        this.forumThread = forumThread;
        this.messageTime = LocalDateTime.now();
        this.user = user;

    }

    public Message() {
        this.messageTime = LocalDateTime.now();
     
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    public void setForumThread(ForumThread forumThread) {
        this.forumThread = forumThread;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message [content=" + content + ", messageTime=" + messageTime + ", user=" + user + "]";
    }

    
    



}
