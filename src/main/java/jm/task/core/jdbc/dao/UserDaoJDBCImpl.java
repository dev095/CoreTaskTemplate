package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();
    private final String DB_TABLE = "users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " + DB_TABLE + "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY , name VARCHAR(35) NOT NULL, " +
                    "lastName VARCHAR(35) NOT NULL, age MEDIUMINT NOT NULL) DEFAULT CHARSET=UTF8");
            System.out.println("Создание таблицы - OK");
        } catch (Exception e) {
            System.err.println("Создание таблицы - ERROR");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + DB_TABLE);
            System.out.println("Удаление таблицы - OK");
        } catch (Exception e) {
            System.err.println("Удаление таблицы - ERROR");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO " + DB_TABLE + "(name , lastName, age) " +
                    " values ('" + name + "', '" + lastName + "', '" + age + "')");
            System.out.println("Сохранение пользователя " + name + " - OK");
        } catch (Exception e) {
            System.err.println("Сохранение пользователя " + name + " - ERROR");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + DB_TABLE + " WHERE id = " + id);
            System.out.println("Удаление пользователя с ID: " + id + " - OK");
        } catch (Exception e) {
            System.err.println("Удаление пользователя с ID: " + id + " - ERROR");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM " + DB_TABLE);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4));
                users.add(user);
            }
            System.out.println("Печать всех пользователей - ОК");
        } catch (Exception e) {
            System.err.println("Печать всех пользователей - ERROR");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE " + DB_TABLE);
            System.out.println("Очистка таблицы - ОК");
        } catch (Exception e) {
            System.err.println("Очистка таблицы - ERROR");
        }
    }
}
