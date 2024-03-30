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
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ForumThread> threads;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
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
        return "Category [name=" + name + ", description=" + description + "]";
    }

  
    

    

}
