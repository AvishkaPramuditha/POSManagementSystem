package view.tm;

import java.util.Objects;

public class OrderTM {
    private String foodCode;
    private String description;
    private double price;
    private int quantity;
    private double amount;

    public OrderTM(String foodCode, String description, double price, int quantity, double amount) {
        this.foodCode = foodCode;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
