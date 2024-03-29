package hh.sof03.travelexp.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    
    private Long categoryId;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ForumThread> threads;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryid() {
        return categoryId;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryId = categoryid;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
      
    }

      public List<ForumThread> getThreads() {
        return threads;
    }

    public void setThreads(List<ForumThread> threads) {
        this.threads = threads;
    }


    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

  
    

    

}
