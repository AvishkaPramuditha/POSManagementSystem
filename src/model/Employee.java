package model;

public class Employee {
    private String employeeID;
    private String employeeNIC;
    private String employeeName;
    private String employeeAddress;
    private String employeeContactNumber;
    private String jobRole;
    private String workingHours;
    private String bikeNo;
    private String drivingLicenseNumber;
    private String userName;
    private String password;

    public Employee(String employeeID, String employeeName) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
    }

    public Employee(String employeeID, String employeeNIC, String employeeName, String employeeAddress, String employeeContactNumber, String jobRole, String workingHours, String bikeNo, String drivingLicenseNumber, String userName, String password) {
        this.employeeID = employeeID;
        this.employeeNIC = employeeNIC;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeContactNumber = employeeContactNumber;
        this.jobRole = jobRole;
        this.workingHours = workingHours;
        this.bikeNo = bikeNo;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.userName = userName;
        this.password = password;
    }

    public Employee(String employeeID, String employeeNIC, String employeeName, String employeeAddress, String employeeContactNumber, String jobRole, String workingHours, String bikeNo, String drivingLicenseNumber) {
        this.employeeID = employeeID;
        this.employeeNIC = employeeNIC;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.employeeContactNumber = employeeContactNumber;
        this.jobRole = jobRole;
        this.workingHours = workingHours;
        this.bikeNo = bikeNo;
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public Employee() {
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeNIC() {
        return employeeNIC;
    }

    public void setEmployeeNIC(String employeeNIC) {
        this.employeeNIC = employeeNIC;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeContactNumber() {
        return employeeContactNumber;
    }

    public void setEmployeeContactNumber(String employeeContactNumber) {
        this.employeeContactNumber = employeeContactNumber;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return employeeID+" - "+employeeName;
    }
}
