package controller;

import database.DbConnection;
import model.Order;
import model.OrderDetail;
import model.PackageDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {
    public String getNextOrderID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT OrderID FROM orders ORDER BY OrderId DESC LIMIT 1").executeQuery();
    if (resultSet.next()){
        Integer integer = Integer.valueOf(resultSet.getString(1).split("-")[1])+1;
        if (integer<9){
            return "O-0000000"+integer;
        }else if (integer<99){ return "R-000000"+integer;}else if (integer<999){ return "R-00000"+integer;}else if (integer<9999){return "R-0000"+integer;}else if (integer<99999){return "R-000"+integer;}else if (integer<999999){return "R-00"+integer;}else if (integer<9999999){return "R-0"+integer;}else {return "R-"+integer;}
    }else {
        return "O-00000001";
    }
    }
    public boolean placeOrder(Order order) {
        Connection connection=null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,order.getOrderID());
            preparedStatement.setString(2,order.getCustId());
            preparedStatement.setString(3,order.getOrderDate());
            preparedStatement.setString(4,order.getOrderTime());
            preparedStatement.setString(5,order.getOrderType());
            preparedStatement.setDouble(6,order.getSubTotal());
            preparedStatement.setDouble(7,order.getDeliveryCharges());
            preparedStatement.setDouble(8,order.getTotal());
            preparedStatement.setString(9,order.getOrderStatus());
            if (preparedStatement.executeUpdate()>0){
                    if (orderDetail(order.getOrderedItem(),order.getOrderID())){
                        connection.commit();
                        return true;
                    }else {
                        connection.rollback();
                        return false;
                    }
            }else {
                connection.rollback();return false;}
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

       return false;
    }
    private boolean orderDetail(ArrayList<OrderDetail> orderDetails, String orderID) throws SQLException, ClassNotFoundException {
        String table;
        for (OrderDetail detail:orderDetails
             ) {
            if (detail.getFoodType()=="Meal"){table="ordermealdetail";}else if (detail.getFoodType()=="Pizza"){table="orderpizzadetail";}else if (detail.getFoodType()=="Sub"){table="ordersubdetail";}else if (detail.getFoodType()=="Drink"){table=" orderdrinkdetail";}else if (detail.getFoodType()=="Package"){table=" orderpackagedetail";}else {continue;}
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO "+table+" VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,orderID);
            preparedStatement.setString(2,detail.getFoodID());
            preparedStatement.setString(3,detail.getDescription());
            preparedStatement.setDouble(4,detail.getSellingPrice());
            preparedStatement.setInt(5,detail.getQuantity());
          if (preparedStatement.executeUpdate()>0){}else{return false;}
        }
        return true;
    }
}
