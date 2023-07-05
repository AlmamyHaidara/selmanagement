package org.selManagement.Model;

public class Command {
    private String date;
    private int userId;
    private int productId;
    private String numFacture;
    private String etatCommand;
    private String etatPayement;

    public Command(String date, int userId, int productId, String numFacture, String etatCommand, String etatPayement) {
        this.date = date;
        this.userId = userId;
        this.productId = productId;
        this.numFacture = numFacture;
        this.etatCommand = etatCommand;
        this.etatPayement = etatPayement;
    }


    public String getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public String getEtatCommand() {
        return etatCommand;
    }

    public String getEtatPayement() {
        return etatPayement;
    }
}
