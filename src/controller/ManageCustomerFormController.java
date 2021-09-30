package controller;

import ValidationFields.Validation;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.sql.SQLException;

public class ManageCustomerFormController {
    public TextArea txtFCustomerAddress;
    public AnchorPane context;
    public JFXTextField txtSearch;
    public TextField txtCustomerName;
    public TextField txtCustomerMobile;

    public void search_OnAction(ActionEvent actionEvent) {
        if (new Validation().mobileNumberValidation(txtSearch)){
            try {
                Customer customerDetail = new CustomerController().getCustomerDetail(txtSearch.getText());
                if (customerDetail!=null){
                    txtCustomerName.setText(customerDetail.getCustomerName());
                    txtCustomerMobile.setText(customerDetail.getCustomerMobile());
                    txtFCustomerAddress.setText(customerDetail.getCustomerAddress());
                    txtSearch.setStyle(null);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No customer for this number", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    txtSearch.setStyle(null);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid number", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }

    }

    public void update_OnAction(ActionEvent actionEvent) {
        Validation validation = new Validation();
        if (validation.nameValidation(txtCustomerName)&&validation.addressValidation(txtFCustomerAddress,"Customer")&&validation.mobileNumberValidation(txtCustomerMobile)){

        }
        try {
            if (new CustomerController().updateCustomer(new Customer(txtCustomerName.getText(),txtFCustomerAddress.getText(),txtCustomerMobile.getText()),txtSearch.getText())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                clearFields();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "please check & try again", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
    }

    public void Delete_OnAction(ActionEvent actionEvent) {
        if (new Validation().mobileNumberValidation(txtSearch)){
            try {
                if (new CustomerController().deleteCustomer(txtSearch.getText())){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    clearFields();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "try again", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                }
            }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "please check & try again", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }

    }

    private void clearFields(){
        txtSearch.clear();
        txtCustomerMobile.clear();
        txtFCustomerAddress.clear();
        txtCustomerName.clear();
        txtSearch.setStyle(null);
        txtCustomerMobile.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #9a9a9a");
        txtFCustomerAddress.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #9a9a9a");
        txtCustomerName.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #9a9a9a");
    }

}
