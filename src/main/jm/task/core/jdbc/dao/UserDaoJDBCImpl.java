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
    private Util util;

    public UserDaoJDBCImpl() {
        util = new Util();
    }

    public void createUsersTable() {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = util.getConnetion();
            statement = conn.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS sys.users (  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY "
                            + ",`name` VARCHAR(45) NOT NULL, `lastName` VARCHAR(45) NOT NULL,  `age` INT NOT NULL ) ENGINE=MEMORY;");
            conn.commit();
            System.out.println("User table have been created");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant create table:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant create table:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = util.getConnetion();
            statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS sys.users ;");
            conn.commit();
            System.out.println("User table have been droped");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant drop table:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant drop table:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection conn = null;
        PreparedStatement prSt = null;
        try {
            conn = util.getConnetion();
            prSt = conn.prepareStatement("INSERT INTO sys.users (name,lastName,age) VALUES (?,?,?)");
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setByte(3, age);
            prSt.executeUpdate();
            conn.commit();
            System.out.println("User have been saved");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant save user:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant save user:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (prSt != null) {
                try {
                    prSt.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection conn = null;
        PreparedStatement prSt = null;
        try {
            conn = util.getConnetion();
            prSt = conn.prepareStatement("DELETE  FROM sys.users WHERE id = ?");
            prSt.setLong(1, id);
            prSt.executeUpdate();
            conn.commit();
            System.out.println("User have been removed");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant delete user:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant delete user:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (prSt != null) {
                try {
                    prSt.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        Connection conn = null;
        Statement statement = null;
        ResultSet reSet;
        try {
            conn = util.getConnetion();
            statement = conn.createStatement();
            reSet = statement.executeQuery("SELECT * FROM sys.users");
            conn.commit();
            while (reSet.next()) {
                userList.add(new User(reSet.getString(2), reSet.getString(3), reSet.getByte(4)) {
                    {
                        setId(reSet.getLong(1));
                    }

                });
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant get users:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant get users:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection conn = null;
        PreparedStatement prSt = null;
        try {
            conn = util.getConnetion();
            prSt = conn.prepareStatement("DELETE  FROM sys.users ");
            prSt.executeUpdate();
            conn.commit();
            System.out.println("User table have been cleaned");
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant clean user table:" + e.getMessage());
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("An error occurred while trying to rollback :" + ex.getMessage());
            }
            System.out.println("Cant clean user table:" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
            if (prSt != null) {
                try {
                    prSt.close();
                } catch (SQLException e) {
                    System.out.println("Cant close connection:" + e.getMessage());
                }
            }
        }
    }

}
