package model;

public class Customer {
    private String customerName;
    private String CustomerAddress;
    private String customerMobile;


    public Customer(String customerName,  String customerAddress,String customerMobile) {
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        CustomerAddress = customerAddress;
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
}
