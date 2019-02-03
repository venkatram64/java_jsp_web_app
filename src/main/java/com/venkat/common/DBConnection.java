package com.venkat.common;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection implements Serializable {

    private static DBConnection dbConnection;
    private static Connection conn;

    private DBConnection(){}

    private Properties readDBProps() throws IOException{

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;

    }

    public static DBConnection getInstance(){
        return new DBConnection();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        try{
            Properties properties = readDBProps();
            String driver = properties.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver) ;
            }

            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            conn = DriverManager.getConnection(url, username, password);

        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return conn;
    }

    public void closeConnection(){

        try{
            if(conn != null){
                conn.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
