package controller;

import database.DbConnection;
import model.DeliveryInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryController {
    public boolean addDelivery(String orderID,String driverEmployeeID) throws SQLException, ClassNotFoundException {
       return DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO delivery VALUES ('"+orderID+"','"+driverEmployeeID+"')").executeUpdate()>0;
    }
    public DeliveryInformation getDeliveryInformation(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT c.CustID,c.CustName,c.CustAddress,c.CustMobileNumber, e.EmployeeID,e.EmployeeName FROM customer c INNER JOIN Orders o INNER JOIN delivery d INNER JOIN employee e ON c.CustID=o.CustID AND o.OrderID=d.OrderID AND d.DriverEmployee=e.EmployeeID WHERE o.OrderID='" + orderID + "'").executeQuery();
        if (resultSet.next()){
            return new DeliveryInformation(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
        }

return null;
    }
}
