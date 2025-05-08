import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String csvFile = "src/Books.csv";
        DatasetManager dm = new DatasetManager(csvFile);

        List<Book> books;
        try {
            books = dm.loadBooks();
        } catch (IOException e) {
            System.err.println("Error loading dataset: " + e.getMessage());
            return;
        }

        BookRecommendationApp app = new BookRecommendationApp(books);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Book Recommendation App!");
        boolean running = true;

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1) Recommend by Genre");
            System.out.println("2) Recommend by Author");
            System.out.println("3) Recommend by Title");
            System.out.println("4) Exit");
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine().trim();
            List<Book> results;

            switch (choice) {
                case "1":
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine().trim();
                    results = app.recommendByGenre(genre);
                    break;
                case "2":
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine().trim();
                    results = app.recommendByAuthor(author);
                    break;
                case "3":
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine().trim();
                    results = app.recommendByTitle(title);
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    continue;
                default:
                    System.out.println("Invalid option. Please try again.");
                    continue;
            }

            if (results.isEmpty()) {
                System.out.println("No recommendations found.");
            } else {
                System.out.println("\nRecommendations:");
                for (Book b : results) {
                    System.out.println("  - " + b);
                }
            }
        }

        scanner.close();
    }
}
