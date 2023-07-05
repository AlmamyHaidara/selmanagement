/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.selManagement.Controller;

import org.selManagement.Model.*;


import config.Connexion;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Cette controller contient le crud pour la gestion des produit
 *
 * @author almamy
 */
public class ProductControler {

    private int idProduct;
    private String codeProduct;
    private String nomProduct;
    private String categorieProduct;
    private String description;
    private Double prixUnit;
    private Integer quantiter;
    private Boolean res;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private Connection conn;

    //private ArrayList<Product> products;


    public ProductControler() {
        new Connexion();
        this.conn = new Connexion().conn();

    }

    /**
     * This function create they new product. She is return `error` if one there
     * parameter is null or "" or this creation is not succesfull, `success` if
     * the creation is successfull or `fatalError` if is exeption error.
     *
     * @param nomProduct:       String
     * @param categorieProduct: String
     * @param description:      String
     * @param prixUnit:         Double
     * @param quantiter:        Int
     * @return String
     */
    public String createProduct(String nomProduct, String categorieProduct, String description, double prixUnit, int quantiter) {
        this.codeProduct = this.generateProductCode();
        this.nomProduct = nomProduct;
        this.categorieProduct = categorieProduct;
        this.description = description;
        this.prixUnit = prixUnit;
        this.quantiter = quantiter;

        if (this.nomProduct.isEmpty() || this.prixUnit <= 0 || this.quantiter == 0 || this.description.isEmpty())  {
            return "error";
        } else {
            String sqlProduct = "SELECT * FROM product where nomProduct = ?";
            String sqlSelect = "SELECT * FROM categorie where categorie = ?";
            String sql = "INSERT INTO product (codeProduct, nomProduct,categorie,description, prixUnit ,quantiter) VALUE (?,?,?,?,?,?)";

            try {
                // Select the current product on the table product in the database
                PreparedStatement stmtProduct = this.conn.prepareStatement(sqlProduct);
                stmtProduct.setString(1, this.nomProduct);
                ResultSet res = stmtProduct.executeQuery();
                if (!res.next()) {
                    // Select the currente categorie on the table categorie in the database
                    PreparedStatement stmtSelect = this.conn.prepareStatement(sqlSelect);
                    System.out.println(categorieProduct);
                    stmtSelect.setString(1, this.categorieProduct);
                    ResultSet rs = stmtSelect.executeQuery();
                    int categorie = 0;
                    System.out.println(" +++ " + rs.next());
                    while (rs.next()) {
                        categorie = rs.getInt("id");
                        System.out.println("cat " + categorie);
                    }
                    // Insert a new product on the product table in my database

                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    stmt.setString(1, this.codeProduct);
                    stmt.setString(2, this.nomProduct);
                    stmt.setInt(3, categorie);
                    stmt.setString(4, this.description);
                    stmt.setDouble(5, this.prixUnit);
                    stmt.setInt(6, this.quantiter);
                    stmt.executeUpdate();

                } else {
//                    //Update quantity of the currente product if if exist in the dataBase 
//                    int idProduct = 0;
//                    int qt = 0;
//                    System.out.println(res.next());
//                    while (res.next()) {
//                        System.out.println("======================================================================");
//                        idProduct = res.getInt("id");
//                        qt = this.quantiter + res.getInt("quantiter");
//                        System.out.println("QT==? " + qt);
//
//                    }
//                    String sqltexist = "UPDATE product set quantiter = ? where id = ?";
//                    PreparedStatement stmtUp = this.conn.prepareStatement(sqltexist);
//                    System.out.println("QT==? " + qt);
//                    stmtUp.setInt(1, this.quantiter);
//                    stmtUp.setInt(2, idProduct);
//
//                    stmtUp.executeUpdate();

                    return "exist";
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductControler.class.getName()).log(Level.SEVERE, null, ex);
                return "fatalError";
            }

            return "success";

        }

    }

    public ArrayList Categoriefunc() {

        String sqlSelect = "SELECT categorie FROM categorie";
        ArrayList<String> cat = new ArrayList<>();

        try {
            System.out.println(this.conn.getSchema());
            PreparedStatement stmtSelect = this.conn.prepareStatement(sqlSelect);
            ResultSet rs = stmtSelect.executeQuery();
            while (rs.next()) {
                cat.add(rs.getString("categorie"));

            }

            for (String categorie : cat) {
                System.out.println(categorie);
            }

            rs.close();
            stmtSelect.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cat;
    }

    public boolean newCategorieFunc(String categorie, boolean etat) {

        String sql = "INSERT INTO categorie (categorie,etat) VALUE (?,?)";
        String sqlSelect = "SELECT categorie from categorie where categorie = ?";

        try {
            PreparedStatement stmtSelect = this.conn.prepareStatement(sqlSelect);
            stmtSelect.setString(1, categorie);
            boolean rs = stmtSelect.executeQuery().next();
            System.out.println(rs);
            if (!rs) {

                PreparedStatement stmt = this.conn.prepareStatement(sql);

                stmt.setString(1, categorie);
                stmt.setBoolean(2, etat);
                stmt.executeUpdate();
                System.out.println(rs + "Falllllll");
                JOptionPane.showMessageDialog(null, "Categorie creer avec succe");
//                return true;

            } else {
                JOptionPane.showMessageDialog(null, "Categorie existe deja");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Categorie non creer");
            Logger.getLogger(ProductControler.class.getName()).log(Level.SEVERE, null, ex);
        }
//        return true;
        return true;

    }

    /**
     * This function generation the product code that is composet they char end
     * integer
     *
     * @return String
     */
    @org.jetbrains.annotations.NotNull
    public static String generateProductCode() {
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

    public ArrayList<Products> getAllProduct() {
        String stmtSelectAll = "SELECT * FROM product";
        String sqlCat = "SELECT categorie FROM categorie where id = ?";


        try {
            PreparedStatement stmt = this.conn.prepareStatement(stmtSelectAll);
            ResultSet result = stmt.executeQuery();
            int categorie = 0;

            while (result.next()) {
                this.idProduct = result.getInt("id");
                this.codeProduct = result.getString("codeProduct");
                this.nomProduct = result.getString("nomProduct");
                this.description = result.getString("description");
                categorie = result.getInt("categorie");
                this.prixUnit = result.getDouble("prixUnit");
                this.quantiter = result.getInt("quantiter");
                PreparedStatement stmtCat = this.conn.prepareStatement(sqlCat);
                stmtCat.setInt(1, categorie);
                ResultSet rs = stmtCat.executeQuery();
                while (rs.next()) {
                    this.categorieProduct = rs.getString("categorie");
                }

                ArrayList<Products> produits = new ArrayList<Products>();
                produits.add(new Products(this.idProduct, this.codeProduct, this.nomProduct, this.categorieProduct, this.description, this.prixUnit, this.quantiter));

                return produits;
            }

            System.out.println(this.codeProduct);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
