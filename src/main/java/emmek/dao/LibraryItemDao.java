package emmek.dao;

import emmek.entities.LibraryItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryItemDao {

    private final EntityManager em;

    public LibraryItemDao(EntityManager em) {
        this.em = em;
    }

    public void save(LibraryItem item) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(item);
        tx.commit();
        System.out.println("Item " + item.getTitle() + " saved");
    }

    public LibraryItem getById(String id) {
        return em.find(LibraryItem.class, id);
    }

    public void delete(LibraryItem item) {

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(item);
        tx.commit();
        System.out.println("Item " + item.getTitle() + " deleted");

    }

    public void refresh(LibraryItem item) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.refresh(item);
        tx.commit();
        System.out.println("Item refreshed");
    }

    public LibraryItem getRandomItem() {
        return em.createQuery("SELECT i FROM LibraryItem i ORDER BY RAND()", LibraryItem.class).setMaxResults(1).getSingleResult();
    }

    public void deleteIsbn(String isbn) {
        LibraryItem item = getById(isbn);
        if (item != null) {
            System.out.println(isbn);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(item);
            tx.commit();
            System.out.println("Item with isbn " + isbn + " deleted");
        } else {
            System.out.println("Item with isbn " + isbn + " not found");
        }
    }

    public List<LibraryItem> findByPartialIsbn(String isbn) {
        TypedQuery<LibraryItem> query = em.createQuery("SELECT i FROM LibraryItem i WHERE i.isbn LIKE CONCAT('%', :isbn,'%')", LibraryItem.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    public List<LibraryItem> findByYear(int year) {
        TypedQuery<LibraryItem> query = em.createQuery("SELECT i FROM LibraryItem i WHERE i.year = :year", LibraryItem.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public List<LibraryItem> findByAuthor(String author) {
        TypedQuery<LibraryItem> query = em.createQuery("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author,'%'))", LibraryItem.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    public List<LibraryItem> searchByTitle(String title) {
        TypedQuery<LibraryItem> query = em.createQuery("SELECT i FROM LibraryItem i WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :title,'%'))", LibraryItem.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    public List<LibraryItem> getAll() {
        TypedQuery<LibraryItem> query = em.createQuery("SELECT i FROM LibraryItem i", LibraryItem.class);
        return query.getResultList();
    }

    @Override
    public String toString() {

        return getAll().stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));


    }
}
