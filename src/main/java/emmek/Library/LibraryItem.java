package emmek.Library;

public class LibraryItem {
    private final String isbn;

    private final String title;

    private final String year;
    private final int pages;


    public LibraryItem(String isbn, String title, String year, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return
                "isbn='" + isbn + '\'' +
                        ", title='" + title + '\'' +
                        ", year='" + year + '\'' +
                        ", pages=" + pages;

    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }
}
