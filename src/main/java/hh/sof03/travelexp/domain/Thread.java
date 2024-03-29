package hh.sof03.travelexp.domain;
import java.time.LocalDate;

public class Thread {

    private String title;
    private LocalDate startDay;
    private int messages;

    
    public Thread(String title, LocalDate startDay, int messages) {
        this.title = title;
        this.startDay = startDay;
        this.messages = messages;
    }

    public Thread() {
    
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getStartDay() {
        return startDay;
    }
    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }
    public int getMessages() {
        return messages;
    }
    public void setMessages(int messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Threads [title=" + title + ", startDay=" + startDay + ", messages=" + messages + "]";
    }


}
