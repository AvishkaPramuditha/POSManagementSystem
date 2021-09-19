package model;

import java.io.File;

public class SubBurgersAndOthers implements Item {
    private String sandwichID;
    private String description;
    private double unitPrice;
    private int quantityOnHand;
    private File subImage;

    public SubBurgersAndOthers(String sandwichID, String description, double unitPrice, int quantityOnHand, File subImage) {
        this.sandwichID = sandwichID;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantityOnHand = quantityOnHand;
        this.subImage = subImage;
    }

    public SubBurgersAndOthers() {
    }

    public SubBurgersAndOthers(String sandwichID, String description) {
        this.sandwichID = sandwichID;
        this.description = description;
    }

    @Override
    public String toString() {
        return getSandwichID() +" - "+ getDescription();
    }

    public String getSandwichID() {
        return sandwichID;
    }

    public void setSandwichID(String sandwichID) {
        this.sandwichID = sandwichID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public File getSubImage() {
        return subImage;
    }

    public void setSubImage(File subImage) {
        this.subImage = subImage;
    }

    @Override
    public String getID() {
        return sandwichID;
    }
}
