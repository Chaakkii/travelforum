package hh.sof03.travelexp.domain;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "forumthread")
public class ForumThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDateTime startDay;
    private int messages;

    @ManyToOne
    @JsonIgnoreProperties("threads")
    private Category category;

    public ForumThread(String title, LocalDateTime startDay, int messages, Category category) {
        this.title = title;
        this.startDay = LocalDateTime.now();
        this.messages = messages;
        this.category = category;
    }

    public ForumThread() {
    
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getStartDay() {
        return startDay;
    }
    public void setStartDay(LocalDateTime startDay) {
        this.startDay = startDay;
    }
    public int getMessages() {
        return messages;
    }
    public void setMessages(int messages) {
        this.messages = messages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Override
    public String toString() {
        return "Threads [title=" + title + ", startDay=" + startDay + ", messages=" + messages + "category =" + this.getCategory() + "]";
    }


}
