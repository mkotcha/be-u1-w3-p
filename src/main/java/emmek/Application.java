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
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class Application {

    private static final EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    private static final EntityManager em = emf.createEntityManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();
//        fakerize();
        menu();

    }

    public static void menu() {
        LibraryItemDao libraryDao = new LibraryItemDao(em);
        UserDao userDao = new UserDao(em);
        BorrowDao borrowDao = new BorrowDao(em);
        String query;
        List<LibraryItem> result = null;
        List<User> userList;
        List<Borrow> borrowList;
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
            System.out.println("6 - find item borrowed by user");
            System.out.println("7 - show expired borrows");
            System.out.println("8 - show unreturned items");
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
                    result = libraryDao.findByPartialIsbn(query);
                    System.out.println("result:");
                    result.forEach(item -> System.out.print(item.toString()));
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.println("search by year");
                    try {
                        int year = abs(Integer.parseInt(scanner.nextLine()));
                        result = libraryDao.findByYear(year);
                        System.out.println("result:");
                        result.forEach(item -> System.out.print(item.toString()));

                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                    }
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("search by author");
                    query = scanner.nextLine().toLowerCase();
                    result = libraryDao.findByAuthor(query);
                    System.out.println("result:");
                    result.forEach(item -> System.out.print(item.toString()));
                    System.out.println();
                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.println("find item borrowed by user");
                    System.out.println("enter your card number");
                    try {
                        int card = abs(Integer.parseInt(scanner.nextLine()));
                        User user = userDao.getById(card);
                        System.out.println("result:");
                        borrowList = user.getBorrows();
                        System.out.println("\n" + borrowList.size() + " items for " + user.toString() + "\n");
                        borrowList.forEach(borrow -> {
                            System.out.println(borrow.toString());
                            System.out.println("Expired: " + borrow.isExpired());
                        });
                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                    }

                    System.out.println("press enter to continue");
                    scanner.nextLine();
                    break;
                case 7:
                    borrowList = borrowDao.findExpiredBorrow();
                    System.out.println(borrowList.size() + " items expired\n");
                    borrowList.forEach(borrow -> System.out.println(borrow.toString()));
                    System.out.println("press enter to continuee");
                    scanner.nextLine();
                    break;
                case 8:
                    borrowList = borrowDao.showUnreturnedItems();
                    System.out.println(borrowList.size() + " items not returned\n");
                    borrowList.forEach(borrow -> System.out.println(borrow.toString()));
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
        LibraryItemDao library = new LibraryItemDao(em);
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
                System.out.println(library.toString());
                try {
                    isbn = scanner.nextLine();
                    library.deleteIsbn(isbn);
                } catch (NumberFormatException ex) {
                    System.err.println("wrong search filter no deleted item");
                }
                System.out.println(library.toString());

            }
            if (choice == 2) {
                choice = -1;
                int index = -1;
                while (index != 0) {
                    System.out.println();
                    System.out.println("chose an item to remove from catalogue - 0 to exit\n");
                    try {
                        List<LibraryItem> libraryList = library.getAll();
                        AtomicInteger i = new AtomicInteger(1);
                        libraryList.forEach(item -> System.out.println(i.getAndIncrement() + " " + item.toString()));
                        try {
                            index = abs(Integer.parseInt(scanner.nextLine()));
                            library.delete(libraryList.get(index - 1));
                        } catch (NumberFormatException ex) {
                            System.err.println("not a number");
                        } catch (IndexOutOfBoundsException ex) {
                            if (index != 0) System.err.println("wrong index");
                        }
                        System.out.println(library.toString());
                    } catch (NumberFormatException ex) {
                        System.err.println("not a number");
                    }
                }
            }

        }
    }

    private static void addItem() {

        LibraryItemDao library = new LibraryItemDao(em);
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
                    library.save(new Book(isbn, title, year, pages, author, genre));
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
                    library.save(new Magazine(isbn, title, year, pages, periodicity));
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
            LocalDate randomDay = LocalDate.now().minusDays(faker.number().numberBetween(1, 60));
            borrow = new Borrow(library.getRandomItem(), userDao.getRandomUser(), randomDay);
            try {
                borrowDao.save(borrow);
            } catch (Exception ex) {
                System.err.println("item already borrowed " + ex.getMessage());
            }
        }
    }
}


