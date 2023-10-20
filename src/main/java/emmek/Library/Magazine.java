package emmek.Library;

public class Magazine extends LibraryItem {
    private Periodicity periodicity;

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
