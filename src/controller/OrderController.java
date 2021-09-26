package controller;

import database.DbConnection;
import model.Order;
import model.OrderDetail;
import model.PackageDetail;
import view.tm.OrderTM;

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
        }else if (integer<99){ return "O-000000"+integer;}else if (integer<999){ return "O-00000"+integer;}else if (integer<9999){return "O-0000"+integer;}else if (integer<99999){return "O-000"+integer;}else if (integer<999999){return "O-00"+integer;}else if (integer<9999999){return "O-0"+integer;}else {return "O-"+integer;}
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
                    if (saveOrderDetail(order.getOrderedItem(),order.getOrderID())){
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
    private boolean saveOrderDetail(ArrayList<OrderDetail> orderDetails, String orderID) throws SQLException, ClassNotFoundException {
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

    public Order getOrderDetail(String orderID,String orderType) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT c.CustID,c.CustName,o.SubTotal,o.DeliveryCharges,o.Total,o.OrderStatus from Orders o INNER JOIN customer c ON c.CustID=o.CustID WHERE OrderType='" + orderType + "' AND OrderID='" + orderID + "'").executeQuery();
          if (resultSet.next()){
              ArrayList<OrderDetail> itemOnOrder = getItemOnOrder(orderID);
              return new Order(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getString(6),itemOnOrder);
          }else {return null;}

    }
    private ArrayList<OrderDetail> getItemOnOrder(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("(SELECT MealID As foodCode,Description,SellingPrice,Quantity,\"Meal\" as tempField from orderMealDetail WHERE OrderID='" + orderID + "')UNION (SELECT PizzaID As foodCode,Description,SellingPrice,Quantity,\"Pizza\" as tempField from orderPizzaDetail WHERE  OrderID='" + orderID + "')UNION (SELECT SandwichID As foodCode,Description,SellingPrice,Quantity,\"Sub\" as tempField from orderSubDetail WHERE  OrderID='" + orderID + "')UNION (SELECT BeverageID As foodCode,Description,SellingPrice,Quantity,\"Drink\" as tempField from orderDrinkDetail WHERE  OrderID='" + orderID + "')UNION (SELECT PackageID As foodCode,Description,SellingPrice,Quantity,\"Package\" as tempField from orderPackageDetail WHERE  OrderID='" + orderID + "') ;").executeQuery();
        ArrayList<OrderDetail> list=new ArrayList<>();
        while (resultSet.next()){
            list.add(new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getString(5)));
        }
        return list;
    }
}
