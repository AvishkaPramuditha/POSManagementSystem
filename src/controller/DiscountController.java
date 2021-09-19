package controller;

import database.DbConnection;
import model.Discount;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DiscountController {
    public boolean addMealDiscount(Discount discount,String mealID){
        try {

            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,MealID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,discount.getDiscountID());
            preparedStatement.setString(2,mealID);
            preparedStatement.setString(3,discount.getStartDate());
            preparedStatement.setString(4,discount.getCloseDate());
            preparedStatement.setDouble(5,discount.getDiscount());
            return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
     return false;
    }
    public boolean addPizzaDiscount(Discount discount,String pizzaID){
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,PizzaID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,discount.getDiscountID());
            preparedStatement.setString(2,pizzaID);
            preparedStatement.setString(3,discount.getStartDate());
            preparedStatement.setString(4,discount.getCloseDate());
            preparedStatement.setDouble(5,discount.getDiscount());
            return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean addSubDiscount(Discount discount,String sandwichID){
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,SandwichID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,discount.getDiscountID());
            preparedStatement.setString(2,sandwichID);
            preparedStatement.setString(3,discount.getStartDate());
            preparedStatement.setString(4,discount.getCloseDate());
            preparedStatement.setDouble(5,discount.getDiscount());
            return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean addDrinkDiscount(Discount discount,String beverageID){
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,BeverageID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,discount.getDiscountID());
            preparedStatement.setString(2,beverageID);
            preparedStatement.setString(3,discount.getStartDate());
            preparedStatement.setString(4,discount.getCloseDate());
            preparedStatement.setDouble(5,discount.getDiscount());
            return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
}
