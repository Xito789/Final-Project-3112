import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatasetManager{
       private final String csvPath;
       private static final String unknownGenre = "Unknown";

    public DatasetManager(String csvPath){
        this.csvPath = csvPath;
    }

    public List<Book> loadBooks() throws IOException {
        Map<String, Author> authors = new HashMap<>();
        Map<String, Genre> genres = new HashMap<>();
        List<Book> books = new ArrayList<>();

        genres.putIfAbsent(unknownGenre, new Genre(unknownGenre));

        try(BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String line = reader.readLine();

            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }

                String[] cols = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (cols.length < 6) {
                    continue;
                }

                String title = cols[1];
                String authorName = cols[2];
                String genresField = cols[4];
                double avgRating = 0.0;
                try {
                    avgRating = Double.parseDouble(cols[5]);
                } catch (NumberFormatException ignored) {}

                Author author = authors.computeIfAbsent(authorName, Author::new);
                List<String> genreNames = parseGenresField(genresField);

                List<Genre> bookGenres = new ArrayList<>();
            for (String gName : genreNames) {
                Genre g = genres.computeIfAbsent(gName, Genre::new);
                bookGenres.add(g);
            }

            Book book = new Book(title, author, bookGenres, avgRating);

            author.addBook(book);
            for (Genre g : bookGenres) {
                g.addBook(book);
            }
            books.add(book);
           
        }
    }

    return books;
}
    private List<String> parseGenresField(String field) {
        
        String trimmed = field.trim();
        if (trimmed.length() < 2) return List.of(unknownGenre);
        trimmed = trimmed.substring(1, trimmed.length() - 1);

        
        String[] parts = trimmed.split("', '");
        List<String> result = new ArrayList<>();
        for (String p : parts) {
            
            String name = p.replaceAll("^['\"]|['\"]$", "").trim();
            if (!name.isEmpty()) {
                result.add(name);
            }
        }
       
        if (result.isEmpty()) result.add(unknownGenre);
        return result;
    }
}

