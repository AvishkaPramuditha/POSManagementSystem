package model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String custId;
    private String custName;
    private String orderDate;
    private String orderTime;
    private String orderType;
    private double subTotal;
    private double deliveryCharges;
    private double total;
    private String orderStatus;
    private ArrayList<OrderDetail> orderedItem;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Order(String custId, String custName, double subTotal, double deliveryCharges, double total, String orderStatus, ArrayList<OrderDetail> orderedItem) {
        this.custId = custId;
        this.custName = custName;
        this.subTotal = subTotal;
        this.deliveryCharges = deliveryCharges;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderedItem = orderedItem;
    }

    public Order(String orderID, String custId, String orderDate, String orderTime, String orderType, double subTotal, double deliveryCharges, double total, String orderStatus, ArrayList<OrderDetail> orderedItem) {
        this.orderID = orderID;
        this.custId = custId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.subTotal = subTotal;
        this.deliveryCharges = deliveryCharges;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderedItem = orderedItem;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<OrderDetail> getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(ArrayList<OrderDetail> orderedItem) {
        this.orderedItem = orderedItem;
    }
}
