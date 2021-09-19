package model;

public class Discount {
    private String discountID;
    private String startDate;
    private String closeDate;
    private double discount;


    public Discount() {
    }

    public Discount(String discountID, String startDate, String closeDate, double discount) {
        this.discountID = discountID;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.discount = discount;
    }

    public String getDiscountID() {
        return discountID;
    }

    public void setDiscountID(String discountID) {
        this.discountID = discountID;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
