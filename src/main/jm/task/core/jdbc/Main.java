package jm.task.core.jdbc;

import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("user1", "lastName1", (byte) 21);
        user.saveUser("user2", "lastName2", (byte) 21);
        user.saveUser("user3", "lastName3", (byte) 21);
        user.saveUser("user4", "lastName4", (byte) 21);
        System.out.println(user.getAllUsers());
        user.removeUserById(1l);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
