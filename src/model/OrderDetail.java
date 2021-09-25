package model;

public class OrderDetail {
    private String foodID;
    private String description;
    private double sellingPrice;
    private int quantity;
    private String foodType;

    public OrderDetail(String foodID, String description, double sellingPrice, int quantity, String foodType) {
        this.foodID = foodID;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.foodType = foodType;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
