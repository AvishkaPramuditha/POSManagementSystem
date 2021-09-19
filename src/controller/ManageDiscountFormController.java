package controller;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import view.tm.DiscountTM;


import java.sql.SQLException;

public class ManageDiscountFormController {
    public TextField txtDiscountID;
    public TextField txtDiscountPrice;
    public TextField txtCloseDate;
    public TextField txtStartDate;
    public TableView<DiscountTM> tableView;
    public ComboBox<Item> cmbFoodItem;
    public AnchorPane context;

    public void initialize(){
        setCmbFoodItem();
        loadItemsToTable();
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("discountID"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("FoodItem"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("closeDate"));

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue!=null){
                DiscountTM itemDetail = new DiscountController().getItemDetail(newValue.getDiscountID());
                txtDiscountID.setText(itemDetail.getDiscountID());
                txtDiscountPrice.setText(String.valueOf(itemDetail.getDiscountPrice()));
                txtStartDate.setText(itemDetail.getStartDate());
                txtCloseDate.setText(itemDetail.getCloseDate());
                txtDiscountID.setDisable(true);}
            }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}

       });
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
            loadItemsToTable();
            clear(null);
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(context.getScene().getWindow());
        alert.show();
    }

    public void update(ActionEvent actionEvent) {
        boolean b;
        if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof Meal){
            b = new DiscountController().updateMealDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())),  cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof Pizza){
            b = new DiscountController().updatePizzaDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())),  cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else if (cmbFoodItem.getSelectionModel().getSelectedItem() instanceof SubBurgersAndOthers){
            b = new DiscountController().updateSubDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())), cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }else{
            b = new DiscountController().updateDrinkDiscount(new Discount(txtDiscountID.getText(), txtStartDate.getText(), txtCloseDate.getText(), Double.valueOf(txtDiscountPrice.getText())), cmbFoodItem.getSelectionModel().getSelectedItem().getID());
        }
        Alert alert;
        if (b){
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Update  Successfully", ButtonType.OK);
            loadItemsToTable();
            clear(null);
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(context.getScene().getWindow());
        alert.show();

    }

    public void delete(ActionEvent actionEvent) {
        boolean b = false;
        try {
            b = new DiscountController().delete(txtDiscountID.getText());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Alert alert;
        if (b){
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved  Successfully", ButtonType.OK);
            clear(null);
            loadItemsToTable();
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(context.getScene().getWindow());
        alert.show();
    }

    private void setCmbFoodItem()  {
    try {
        cmbFoodItem.getItems().addAll(new ItemController().getMealID_Description_Portion());
        cmbFoodItem.getItems().addAll(new ItemController().getPizzaID_Description_Portion());
        cmbFoodItem.getItems().addAll(new ItemController().getSubs_Description());
        cmbFoodItem.getItems().addAll(new ItemController().getDrink_Description());
    }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}

    }

    private void loadItemsToTable(){
        try {
            tableView.setItems(new DiscountController().getDiscountItems());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear(ActionEvent actionEvent) {
        txtDiscountID.setDisable(false);
        txtDiscountID.clear();
        txtDiscountPrice.clear();
        txtStartDate.clear();
        txtCloseDate.clear();
        cmbFoodItem.getSelectionModel().clearSelection();
    }
}
