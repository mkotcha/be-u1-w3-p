package emmek.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "item_id", unique = true)
    private LibraryItem item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    public Borrow() {
    }

    public Borrow(LibraryItem item, User user, LocalDate dateFrom) {
        this.item = item;
        this.user = user;
        this.dateFrom = dateFrom;
    }

    public int getId() {
        return id;
    }

    public LibraryItem getItem() {
        return item;
    }

    public void setItem(LibraryItem item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isExpired() {
        LocalDate limit = dateFrom.plusDays(30);

        return Objects.requireNonNullElseGet(dateTo, LocalDate::now).isAfter(limit);

    }

    @Override
    public String toString() {
        return "Borrow " +
                "id=" + id +
                "\n item=" + item +
                " user=" + user +
                "\n dateFrom=" + dateFrom +
                ", dateTo=" + dateTo + " ";
    }
}
