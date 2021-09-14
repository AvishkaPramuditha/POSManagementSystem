package model;

import java.io.File;

public class Pizza {
    private String pizzaID;
    private String description;
    private String size;
    private double unitPrize;
    private int quantityOnHand;

    public Pizza(String pizzaID, String description, String size) {
        this.pizzaID = pizzaID;
        this.description = description;
        this.size = size;
    }

    public Pizza() {
    }

    public Pizza(String pizzaID, String description, String size, double unitPrize, int quantityOnHand, File pizzaImage) {
        this.pizzaID = pizzaID;
        this.description = description;
        this.size = size;
        this.unitPrize = unitPrize;
        this.quantityOnHand = quantityOnHand;
        this.pizzaImage = pizzaImage;
    }

    private File pizzaImage;

    @Override
    public String toString() {
        return getPizzaID() +" - "+ getDescription() +" - "+ getSize();
    }


    public String getPizzaID() {
        return pizzaID;
    }

    public void setPizzaID(String pizzaID) {
        this.pizzaID = pizzaID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getUnitPrize() {
        return unitPrize;
    }

    public void setUnitPrize(double unitPrize) {
        this.unitPrize = unitPrize;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public File getPizzaImage() {
        return pizzaImage;
    }

    public void setPizzaImage(File pizzaImage) {
        this.pizzaImage = pizzaImage;
    }
}
