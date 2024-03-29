package hh.sof03.travelexp.domain;

import java.time.LocalDateTime;

public class Message {

    //private Long id;
    //threadsID -> viittaus tietyn ketjun viestiin
    //käyttäjäID -> viittaus käyttäjä tauluun, tieto kenen viesti
    private String message;
    private LocalDateTime messageTime;

    
    public Message(String message, LocalDateTime messageTime) {
        this.message = message;
        this.messageTime = messageTime;
    }

    public Message() {
     
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "Message [message=" + message + ", messageTime=" + messageTime + "]";
    }

    
    



}
