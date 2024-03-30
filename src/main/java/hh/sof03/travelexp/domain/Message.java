package hh.sof03.travelexp.domain;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime messageTime;

    @ManyToOne
    private ForumThread forumThread;

    
    public Message(String content, ForumThread forumThread) {
        this.content = content;
        this.forumThread = forumThread;
        this.messageTime = LocalDateTime.now();
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

    @Override
    public String toString() {
        return "Message [content=" + content + ", messageTime=" + messageTime + "]";
    }

    
    



}
