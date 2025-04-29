package com.mycompany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // Replace with your actual MySQL connection details
        String url = "jdbc:mysql://localhost:3306/customer_survey";  // Database URL
        String user = "root";  // Database username
        String password = "0000";  // Database password

        return DriverManager.getConnection(url, user, password);
    }
}
