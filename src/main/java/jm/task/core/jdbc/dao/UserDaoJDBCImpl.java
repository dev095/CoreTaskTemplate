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
        } catch (Exception e) {
            System.err.println("Failed to create table.");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + DB_TABLE);
        } catch (Exception e) {
            System.err.println("Failed to drop table.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO " + DB_TABLE + "(name , lastName, age) " +
                    " values ('" + name + "', '" + lastName + "', '" + age + "')");
            System.out.println("User named " + name + " added to database.");
        } catch (Exception e) {
            System.err.println("Failed to save user.");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + DB_TABLE + " WHERE id = " + id);
        } catch (Exception e) {
            System.err.println("Failed to delete user.");
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT  * FROM " + DB_TABLE);

            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getByte(4));
                users.add(user);
            }
        } catch (Exception e) {
            System.err.println("Failed to return user list.");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE " + DB_TABLE);
        } catch (Exception e) {
            System.err.println("Failed to clear table.");
        }
    }
}
