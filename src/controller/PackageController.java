package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Package;
import model.PackageDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageController {
  public boolean addPackage(Package pack){
      Connection connection=null;
      try {
          connection=DbConnection.getInstance().getConnection();
          connection.setAutoCommit(false);
          PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO package VALUES (?,?,?)");
          preparedStatement.setString(1,pack.getPackageID());
          preparedStatement.setString(2,pack.getName());
          preparedStatement.setDouble(3,pack.getPrice());
          if (preparedStatement.executeUpdate()>0){
              if (savePackageDetails(pack.getPackageID(),pack.getPackageDetails())){
                  connection.commit();
                  return true;
              }else{
                  connection.rollback();
                  return false;
              }
          }else{
              connection.rollback();
              return false;
          }
      }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}finally {
          try {
              connection.setAutoCommit(true);
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
             return false;
    }
    private boolean savePackageDetails(String packageID,ArrayList<PackageDetail> list) throws SQLException, ClassNotFoundException {
      String colum = null;
        for (PackageDetail detail:list
             ) {
            if (detail.getFoodType()=="Meal"){colum="MealID";}else if(detail.getFoodType()=="Pizza"){colum="PizzaID";}else if (detail.getFoodType()=="Sub"){colum="SandwichID";}else {colum="BeverageID";}
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO packageDetail(PackageID,"+colum+",Quantity) VALUES (?,?,?)");
            preparedStatement.setString(1,packageID);
            preparedStatement.setString(2,detail.getFoodCode());
            preparedStatement.setDouble(3,detail.getQuantity());
            if (!(preparedStatement.executeUpdate()>0)){return false;}
        }
            return true;
    }
    public ObservableList[] getPackageCodes() throws SQLException, ClassNotFoundException {
        ObservableList[] codeAndName=new ObservableList[2];
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("Select PackageID,Name FROM package").executeQuery();
        ObservableList<String> code= FXCollections.observableArrayList();
        ObservableList<String> name= FXCollections.observableArrayList();
        while (resultSet.next()){
              code.add(resultSet.getString(1));
              name.add(resultSet.getString(2));
          }
        codeAndName[0]=code;
        codeAndName[1]=name;
        return codeAndName;
  }
  public Package getPackageDetail(String packageID,String PackageName) throws SQLException, ClassNotFoundException {
      ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM package WHERE PackageID='" + packageID + "' OR Name='" + PackageName + "'").executeQuery();
      if (resultSet.next()){
          return new Package(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),getPackageItems(resultSet.getString(1)));
      }
           return null;
      }
      private ArrayList<PackageDetail> getPackageItems(String packageID) throws SQLException, ClassNotFoundException {
          ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT PackageID,MealID,PizzaID,SandWichID,BeverageID,Quantity FROM packageDetail WHERE PackageID='" + packageID + "'").executeQuery();
        ArrayList<PackageDetail> detailArrayList=new ArrayList<>();
         while (resultSet.next()){
              int colum=2;
              String type=null;
              for (int i = 2; i < 5; i++) {
                  if (resultSet.getString(i)!=null){
                      colum=i;
                      if (i==2){type="Meal";}else if (i==3){type="Pizza";}else if (i==4){type="Sub";}else {type="Drink";}
                  }
              }
              detailArrayList.add(new PackageDetail(type,resultSet.getString(colum),resultSet.getInt(6)));
         }
         return detailArrayList;
      }

      public boolean deletePackage(String packageID ,String packageName) throws SQLException, ClassNotFoundException {
       return DbConnection.getInstance().getConnection().prepareStatement("Delete FROM package WHERE PackageID='"+packageID+"' OR Name='"+packageName+"'").executeUpdate()>0;
      }
  }

