package com.example.hopital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager() {
        // Le constructeur ne fait rien de spécifique ici, la connexion est gérée directement dans getConnection
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Créer une nouvelle connexion à chaque fois que getConnection est appelée
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/java_hopital", "root", "");
            System.out.println("Connected to the database successfully!");
            return con;
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL JDBC Driver not found.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Error connecting to the database.");
            ex.printStackTrace();
        }
        return null;
    }
}
