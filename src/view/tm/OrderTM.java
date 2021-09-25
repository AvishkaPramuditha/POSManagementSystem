package view.tm;

public class OrderTM {
    private String foodCode;
    private String description;
    private double price;
    private int quantity;
    private double amount;
    private  String foodType;

    public OrderTM() {
    }

    public OrderTM(String foodCode, String description, double price, int quantity, double amount, String foodType) {
        this.foodCode = foodCode;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.foodType = foodType;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
