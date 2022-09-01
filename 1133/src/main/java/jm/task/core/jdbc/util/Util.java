package jm.task.core.jdbc.util;





import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp101";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Bdfyxbr9";
    //private static Connection connection;
    //private static Connection connection;


    public static Connection getConnection (){
        try {
            Driver drive = new Driver();
            DriverManager.registerDriver(drive);
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
