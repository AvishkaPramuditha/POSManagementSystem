package view.tm;

public class PackageTM {
    private String foodCode;
    private String Description;
    private int quantity;
    private String foodType;

    public PackageTM() {
    }


    public PackageTM(String foodCode, String description, int quantity, String foodType) {
        this.foodCode = foodCode;
        Description = description;
        this.quantity = quantity;
        this.foodType= foodType;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public void setFoodType(String foodTypeString) {
        this.foodType= foodTypeString;
    }
}
