package model;

import java.io.File;

public class MealButton {
    private String mealID;
    private String description;
    private String portion;
    private boolean isAvailable;
    private double unitPrice;
    private Double discountPrice;

    public MealButton(String mealID, String description, String portion, boolean isAvailable, double unitPrice, Double discountPrice) {
        this.mealID = mealID;
        this.description = description;
        this.portion = portion;
        this.isAvailable = isAvailable;
        this.unitPrice = unitPrice;
        this.discountPrice = discountPrice;
    }

    public MealButton() {
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
