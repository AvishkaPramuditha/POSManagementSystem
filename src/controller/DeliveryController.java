package controller;

import database.DbConnection;

import java.sql.SQLException;

public class DeliveryController {
    public boolean addDelivery(String orderID,String driverEmployeeID) throws SQLException, ClassNotFoundException {
       return DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO delivery VALUES ('"+orderID+"','"+driverEmployeeID+"')").executeUpdate()>0;

    }
}
