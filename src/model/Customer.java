package model;

public class Customer {
    private  String custID;
    private String customerName;
    private String CustomerAddress;
    private String customerMobile;

    public Customer(String customerName, String customerAddress, String customerMobile,String custID) {
        this.customerName = customerName;
        CustomerAddress = customerAddress;
        this.customerMobile = customerMobile;
        this.custID = custID;
    }

    public Customer(String customerName, String customerAddress, String customerMobile) {
        this.setCustomerName(customerName);
        this.setCustomerMobile(customerMobile);
        setCustomerAddress(customerAddress);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }
}
