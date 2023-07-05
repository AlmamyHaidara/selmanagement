package org.selManagement.Model;

import config.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.IntFunction;

public class Products extends ArrayList<Products> {
    private int idProduct;
    private String codeProduct;
    private String nomPoduct;
    private String description;
    private String categorieProduct;
    private Double prixUnit;
    private int quantiter;

    public int getIdProduct() {
        return idProduct;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public String getNomPoduct() {
        return nomPoduct;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorieProduct() {
        return categorieProduct;
    }

    public Double getPrixUnit() {
        return prixUnit;
    }

    public int getQuantiter() {
        return quantiter;
    }

    private Connection conn;

    public Products(int idProduct, String codeProduct, String nomProduct, String categorieProduct, String description, Double prixUnit, int quantiter) {
        this.idProduct = idProduct;
        this.codeProduct = codeProduct;
        this.categorieProduct = categorieProduct;
        this.nomPoduct = nomProduct;
        this.prixUnit = prixUnit;
        this.description = description;
        this.quantiter = quantiter;
        new Connexion();
        this.conn = new Connexion().conn();
    }


}
