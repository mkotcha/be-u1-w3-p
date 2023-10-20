package emmek.dao;

import emmek.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UserDao {

    private final EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public void save(User user) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(user);
        tx.commit();
        System.out.println("User " + user.getName() + " saved");
    }

    public User getById(long id) {
        return em.find(User.class, id);
    }

    public void delete(User user) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(user);
        tx.commit();
        System.out.println("User " + user.getName() + " deleted");
    }

    public void refresh(User user) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.refresh(user);
        tx.commit();
        System.out.println("User refreshed");
    }

    public User getRandomUser() {
        return em.createQuery("SELECT u FROM User u ORDER BY RAND()", User.class).setMaxResults(1).getSingleResult();
    }

}
