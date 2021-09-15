package controller;

import database.DbConnection;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {
    public Customer getCustomerDetail(String customerMobile) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select custName,CustAddress,CustMobileNumber from customer WHERE CustMobileNumber='" + customerMobile + "'").executeQuery();
        if (resultSet.next()) {
            return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
        return null;
    }
    public boolean updateCustomer(Customer customer, String customerMobileNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET CustName=?,CustAddress=?,CustMobileNumber=? WHERE CustMobileNumber=?");
        preparedStatement.setString(1,customer.getCustomerName());
        preparedStatement.setString(2,customer.getCustomerAddress());
        preparedStatement.setString(3,customer.getCustomerMobile());
        preparedStatement.setString(4,customerMobileNumber);
        return preparedStatement.executeUpdate()>0;
    }

    public boolean deleteCustomer(String customerMobileNumber) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE CustMobileNumber='"+customerMobileNumber+"'").executeUpdate()>0;
    }
}
