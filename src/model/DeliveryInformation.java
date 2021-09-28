package model;

public class DeliveryInformation {
    private String custId;
    private String custname;
    private String custAddress;
    private String custMobileNumber;
    private String employeeId;
    private String employeeName;

    public DeliveryInformation(String custId, String custname, String custAddress, String custMobileNumber, String employeeId, String employeeName) {
        this.custId = custId;
        this.custname = custname;
        this.custAddress = custAddress;
        this.custMobileNumber = custMobileNumber;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustMobileNumber() {
        return custMobileNumber;
    }

    public void setCustMobileNumber(String custMobileNumber) {
        this.custMobileNumber = custMobileNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
