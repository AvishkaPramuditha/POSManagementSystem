package model;

import java.io.File;

public class Drink {
    private String beverageID;
    private String description;
    private double unitPrice;
    private boolean isAvailable;
     private File drinkImage;

    public Drink() {
    }

    public Drink(String beverageID, String description, double unitPrice, boolean isAvailable, File drinkImage) {
        this.beverageID = beverageID;
        this.description = description;
        this.unitPrice = unitPrice;
        this.isAvailable = isAvailable;
        this.drinkImage = drinkImage;
    }

    public Drink(String beverageID, String description) {
        this.beverageID = beverageID;
        this.description = description;
    }

    public String getBeverage() {
        return getBeverageID();
    }

    public void setBeverage(String beverage) {
        this.setBeverageID(beverage);
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return getBeverageID() +" - "+ getDescription();
    }

    public String getBeverageID() {
        return beverageID;
    }

    public void setBeverageID(String beverageID) {
        this.beverageID = beverageID;
    }

    public File getDrinkImage() {
        return drinkImage;
    }

    public void setDrinkImage(File drinkImage) {
        this.drinkImage = drinkImage;
    }
}
