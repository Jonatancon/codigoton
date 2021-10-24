package reto.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost/evalart_reto";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public Connection connecDB(){
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            return connection;
        }catch (Exception e){
            System.out.println(e + "Error Connection");
            return null;
        }
    }
}
