package hh.sof03.travelexp.domain;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "forumthread")
public class ForumThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDateTime startDay;

    @ManyToOne
    @JsonIgnoreProperties("threads")
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "forumThread", cascade = CascadeType.ALL)
    private List<Message> messages;

    public ForumThread(String title, LocalDateTime startDay, Category category) {
        this.title = title;
        this.startDay = startDay;
        this.category = category;
        this.messages = new ArrayList<>();
    }

    public ForumThread() {
        this.messages =  new ArrayList<>();
    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setForumThread(this);
    }

    @Override
    public String toString() {
        return "Threads [title=" + title + ", startDay=" + startDay + ", category =" + this.getCategory() + "]";
    }




}
