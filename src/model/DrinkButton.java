package model;

public class DrinkButton {
    private String beverageID;
    private String description;
    private boolean isAvailable;
    private double unitPrice;
    private double discountPrice;

    public DrinkButton(String beverageID, String description, boolean isAvailable, double unitPrice, double discountPrice) {
        this.beverageID = beverageID;
        this.description = description;
        this.isAvailable = isAvailable;
        this.unitPrice = unitPrice;
        this.discountPrice = discountPrice;
    }

    public DrinkButton() {
    }

    public String getBeverageID() {
        return beverageID;
    }

    public void setBeverageID(String beverageID) {
        this.beverageID = beverageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
