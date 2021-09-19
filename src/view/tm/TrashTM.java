package view.tm;

public class TrashTM {
    private String foodCode;
    private String description;
    private int quantity;
    private String reason;

    public TrashTM(String foodCode, String description, int quantity, String reason) {
        this.foodCode = foodCode;
        this.description = description;
        this.quantity = quantity;
        this.reason = reason;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
