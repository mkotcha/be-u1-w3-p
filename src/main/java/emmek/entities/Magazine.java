package emmek.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "magazines")
public class Magazine extends LibraryItem {
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    public Magazine() {
    }

    public Magazine(String isbn, String title, String year, int pages, Periodicity periodicity) {
        super(isbn, title, year, pages);
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                super.toString() +
                ", periodicity=" + periodicity +
                "}\n";
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }
}
