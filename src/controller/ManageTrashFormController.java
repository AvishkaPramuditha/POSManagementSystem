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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

}
