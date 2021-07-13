package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_SCRIPT = "CREATE TABLE IF NOT EXISTS sys.users (  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY "
            + ",`name` VARCHAR(45) NOT NULL, `lastName` VARCHAR(45) NOT NULL,  `age` INT NOT NULL ) ENGINE=MEMORY;";
    private static final String DELETE_SCRIPT = "DROP TABLE IF EXISTS sys.users ;";
    private static final String INSERT_USER = "INSERT INTO sys.users (name,lastName,age) VALUES (?,?,?)";
    private static final String DELETE_USER = "DELETE  FROM sys.users WHERE id = ?";
    private static final String DELETE_ALL_USERS = "DELETE  FROM sys.users ";
    private static final String FIND_ALL_USERS = "SELECT * FROM sys.users";
    private Util util;
    private Logger log;

    public UserDaoJDBCImpl() {
        util = new Util();
        log = Logger.getLogger("UserDaoJDBCImpl");
    }

    public void createUsersTable() {
        try {
            scriptRun(CREATE_SCRIPT);
            log.log(Level.INFO, "Tables have been created");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant create tebles");
        }
    }

    public void dropUsersTable() {
        try {
            scriptRun(DELETE_SCRIPT);
            log.log(Level.INFO, "Tables have been deleted");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant delete tables");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnetion();
                PreparedStatement prSt = connection.prepareStatement(INSERT_USER)) {
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate();
            log.log(Level.INFO, "User " + name + " have been added");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Cant add user", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant add user", e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnetion();
                PreparedStatement prSt = connection.prepareStatement(DELETE_USER)) {
            prSt.setLong(1, id);
            prSt.executeUpdate();
            log.log(Level.INFO, "User have been deleted");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Cant remove user", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant remove user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        try (Connection connection = util.getConnetion();
                Statement statement = connection.createStatement();
                ResultSet reSet = statement.executeQuery(FIND_ALL_USERS)) {
            while (reSet.next()) {
                userList.add(new User(reSet.getString(2), reSet.getString(3), reSet.getByte(4)) {
                    {
                        setId(reSet.getLong(1));
                    }

                });
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Cant get all users", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant get all users", e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnetion();
                PreparedStatement prSt = connection.prepareStatement(DELETE_ALL_USERS)) {
            prSt.executeUpdate();
            log.log(Level.INFO, "All users have been deleted");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Cant remove users", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant remove users", e);
        }
    }

    private void scriptRun(String script) throws Exception {
        try (Connection conn = util.getConnetion(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(script);
            log.log(Level.INFO, "Tables have been changed");
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Cant run script", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Cant run script", e);
        }
    }
}
