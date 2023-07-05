/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.selManagement.Controller;

import config.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almamy
 */
public class LoginConnection {

    private String username;
    private String password;
    private ResultSet rs;
    private int re = 0;

    /**
     * Verifie les differentes regle de connection d'un utilisateur
     *
     * @param username
     * @param password
     * @return boolean
     */
    public int login(String username, String password) {
        this.username = username;
        this.password = password;
        new Connexion();
        Statement conn = Connexion.getStatement();
        System.out.println(conn);
        try {
//            this.rs = conn.executeQuery("SELECT * FROM users WHERE username=" + this.username + " or email=" + this.username);
            this.rs = conn.executeQuery("SELECT * FROM users");

            while (rs.next()) {
            System.out.println(this.rs.getInt("id"));
                if ((this.rs.getString("password").equals(this.password))) {
                    this.re = this.rs.getInt("id");
                } else {
                    this.re = 0;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.re;
    }

}
