package hh.sof03.travelexp.domain;

public class Category {


    //categorian nimi + id myöhemmin
    //private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
      
    }

    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }

    

    

}
