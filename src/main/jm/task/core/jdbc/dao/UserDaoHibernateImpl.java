package jm.task.core.jdbc.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoHibernateImpl implements UserDao {
    private Util util;

    public UserDaoHibernateImpl() {
        util = new Util();
    }

    @Override
    public void createUsersTable() {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS sys.users (  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY "
                            + ",`name` VARCHAR(45) NOT NULL, `lastName` VARCHAR(45) NOT NULL,  `age` INT NOT NULL ) ENGINE=MEMORY")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Table have been created");
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception e1) {
                    System.out.println("Cant rollback : " + e1.getMessage());
                }
            }
            System.out.println("Cant create table : " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS sys.users ").executeUpdate();
            transaction.commit();
            System.out.println("Table have been deleted");
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception e1) {
                    System.out.println("Cant rollback : " + e1.getMessage());
                }
            }
            System.out.println("Cant delete teble : " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getSession();
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User " + user.getName() + " have been saved");
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception e1) {
                    System.out.println("Cant rollback : " + e1.getMessage());
                }
            }
            System.out.println("Cant save user : " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id= :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User have been deleted");
        } catch (Exception e) {
            System.out.println("Cant delete user : " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSession();
        List<User> userList = null;
        try {
            userList = session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            System.out.println("Cant get all users : " + e.getMessage());
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM sys.users").executeUpdate();
            transaction.commit();
            System.out.println("Table have been cleaned");
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception e1) {
                    System.out.println("Cant rollback : " + e1.getMessage());
                }
            }
            System.out.println("Cant cleane table : " + e.getMessage());
        } finally {
            session.close();
        }
    }

}
