package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Discount;
import model.Item;
import view.tm.DiscountTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountController {
    public boolean addMealDiscount(Discount discount, String mealID) {
        try {

            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,MealID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, discount.getDiscountID());
            preparedStatement.setString(2, mealID);
            preparedStatement.setString(3, discount.getStartDate());
            preparedStatement.setString(4, discount.getCloseDate());
            preparedStatement.setDouble(5, discount.getDiscount());
            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addPizzaDiscount(Discount discount, String pizzaID) {
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,PizzaID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, discount.getDiscountID());
            preparedStatement.setString(2, pizzaID);
            preparedStatement.setString(3, discount.getStartDate());
            preparedStatement.setString(4, discount.getCloseDate());
            preparedStatement.setDouble(5, discount.getDiscount());
            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSubDiscount(Discount discount, String sandwichID) {
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,SandwichID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, discount.getDiscountID());
            preparedStatement.setString(2, sandwichID);
            preparedStatement.setString(3, discount.getStartDate());
            preparedStatement.setString(4, discount.getCloseDate());
            preparedStatement.setDouble(5, discount.getDiscount());
            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addDrinkDiscount(Discount discount, String beverageID) {
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO discount (DiscountID,BeverageID,StartDate,CloseDate,DiscountPrice) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, discount.getDiscountID());
            preparedStatement.setString(2, beverageID);
            preparedStatement.setString(3, discount.getStartDate());
            preparedStatement.setString(4, discount.getCloseDate());
            preparedStatement.setDouble(5, discount.getDiscount());
            return preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<DiscountTM> getDiscountItems() throws SQLException, ClassNotFoundException {
        ObservableList<DiscountTM> list = FXCollections.observableArrayList();
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("Select * FROM discount").executeQuery();
        while (resultSet.next()) {
            int colum=1;
            for (int i = 2; i <5 ; i++) {
                if (resultSet.getString(i)!=null){colum=i;}
            }
            list.add(new DiscountTM(resultSet.getString(1), resultSet.getString(colum), resultSet.getDouble(8), resultSet.getString(6), resultSet.getString(7)));
        }
        return list;
    }

    public DiscountTM getItemDetail(String discountID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM discount WHERE DiscountID='" + discountID + "'").executeQuery();
        if (resultSet.next()) {
            int colum = 1;
            for (int i = 2; i < 5; i++) {
                if (resultSet.getString(i) != null) {
                    colum = i;
                }
            }
            return new DiscountTM(resultSet.getString(1), resultSet.getString(colum), resultSet.getDouble(8), resultSet.getString(6), resultSet.getString(7));
        }
        return null;
    }
    public boolean delete(String discountID) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM discount WHERE DiscountID='"+discountID+"'").executeUpdate()>0;
    }
    public boolean updateMealDiscount(Discount discount,String mealID) {
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE discount SET MealID=?,StartDate=?,CloseDate=?,DiscountPrice=?, PizzaID=?,SandwichID=?,BeverageID=? WHERE DiscountID=?");
            preparedStatement.setString(1,mealID);
            preparedStatement.setString(2,discount.getStartDate());
            preparedStatement.setString(3,discount.getCloseDate());
            preparedStatement.setDouble(4,discount.getDiscount());
            preparedStatement.setString(5,null);
            preparedStatement.setString(6,null);
            preparedStatement.setString(7,null);
            preparedStatement.setString(8,discount.getDiscountID());
            return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean updatePizzaDiscount(Discount discount,String pizzaID){
        try {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE discount SET PizzaID=?,StartDate=?,CloseDate=?,DiscountPrice=?, MealID=?,SandwichID=?,BeverageID=? WHERE DiscountID=?");
        preparedStatement.setString(1,pizzaID);
        preparedStatement.setString(2,discount.getStartDate());
        preparedStatement.setString(3,discount.getCloseDate());
        preparedStatement.setDouble(4,discount.getDiscount());
        preparedStatement.setString(5,null);
        preparedStatement.setString(6,null);
        preparedStatement.setString(7,null);
        preparedStatement.setString(8,discount.getDiscountID());
        return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean updateSubDiscount(Discount discount,String sandwichID){
        try {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE discount SET SandwichID=?,StartDate=?,CloseDate=?,DiscountPrice=? , MealID=?,PizzaID=?,BeverageID=? WHERE DiscountID=?");
        preparedStatement.setString(1,sandwichID);
        preparedStatement.setString(2,discount.getStartDate());
        preparedStatement.setString(3,discount.getCloseDate());
        preparedStatement.setDouble(4,discount.getDiscount());
        preparedStatement.setString(5,null);
        preparedStatement.setString(6,null);
        preparedStatement.setString(7,null);
            preparedStatement.setString(8,discount.getDiscountID());
        return preparedStatement.executeUpdate()>0;
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean updateDrinkDiscount(Discount discount,String beverageID) {
        try {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE discount SET BeverageID=?,StartDate=?,CloseDate=?,DiscountPrice=?, MealID=?,PizzaID=?,SandwichID=? WHERE DiscountID=?");
        preparedStatement.setString(1,beverageID);
        preparedStatement.setString(2,discount.getStartDate());
        preparedStatement.setString(3,discount.getCloseDate());
        preparedStatement.setDouble(4,discount.getDiscount());
        preparedStatement.setString(5,null);
        preparedStatement.setString(6,null);
        preparedStatement.setString(7,null);
        preparedStatement.setString(8,discount.getDiscountID());
        return preparedStatement.executeUpdate()>0;
    }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        return false;
    }

}