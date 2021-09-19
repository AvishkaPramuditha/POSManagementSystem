package controller;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManageDiscountFormController {
    public TextField txtDiscountID;
    public TextField txtDiscountPrice;
    public TextField txtCloseDate;
    public TextField txtStartDate;
    public TableView tableView;
    public ComboBox<Item> cmbFoodItem;
    public AnchorPane context;

    public void initialize(){
    setCmbFoodItem();
}
    public void add(ActionEvent actionEvent) {
        boolean b;
        if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof Meal){
           b = new DiscountController().addMealDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())),  cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof Pizza){
            b = new DiscountController().addPizzaDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())),  cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof SubBurgersAndOthers){
            b = new DiscountController().addSubDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())), cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else{
            b = new DiscountController().addDrinkDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())), cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }
        Alert alert;
        if (b){
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved  Successfully", ButtonType.OK);
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(context.getScene().getWindow());
        alert.show();
    }

    public void update(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }

    public void setCmbFoodItem()  {
    try {
        cmbFoodItem.getItems().addAll(new ItemController().getMealID_Description_Portion());
        cmbFoodItem.getItems().addAll(new ItemController().getPizzaID_Description_Portion());
        cmbFoodItem.getItems().addAll(new ItemController().getSubs_Description());
        cmbFoodItem.getItems().addAll(new ItemController().getDrink_Description());
    }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}

    }
}
