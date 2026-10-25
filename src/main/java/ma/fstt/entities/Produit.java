package ma.fstt.entities;

public class Produit {
    private int id;
    private String designation;
    private double prix;
    private int quantiteStock;

    public Produit() {}

    public Produit(int id, String designation, double prix, int quantiteStock) {
        this.id = id;
        this.designation = designation;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(int quantiteStock) { this.quantiteStock = quantiteStock; }
}