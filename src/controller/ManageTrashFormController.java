package controller;

import com.jfoenix.controls.JFXDatePicker;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import view.tm.TrashTM;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManageTrashFormController {
    public JFXDatePicker datePicker;
    public TableView<TrashTM> tblView;
    public TableColumn colFoodCode;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colReason;
    public AnchorPane context;

    public void initialize(){
        colFoodCode.setCellValueFactory(new PropertyValueFactory("foodCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        colReason.setCellValueFactory(new PropertyValueFactory("reason"));

   }
    public void delete(ActionEvent actionEvent) {

        try {
            if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM trash WHERE TDate='"+datePicker.getValue().toString()+"'AND FoodCode= '"+ tblView.getSelectionModel().getSelectedItem().getFoodCode()+"'").executeUpdate()>0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                loadData(null);
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again.....", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
    }

    public void clear(ActionEvent actionEvent) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement("DELETE  from trash WHERE TDate='"+datePicker.getValue().toString()+"'").executeUpdate();
            loadData(null);
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
    }

    public void loadData(Event event) {
        LocalDate date = datePicker.getValue();
        ObservableList<TrashTM> list= FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT FoodCode,Description,QuantityWaste,Reason FROM trash WHERE TDate='" + date.toString() + "'").executeQuery();

            while (resultSet.next()){
                list.add(new TrashTM(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getString(4)));
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        tblView.setItems(list);
    }

    private ArrayList<TrashTM> getItemsForTrash() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("(SELECT pizzaID,Description, QuantityOnHand FROM pizza WHERE QuantityOnHand!=0)UNION (SELECT SandwichID,Description,QuantityOnHand FROM subBurgersAndOthers  WHERE QuantityOnHand!=0)").executeQuery();
       ArrayList<TrashTM> list=new ArrayList<>();
       while (resultSet.next()){
           list.add(new TrashTM(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3)));
       }
       return list;
    }
    private boolean  updateTrash(ArrayList<TrashTM> trashTM , Date date) throws SQLException, ClassNotFoundException {
        for (TrashTM tm:trashTM
             ) {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO trash VALUES (?,?,?,?,?)");
            preparedStatement.setDate(1,date);
            preparedStatement.setString(2,tm.getFoodCode());
            preparedStatement.setString(3,tm.getDescription());
            preparedStatement.setString(4,"Day Finished");
            preparedStatement.setInt(5,tm.getQuantity());
            if (preparedStatement.executeUpdate()>0){}else{return false;}
        }
        return true;
    }

    private boolean resetQuantity(){
        Connection connection = null;
        try {
            connection=DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE pizza SET QuantityOnHand=0");
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE subBurgersAndOthers SET QuantityOnHand=0");
            if (preparedStatement.executeUpdate()>0){
               if (preparedStatement1.executeUpdate()>0){
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
        }catch (ClassNotFoundException e){e.printStackTrace();}finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

    }


    public void finishedDay(){
        try {
            ArrayList<TrashTM> itemsForTrash = getItemsForTrash();
            if (updateTrash(itemsForTrash,getDate())){
                resetQuantity();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }
   private Date getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM--dd");
       LocalDate now = LocalDate.now();
       return Date.valueOf(now);

   }
    }
