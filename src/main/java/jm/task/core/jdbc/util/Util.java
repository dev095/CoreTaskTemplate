package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/testdb?characterEncoding=utf-8&useSSL=false";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "s1938740";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            System.out.println("Connection > OK");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
//            System.out.println("Connection > ERROR");
        }
        return connection;
    }
}
