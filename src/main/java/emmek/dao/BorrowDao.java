package emmek.dao;

import emmek.entities.Borrow;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BorrowDao {

    private final EntityManager em;

    public BorrowDao(EntityManager em) {
        this.em = em;
    }

    public void save(Borrow borrow) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(borrow);
        tx.commit();
        System.out.println("Borrow " + borrow.getId() + " saved");
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

}
