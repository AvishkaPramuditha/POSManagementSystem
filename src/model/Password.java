package model;

public class Password {
    private String employeeID;
    private String employeeName;
    private String userName;
    private String password;

    public Password(String employeeID, String employeeName, String userName, String password) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.userName = userName;
        this.password = password;
    }

    public Password() {
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
}
