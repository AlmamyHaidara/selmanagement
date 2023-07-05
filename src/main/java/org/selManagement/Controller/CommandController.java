package org.selManagement.Controller;

import config.Connexion;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommandController {
    private Connection conn;
    int userId = 0;

    public CommandController(int userId) {
        this.userId = userId;
        new Connexion();
        this.conn = new Connexion().conn();
    }

    public void createCommand(String nom, String compagnie, String productName, int quantiter, String email, String address, String telephone, String montant, String payementEtat, String status) throws SQLException {

        if (nom.equals("") || quantiter == 0 || address.equals("") || telephone.equals("")) {
            System.out.println("Nom:" + nom);
            System.out.println("compagnie:" + compagnie);
            System.out.println("productName:" + productName);
            System.out.println("quantiter:" + quantiter);
            System.out.println("email:" + email);
            System.out.println("address:" + address);
            System.out.println("telephone:" + telephone);
            System.out.println("montant:" + montant);
            System.out.println("payementEtat:" + payementEtat);
            System.out.println("status:" + status);
            System.out.println("Creation is failed");
        } else {

            String sqlInsertCustommer = "INSERT INTO customer (nom,compagnie,email,address,telephone) VALUE (?,?,?,?,?)";
            String sqlInsertCommand = "INSERT INTO command (date,user_id,client_id,numFacture,etat,etatPayement) VALUE (?,?,?,?,?,?)";
            String SelectProduct = "SELECT * FROM product where nomProduct = ?";
            String updateQuantiter = "UPDATE product set quantiter = ? where nomProduct = ?";
            String insertProductSeled = "INSERT INTO produitVente (command_id, produit_id, quantiter,montant) VALUE (?,?,?,?)";


            int commandId = 0;
            int productID = 0;
            double prixUnitProd = 0.0;
            int qt = quantiter;

            //Select a product spacified to the data base
            PreparedStatement stmpProduct = this.conn.prepareStatement(SelectProduct, Statement.RETURN_GENERATED_KEYS);
            stmpProduct.setString(1, productName);
            ResultSet rep = stmpProduct.executeQuery();

            while (rep.next()) {
                if (rep.getInt("quantiter") != 0 && rep.getInt("quantiter") >= quantiter) {
                    quantiter = rep.getInt("quantiter") - quantiter;
                    productID = rep.getInt("id");
                    prixUnitProd = rep.getDouble("prixUnit");
                    PreparedStatement stmpQuantiter = this.conn.prepareStatement(updateQuantiter);
                    stmpQuantiter.setInt(1, quantiter);
                    stmpQuantiter.setString(2, productName);
                    System.out.println(productName + " ================================================" + quantiter);
                    stmpQuantiter.executeUpdate();
                    System.out.println("================================================" + quantiter);

//                            while (req.next()) {
//                                req.getInt("quantiter");
//                            }


                    PreparedStatement stmpCustomer = this.conn.prepareStatement(sqlInsertCustommer, Statement.RETURN_GENERATED_KEYS);
                    stmpCustomer.setString(1, nom);
                    stmpCustomer.setString(2, compagnie);
                    stmpCustomer.setString(3, email);
                    stmpCustomer.setString(4, address);
                    stmpCustomer.setString(5, telephone);
//            stmpCustomer.setDate(1, Date.valueOf(LocalDate.now()));
                    int customerInsert = stmpCustomer.executeUpdate();
                    if (customerInsert > 0) {
                        System.out.println("++qwertyuiopasdfghjkzxcvbnmm");

                        ResultSet generatedKeys = stmpCustomer.getGeneratedKeys();
                        if (generatedKeys.next()) {
//                    System.out.println("qwertyuiopasdfghjkzxcvbnmm");
                            int customerId = generatedKeys.getInt(1); // Récupère la première clé générée


                            PreparedStatement stmpCommand = this.conn.prepareStatement(sqlInsertCommand, Statement.RETURN_GENERATED_KEYS);

                            stmpCommand.setDate(1, Date.valueOf(LocalDate.now()));
                            stmpCommand.setInt(2, this.userId);
                            stmpCommand.setInt(3, customerId);
                            stmpCommand.setString(4, "FFG64");
                            stmpCommand.setString(5, status);
                            stmpCommand.setString(6, payementEtat);
//                            stmpCommand.setInt(7, quantiter);
                            int commandInsert = stmpCommand.executeUpdate();
                            if (commandInsert > 0) {
                                System.out.println("++qwertyuiopasdfghjkzxcvbnmm");

                                ResultSet commandKeys = stmpCommand.getGeneratedKeys();
                                if (commandKeys.next()) {
//                    System.out.println("qwertyuiopasdfghjkzxcvbnmm");
                                    commandId = commandKeys.getInt(1); // Récupère la première clé générée
                                    System.out.println("Le client a etet  inséré avec l'ID : " + commandId);
                                }

                                //L'insertion des nouvel command dans a base de donner
                                PreparedStatement stmpproductVendue = this.conn.prepareStatement(insertProductSeled, Statement.RETURN_GENERATED_KEYS);

                                stmpproductVendue.setInt(1, commandId);
                                stmpproductVendue.setInt(2, productID);
                                stmpproductVendue.setInt(3, qt);
                                stmpproductVendue.setDouble(4, (qt * prixUnitProd));
                                stmpproductVendue.executeUpdate();
                                System.out.println("Creation is succesfull");

                            } else if (rep.getInt("quantiter") < quantiter) {
                                System.out.println("Quantiter insuffisant");
//                            return false;
                            } else {
                                System.out.println("Quantiter insuffisant");
//                            return false;
                            }
                            System.out.println("Quantiter insuffisant");

                            generatedKeys.close();
                        }


                    }
                }

            }
        }
    }
}
