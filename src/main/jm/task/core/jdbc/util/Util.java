package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import jm.task.core.jdbc.model.User;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1234";
    private static final String DRIVERS = "com.mysql.cj.jdbc.Driver";
    private Configuration configuration;
    private ServiceRegistry serviceRegistry;

    public Util() {
    }

    public Connection getConnetion() throws Exception {
        try {
            Class.forName(DRIVERS);
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

    public Session getSession() {
        Properties prop = new Properties();
        prop.setProperty("connection.driver_class", DRIVERS);
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("hibernate.connection.username", USER_NAME);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("hibernate.connection.autocommit", "false");
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hbm2ddl.auto", "none");
        configuration = new Configuration().addAnnotatedClass(User.class).setProperties(prop);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        return sf.openSession();
    }

}
