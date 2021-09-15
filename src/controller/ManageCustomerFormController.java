package controller;

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
        try {
            Customer customerDetail = new CustomerController().getCustomerDetail(txtSearch.getText());
            if (customerDetail!=null){
                txtCustomerName.setText(customerDetail.getCustomerName());
                txtCustomerMobile.setText(customerDetail.getCustomerMobile());
                txtFCustomerAddress.setText(customerDetail.getCustomerAddress());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "No customer for this number", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update_OnAction(ActionEvent actionEvent) {
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
    }

    private void clearFields(){
        txtSearch.clear();
        txtCustomerMobile.clear();
        txtFCustomerAddress.clear();
        txtCustomerName.clear();
    }

}
