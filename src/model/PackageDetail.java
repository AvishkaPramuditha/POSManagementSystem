package model;

public class PackageDetail {
    private String foodType;
    private String foodCode;
    private int quantity;

    public PackageDetail() {
    }

    public PackageDetail(String foodType, String foodCode, int quantity) {
        this.foodType = foodType;
        this.foodCode = foodCode;
        this.quantity = quantity;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
