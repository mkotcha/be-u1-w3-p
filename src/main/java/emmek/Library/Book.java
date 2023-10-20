package emmek.Library;

public class Book extends LibraryItem {
    private final String author;
    private final String genre;

    public Book(String isbn, String title, String year, int pages, String author, String genre) {
        super(isbn, title, year, pages);
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                super.toString() +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +

                "}\n";
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }
}
