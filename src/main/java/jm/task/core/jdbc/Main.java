package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        //добавиим users
        userService.saveUser("Ivan", "Ivanov", (byte) 19);
        userService.saveUser("Petr", "Petrov", (byte) 45);
        userService.saveUser("Alexandr", "Alexandrov", (byte) 27);
        userService.saveUser("Sergey", "Seregin", (byte) 30);
        List<User> list = userService.getAllUsers();

        for (User u : list) {
            System.out.println(u);
        }

        userService.cleanUsersTable(); //очистим таблицу
        userService.dropUsersTable(); //удалим таблицу
    }
}
