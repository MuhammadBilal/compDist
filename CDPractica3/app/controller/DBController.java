
// @author: Pablo Varela
// 
// Database controller for mysql

package controller;

import java.sql.*;

public class DBController {
    private final String controlador = "com.mysql.jdbc.Driver";
    private final String ip = "localhost";
    private final String port = "3306";
    private final String name_DB = "meteochatbd";
    private final String URL_DB = "jdbc:mysql://"+ip+":"+port+"/"+name_DB;
    private final String username = "adminmeteo";
    private final String password = "1234";
    private Connection connection;

    // Constructor
    public DBController() {
        if ( checkDriver() ) {
            openConnection();
            return;
        }

        System.out.println("Missing database driver");
    }

    // returns true if driver for mysql exists
    private boolean checkDriver() {
        try {
            Class.forName(controlador).newInstance();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // Get the connection object
    public Connection getConnection() {
        return this.connection;
    }
    
    // Initialize the connection with the database
    public void openConnection() {
        try {
             connection = DriverManager.getConnection(URL_DB, username, password);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Close connection with the DB
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
