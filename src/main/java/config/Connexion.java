/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almamy
 */
public class Connexion {

    private Connection connection;
    private static Statement statement;

    public Connexion() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/selManagement", "root", "root");
            this.statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection conn() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/selManagement", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.connection;

    }

    public static Statement getStatement() {
        return statement;
    }

}
