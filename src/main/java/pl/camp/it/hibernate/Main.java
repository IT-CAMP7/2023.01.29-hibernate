package pl.camp.it.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class Main {
    public static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        User user = new User(7, "karol", "karol", User.Role.USER);

        //persistUser(user);
        //updateUser(user);
        //deleteUser(user);

        //List<User> users = getAllUsers();
        //System.out.println(users);
        //getUserById(6).ifPresent(System.out::println);

        /*User user2 = new User();
        user2.setLogin("wiesiek2");
        user2.setPassword("wiesiek234");
        user2.setRole(User.Role.ADMIN);

        Order order = new Order();
        order.setPrice(150.00);
        order.setUser(user2);

        saveOrder(order);

        System.out.println(order.getId());
        System.out.println(order.getUser().getId());*/

        Optional<Order> orderBox = getOrderById(2);
        System.out.println("Order wyciagniety !!");
        orderBox.ifPresent(System.out::println);
    }

    public static void persistUser(User user) {
        Session session = Main.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void updateUser(User user) {
        Session session = Main.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static void deleteUser(User user) {
        Session session = Main.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(user);
            System.out.println("Deleting user with id : " + user.getId());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static List<User> getAllUsers() {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.hibernate.User", User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    public static Optional<User> getUserById(int id) {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.hibernate.User WHERE id = :id", User.class);
        query.setParameter("id", id);
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (NoResultException e) {}
        session.close();
        return result;
    }

    public static void saveOrder(Order order) {
        Session session = Main.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public static Optional<Order> getOrderById(int id) {
        Session session = Main.sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.camp.it.hibernate.Order WHERE id = :id", Order.class);
        query.setParameter("id", id);
        Optional<Order> result = Optional.empty();
        try {
            result = Optional.of(query.getSingleResult());
        } catch (Exception e) {}
        session.close();
        return result;
    }
}
