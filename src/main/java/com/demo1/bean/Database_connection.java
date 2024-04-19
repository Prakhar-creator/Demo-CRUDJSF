/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.demo1.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database_connection {

    public static void main(String[] args) {
        Connection connection = null;
         Statement statement = null;
         ResultSet resultSet = null;

        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Database URL, username, and password
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "1234";

            // Establish a connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            if (connection != null) {
                System.out.println("Connected to the PostgreSQL database!");

                // Execute a query
                String query = "SELECT * FROM public.\"Students\""; // Replace with your table name
                resultSet = statement.executeQuery(query);

                // Process the result set
                while (resultSet.next()) {
                    int id = resultSet.getInt("id"); // Replace with your column name
                    String name = resultSet.getString("name"); // Replace with your column name

                    System.out.println("ID: " + id + ", Name: " + name);
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failure!");
            e.printStackTrace();
        } finally {
            // Close the connection in the finally block
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
