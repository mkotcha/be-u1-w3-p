package emmek;

import emmek.dao.BorrowDao;
import emmek.dao.LibraryItemDao;
import emmek.dao.UserDao;
import emmek.entities.*;
import emmek.utils.JpaUtil;
import net.datafaker.Faker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Application {

    private static final EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
        fakerize();
        System.out.println("hello world");

    }

    public static void menu() {
        Library library = new Library();
        String query;
        List<LibraryItem> result;
        int choice = -1;
        while (choice != 0) {
            System.out.println();
            System.out.println("FAKE LIBRARY SYSTEM 1.0");
            System.out.println();
            System.out.println("make your choice");
            System.out.println();
            System.out.println("1 - add an item to the library");
            System.out.println("2 - remove an item from the library");
            System.out.println("3 - find item by isbn");
            System.out.println("4 - find item by year");
            System.out.println("5 - find item by author");
            System.out.println("6 - save on disk");
            System.out.println("7 - load from disk");
            System.out.println("8 - print all library catalogue");
            System.out.println("0 - exit");
            System.out.println();

            try {
                choice = abs(Integer.parseInt(scanner.nextLine()));
            } catch (NumberFormatException ex) {
                System.err.println("not a number");
            }

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    remItem();
                    break;
                case 3:
                    System.out.println("search by ISBN");
                    query = scanner.nextLine();
                    result = library.findIsbn(query);
                    System.out.println("result:");
                    result.forEach(item -> System.out.print(item.toString()));
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.println("search by year");
                    query = scanner.nextLine();
                    result = library.findYear(query);
                    System.out.println("result:");
                    result.forEach(item -> System.out.print(item.toString()));
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("search by author");
                    query = scanner.nextLine().toLowerCase();
                    result = library.findAuthor(query);
                    System.out.println("result:");
                    result.forEach(item -> System.out.print(item.toString()));
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 6:
                    library.save();
                    break;
                case 7:
                    library.load();
                    System.out.println(library.toString());
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 8:
                    System.out.println();
                    System.out.println(library.toString());
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("0");
                    break;
            }
        }
    }

    private static void remItem() {
        Library library = new Library();
        int choice = -1;
        String isbn;
        while (choice != 0) {
            System.out.println();
            System.out.println("1 - remove an ISBN");
            System.out.println("2 - remove from a numbered list");
            System.out.println("0 - exit");

            try {
                choice = abs(Integer.parseInt(scanner.nextLine()));
            } catch (NumberFormatException ex) {
                System.err.println("not a number");
            }

            if (choice == 1) {
                System.out.println();
                System.out.println(library.toString());
                System.out.println();
                System.out.println("enter an ISBN (10 number)");
                try {
                    isbn = scanner.nextLine();
                    library.rem(isbn);
                    System.out.println(library.toString());
                } catch (Exception ex) {
                    System.err.println("wrong search filter");
                }

            }
            if (choice == 2) {
                choice = -1;
                while (choice != 0) {
                    System.out.println();
                    System.out.println("chose an item to remove from catalogue - 0 to exit");
                    library.printIndex();
                    try {
                        choice = abs(Integer.parseInt(scanner.nextLine()));
                        if (choice > 0) library.rem(choice - 1);
                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                    }
                }
            }

        }
    }

    private static void addItem() {
        Library library = new Library();
        String isbn;
        String title;
        String year;
        int pages;
        Periodicity[] periodicityArr = Periodicity.values();
        Periodicity periodicity = null;
        int choice = -1;
        while (choice != 0) {
            System.out.println("1 - add a book");
            System.out.println("2 - add a magazine");
            System.out.println("0 - back");
            try {
                choice = abs(Integer.parseInt(scanner.nextLine()));
            } catch (NumberFormatException ex) {
                System.err.println("not a number");
            }
            switch (choice) {
                case 1:
                    System.out.println("ISBN");
                    isbn = scanner.nextLine();
                    System.out.println("title");
                    title = scanner.nextLine();
                    System.out.println("year");
                    year = scanner.nextLine();
                    System.out.println("pages");
                    try {
                        pages = abs(Integer.parseInt(scanner.nextLine()));
                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                        break;
                    }
                    System.out.println("author");
                    String author = scanner.nextLine();
                    System.out.println("genre");
                    String genre = scanner.nextLine();
                    library.add(new Book(isbn, title, year, pages, author, genre));
                    System.out.println(library.toString());
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    choice = 0;
                    break;
                case 2:
                    System.out.println("ISBN");
                    isbn = scanner.nextLine();
                    System.out.println("title");
                    title = scanner.nextLine();
                    System.out.println("year");
                    year = scanner.nextLine();
                    System.out.println("pages");
                    try {
                        pages = abs(Integer.parseInt(scanner.nextLine()));
                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                        break;
                    }
                    int periodicityChoice = -1;
                    while (periodicityChoice != 0) {
                        System.out.println("periodicity");
                        for (int i = 0; i < periodicityArr.length; i++) {
                            System.out.println((i + 1) + " - " + periodicityArr[i]);
                        }
                        try {
                            periodicityChoice = abs(Integer.parseInt(scanner.nextLine()));
                        } catch (NumberFormatException ex) {
                            System.err.println("not a number");
                        }
                        periodicity = periodicityArr[periodicityChoice - 1];
                        periodicityChoice = 0;
                    }
                    library.add(new Magazine(isbn, title, year, pages, periodicity));
                    System.out.println(library.toString());
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    choice = 0;
                    break;
                case 0:
                    System.out.println("ciao");
                    break;
            }
        }
    }

    public static void fakerize() {
        EntityManager em = emf.createEntityManager();
        LibraryItemDao library = new LibraryItemDao(em);
        UserDao userDao = new UserDao(em);
        BorrowDao borrowDao = new BorrowDao(em);
        Faker faker = new Faker();
        Random rnd = new Random();
        Book book;
        Magazine magazine;
        Borrow borrow;
        User user;
        for (int i = 0; i < 4; i++) {
            Periodicity[] periodicity = Periodicity.values();
            if (rnd.nextBoolean()) {
                book = new Book(faker.code().isbn10(),
                        faker.book().title(),
                        faker.date().birthday("yyyy"),
                        faker.number().numberBetween(35, 1000),
                        faker.book().author(),
                        faker.book().genre());
                library.save(book);
            } else {
                magazine = new Magazine(faker.code().isbn10(),
                        faker.yoda().quote(),
                        faker.date().birthday("yyyy"),
                        faker.number().numberBetween(16, 100),
                        periodicity[rnd.nextInt(periodicity.length)]);
                library.save(magazine);
            }
            user = new User(faker.name().firstName(), faker.name().lastName(), faker.date().birthday().toLocalDateTime().toLocalDate());
            userDao.save(user);
            borrow = new Borrow(library.getRandomItem(), userDao.getRandomUser(), LocalDate.now());
            try {
                borrowDao.save(borrow);
            } catch (Exception ex) {
                System.err.println("item already borrowed " + ex.getMessage());
            }
        }
    }
}


