package com.dmitri.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MyDataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String PASSWORD = "12345";
    private static final String USER = "root";
    private static final String DB_NAME = "db_clinic";

    /**
     * Full path to MYSQL db with credentials: psw, user and db name
     */
    private static final String TEMPLATE = "%s%s?user=%s&password=%s";

    private static Connection instance;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MyDataBaseConnection() {
        // not use
    }


    public static Connection getInstance() {
        if (instance == null) {
            String fullUrlToDb = String.format(TEMPLATE, URL, DB_NAME, USER, PASSWORD);
            try {
                instance = DriverManager.getConnection(fullUrlToDb);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return instance;
    }


    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

}
