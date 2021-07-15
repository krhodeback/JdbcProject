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
        session.save(new User(name, lastName, age));
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        User user = (User) session.load(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSession();
        List<User> userList = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        session.close();
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
