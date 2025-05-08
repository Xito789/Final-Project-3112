import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {
    private String title;
    private Author author;
    private List<Genre> genres;
    private double rating;

    public Book(String title, Author author, List<Genre> genres, double rating){
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.rating = rating;
    }

    public String getTitle(){
        return title;
    }

    public Author getAuthor(){
        return author;
    }

    public List<Genre> getGenres(){
        return genres;
    }

    public Set<String> getGenreNames() {
        return genres.stream().map(Genre::getName).collect(Collectors.toSet());
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format(
"Title: %s, Author: %s, Genres: %s, Rating: %.2f",
          title, author.getName(),genres.stream().map(Genre::getName).collect(Collectors.joining(", ")),rating);
    }
}