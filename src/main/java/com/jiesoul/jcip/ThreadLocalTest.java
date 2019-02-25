package com.jiesoul.jcip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadLocalTest {

    public static final String DB_URL = "";

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        public Connection initialValue() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
