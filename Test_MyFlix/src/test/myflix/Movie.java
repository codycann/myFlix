package test.myflix;

public class Movie {
    public String rating;
    public String title;
    public Movie(){
        super();
    }
    
    public Movie(String title, String rating) {
        super();
        this.rating = rating;
        this.title = title;
    }
}