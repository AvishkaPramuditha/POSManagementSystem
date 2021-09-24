package model;

public class PizzaButton {
    private String pizzaID;
    private String description;
    private String size;
    private int quantityOnHand;
    private double unitPrice;
    private double DiscountPrice;

    public PizzaButton(String pizzaID, String description, String size, int quantityOnHand, double unitPrice, double discountPrice) {
        this.pizzaID = pizzaID;
        this.description = description;
        this.size = size;
        this.quantityOnHand = quantityOnHand;
        this.unitPrice = unitPrice;
        DiscountPrice = discountPrice;
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

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        DiscountPrice = discountPrice;
    }
}
