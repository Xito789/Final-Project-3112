import java.util.ArrayList;
import java.util.List;

public class Genre {
    private String name;
    private List<Book> books = new ArrayList<>();

     public Genre(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString(){
        return name;
    }
}