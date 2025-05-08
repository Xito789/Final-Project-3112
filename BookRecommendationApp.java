import java.util.*;
import java.util.stream.Collectors;

public class BookRecommendationApp {
    private List<Book> books;

    public BookRecommendationApp(List<Book> books) {
        this.books = books;
    }

    public List<Book> recommendByGenre(String genreQuery) {
        List<Book> recs = new ArrayList<>();
        String q = genreQuery.toLowerCase();

        for (Book b : books) {
            for (Genre g : b.getGenres()) {
                if (g.getName().toLowerCase().contains(q)) {
                    recs.add(b);
                    break;
                }
            }
        }
        recs.sort(Comparator.comparingDouble(Book::getRating).reversed());
        return recs;
    }

    public List<Book> recommendByTitle(String titleQuery) {
    String q = titleQuery.toLowerCase();

    Optional<Book> seedOpt = books.stream()
        .filter(b -> b.getTitle().toLowerCase().contains(q))
        .findFirst();

    if (seedOpt.isEmpty()) return Collections.emptyList();
    Book seed = seedOpt.get();
    Set<String> seedGenres = seed.getGenreNames();

    return books.stream().filter(b -> b != seed).map(b -> Map.entry(b, overlap(seedGenres, b.getGenreNames())))
        .filter(e -> e.getValue() > 0).sorted(Comparator.<Map.Entry<Book,Integer>>comparingInt(Map.Entry::getValue).reversed()
        .thenComparing(e -> -e.getKey().getRating())).map(Map.Entry::getKey).limit(10).collect(Collectors.toList());
    }

    public List<Book> recommendByAuthor(String authorQuery) {
        List<Book> recs = new ArrayList<>();
        String q = authorQuery.toLowerCase();

        for (Book b : books) {
            if (b.getAuthor().getName().toLowerCase().contains(q)) {
                recs.add(b);
            }
        }
        
        recs.sort(Comparator.comparingDouble(Book::getRating).reversed());

        return recs;
    }

    public List<Book> getBooks() {
        return books;
    }

    private int overlap(Set<String> a, Set<String> b) {
        int cnt = 0;
        for (String g : a) if (b.contains(g)) cnt++;
        return cnt;
    }
}
