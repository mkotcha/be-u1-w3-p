package emmek.dao;

import emmek.entities.Borrow;
import emmek.entities.LibraryItem;
import emmek.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class BorrowDao {

    private final EntityManager em;

    public BorrowDao(EntityManager em) {
        this.em = em;
    }

    public void save(Borrow borrow) {
        if (isItemAvaiable(borrow.getItem())) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(borrow);
            tx.commit();
            System.out.println("Borrow " + borrow.getItem().getTitle() + " saved");
        } else {
            System.out.println("Item " + borrow.getItem().getTitle() + " is not avaiable");
        }
    }

    public Borrow getById(long id) {
        return em.find(Borrow.class, id);
    }

    public void delete(Borrow borrow) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(borrow);
        tx.commit();
        System.out.println("Borrow " + borrow.getId() + " deleted");
    }

    public void refresh(Borrow borrow) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.refresh(borrow);
        tx.commit();
        System.out.println("Borrow refreshed");
    }

    public List<Borrow> findUserBorrow(User user) {
        TypedQuery<Borrow> query = em.createQuery("SELECT b FROM Borrow b WHERE b.user = :user", Borrow.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Borrow> findExpiredBorrow() {
        List<Borrow> borrows = findAll();
        return borrows.stream().filter(Borrow::isExpired).toList();
    }

    private List<Borrow> findAll() {
        TypedQuery<Borrow> query = em.createQuery("SELECT b FROM Borrow b", Borrow.class);
        return query.getResultList();
    }

    public List<Borrow> showUnreturnedItems() {
        TypedQuery<Borrow> query = em.createQuery("SELECT b FROM Borrow b WHERE b.dateTo = null", Borrow.class);
        return query.getResultList();
    }

    public boolean isItemAvaiable(LibraryItem item) {
        TypedQuery<Borrow> query = em.createQuery("SELECT b FROM Borrow b WHERE b.item = :item AND b.dateTo = null", Borrow.class);
        query.setParameter("item", item);
        return query.getResultList().isEmpty();
    }
}
