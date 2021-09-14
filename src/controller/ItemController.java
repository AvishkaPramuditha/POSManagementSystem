package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Drink;
import model.Meal;
import model.Pizza;
import model.SubBurgersAndOthers;

import java.io.*;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController {
   public Meal getMealDetails(String mealID) throws SQLException, ClassNotFoundException, IOException {
       ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select * from meal WHERE mealID='"+mealID+"'").executeQuery();
       if (resultSet.next()) {
           InputStream is = resultSet.getBinaryStream(6);
           File file = new File("E:\\image\\image.png");
           FileOutputStream fos = new FileOutputStream(file);
           byte content[] = new byte[1024];
           int size = 0;
           while ((size = is.read(content)) != -1) {
               fos.write(content, 0, size);
           }
           fos.close();
           is.close();
           return new Meal(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getBoolean(5), file);
       }
       return null;

    }
    public Pizza getPizzaDetails(String PizzaID) throws SQLException, ClassNotFoundException, IOException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select * from pizza WHERE PizzaID='"+PizzaID+"'").executeQuery();
        if (resultSet.next()){
            InputStream is = resultSet.getBinaryStream(6);
            File file = new File("E:\\image\\image.png");
            FileOutputStream fos = new FileOutputStream(file);
            byte content[] = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                fos.write(content, 0, size);
            }
            fos.close();
            is.close();
            return new Pizza(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getInt(5),file);
        }
        return null;
    }
    public SubBurgersAndOthers getSubBurgersAndOthersDetails(String sandwichID) throws SQLException, ClassNotFoundException, IOException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select * from subburgersAndOthers WHERE sandwichID='"+sandwichID+"'").executeQuery();
        if (resultSet.next()){
            InputStream is = resultSet.getBinaryStream(5);
            File file = new File("E:\\image\\image.png");
            FileOutputStream fos = new FileOutputStream(file);
            byte content[] = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                fos.write(content, 0, size);
            }
            fos.close();
            is.close();
            return new SubBurgersAndOthers(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),file);
        }
        return null;
    }
    public Drink getDrinkDetails(String beverageID) throws SQLException, ClassNotFoundException, IOException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select * from Drink WHERE beverageID='"+beverageID+"'").executeQuery();
        if (resultSet.next()){
            InputStream is = resultSet.getBinaryStream(5);
            File file = new File("E:\\image\\image.png");
            FileOutputStream fos = new FileOutputStream(file);
            byte content[] = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                fos.write(content, 0, size);
            }
            fos.close();
            is.close();
            return new Drink(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getBoolean(4),file);
        }
        return null;
    }

    public ObservableList getMealID_Description_Portion() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select mealID,Description,portion from meal").executeQuery();
        ObservableList list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new Meal(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
        }
        return list;
    }

    public ObservableList getPizzaID_Description_Portion() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select PizzaID,Description,Size from pizza").executeQuery();
        ObservableList list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new Pizza(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
        }
        return list;
    }

    public ObservableList getSubs_Description() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select sandwichID,Description from subBurgersAndOthers").executeQuery();
        ObservableList list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new SubBurgersAndOthers(resultSet.getString(1),resultSet.getString(2)));
        }
        return list;
    }

    public ObservableList getDrink_Description() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select BeverageID,Description from Drink").executeQuery();
        ObservableList list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new Drink(resultSet.getString(1),resultSet.getString(2)));
        }
        return list;
    }

    public boolean addMeal(Meal meal) throws SQLException, ClassNotFoundException, FileNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO meal VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1,meal.getMealID());
        preparedStatement.setString(2,meal.getDescription());
        preparedStatement.setString(3,meal.getPortion());
        preparedStatement.setDouble(4,meal.getUnitPrice());
        preparedStatement.setBoolean(5,meal.isAvailable());
        FileInputStream inputStream=new FileInputStream(meal.getMealImage());
        preparedStatement.setBinaryStream(6,(InputStream) inputStream,(int)meal.getMealImage().length());
        return preparedStatement.executeUpdate()>0 ?true:false;
    }


    public boolean addPizza(Pizza pizza) throws SQLException, ClassNotFoundException, FileNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO pizza VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1,pizza.getPizzaID());
        preparedStatement.setString(2,pizza.getDescription());
        preparedStatement.setString(3,pizza.getSize());
        preparedStatement.setDouble(4,pizza.getUnitPrize());
        preparedStatement.setInt(5,pizza.getQuantityOnHand());
        FileInputStream inputStream=new FileInputStream(pizza.getPizzaImage());
        preparedStatement.setBinaryStream(6,(InputStream) inputStream,(int)pizza.getPizzaImage().length());
        return preparedStatement.executeUpdate()>0? true:false;
    }

    public boolean addSub(SubBurgersAndOthers sub) throws SQLException, ClassNotFoundException, FileNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT  INTO  subBurgersAndOthers VALUES (?,?,?,?,?)");
        preparedStatement.setString(1,sub.getSandwichID());
        preparedStatement.setString(2,sub.getDescription());
        preparedStatement.setDouble(3,sub.getUnitPrice());
        preparedStatement.setInt(4,sub.getQuantityOnHand());
        FileInputStream inputStream=new FileInputStream(sub.getSubImage());
        preparedStatement.setBinaryStream(5,(InputStream) inputStream,(int)sub.getSubImage().length());
        return preparedStatement.executeUpdate()>0?true:false;
    }

    public boolean addDrink(Drink drink) throws SQLException, ClassNotFoundException, FileNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO drink VALUES (?,?,?,?,?)");
        preparedStatement.setString(1,drink.getBeverage());
        preparedStatement.setString(2,drink.getDescription());
        preparedStatement.setDouble(3,drink.getUnitPrice());
        preparedStatement.setBoolean(4,drink.isAvailable());
        FileInputStream inputStream=new FileInputStream(drink.getDrinkImage());
        preparedStatement.setBinaryStream(5,(InputStream) inputStream,(int)drink.getDrinkImage().length());
        return preparedStatement.executeUpdate()>0?true:false;
    }




}
