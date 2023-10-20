package emmek.entities;

import javax.persistence.*;

@Entity
@Table(name = "library_items")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class LibraryItem {

    @Id
    private String isbn;

    @OneToOne(mappedBy = "item", cascade = CascadeType.REMOVE)
    private Borrow borrow;

    private String title;

    private String year;
    private int pages;

    public LibraryItem() {

    }

    public LibraryItem(String isbn, String title, String year, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.pages = pages;
    }

    public Borrow getBorrow() {
        return borrow;
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
