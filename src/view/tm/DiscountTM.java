package view.tm;

public class DiscountTM {
    private String discountID;
    private String FoodItem;
    private double discountPrice;
    private String startDate;
    private String closeDate;

    public DiscountTM(String discountID, String foodItem, double discountPrice, String startDate, String closeDate) {
        this.discountID = discountID;
        FoodItem = foodItem;
        this.discountPrice = discountPrice;
        this.startDate = startDate;
        this.closeDate = closeDate;
    }

    public DiscountTM() {
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    public String getFoodItem() {
        return FoodItem;
    }

    public void setFoodItem(String foodItem) {
        FoodItem = foodItem;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
}
