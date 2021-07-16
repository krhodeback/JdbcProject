package jm.task.core.jdbc.dao;

import java.util.EnumSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoHibernateImpl implements UserDao {
    private Util util;
    private EnumSet<TargetType> enumSet;

    public UserDaoHibernateImpl() {
        util = new Util();
        enumSet = EnumSet.of(TargetType.DATABASE);
    }

    @Override
    public void createUsersTable() {
        new SchemaExport().create(enumSet, util.getMetadata());
    }

    @Override
    public void dropUsersTable() {
        new SchemaExport().drop(enumSet, util.getMetadata());
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
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception e1) {
                System.out.println("Cant rollback : " + e1.getMessage());
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
            User user = (User) session.load(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("User " + user.getName() + " have been removed");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Cant remove user : " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSession();
        List<User> userList = null;
        try {
            userList = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            System.out.println("Cant get all users : " + e.getMessage());
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        List<User> userList = getAllUsers();
        for (User user : userList) {
            removeUserById(user.getId());
        }

    }
}
