package model;

public class SubButton {
    private String sandwichId;
    private String description;
    private int quantityOnHand;
    private double unitPrice;
    private double discountPrice;

    public SubButton(String sandwichId, String description, int quantityOnHand, double unitPrice, double discountPrice) {
        this.sandwichId = sandwichId;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
        this.unitPrice = unitPrice;
        this.discountPrice = discountPrice;
    }

    public SubButton() {
    }

    public String getSandwichId() {
        return sandwichId;
    }

    public void setSandwichId(String sandwichId) {
        this.sandwichId = sandwichId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
