package controller;

import ValidationFields.Validation;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Employee;

import java.sql.SQLException;

public class ManageEmployeeFormController {
    public TextField txtEmployeeID;
    public TextField txtEmployeeName;
    public TextField txtEmployeeNIC;
    public TextField txtContactNumber;
    public TextArea txtEmployeeAddress;
    public TextField txtBikeNo;
    public ComboBox<String> cmbJobRole;
    public TextField txtDrivingLicense;
    public TextField txtWorkingHours;
    public Pane paneLogonPassword;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public PasswordField txtConformPassword;
    public AnchorPane context;


    public void initialize(){
        cmbJobRole.setItems(FXCollections.observableArrayList("Cashier","Rider","Chef","Waiter","Kitchen Helper","Admin"));
        cmbJobRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue=="Rider"){
                txtBikeNo.setDisable(false);
                txtDrivingLicense.setDisable(false);
            }else {
                txtBikeNo.setDisable(true);
                txtDrivingLicense.setDisable(true);
            }
        });
    }
    public void addEmployee(ActionEvent actionEvent) {
        Validation validation = new Validation();
        if (cmbJobRole.getSelectionModel().getSelectedItem().equals("Rider")){
            if (validation.idValidation(txtEmployeeID)&&validation.nameValidation(txtEmployeeName)&&validation.nicValidation(txtEmployeeNIC)&&validation.mobileNumberValidation(txtContactNumber)&&validation.addressValidation(txtEmployeeAddress,"XX")&&validation.bikeNo(txtBikeNo)&&validation.drivingLicense(txtDrivingLicense)&&validation.workingHours(txtWorkingHours)&&validation.userName(txtUserName)&&validation.password(txtPassword)&&validation.password(txtConformPassword)&&cmbJobRole.getSelectionModel()!=null){
                boolean b = new EmployeeController().addEmployee(new Employee(txtEmployeeID.getText(), txtEmployeeNIC.getText(), txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtContactNumber.getText(), cmbJobRole.getSelectionModel().getSelectedItem(), txtWorkingHours.getText(), txtBikeNo.getText(), txtDrivingLicense.getText(), txtUserName.getText(), String.valueOf(txtConformPassword.getText().hashCode())));
                if (b){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully .....", ButtonType.CLOSE);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    reset(null);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }else {
            if (validation.idValidation(txtEmployeeID)&&validation.nameValidation(txtEmployeeName)&&validation.nicValidation(txtEmployeeNIC)&&validation.mobileNumberValidation(txtContactNumber)&&validation.addressValidation(txtEmployeeAddress,"XX")&&validation.workingHours(txtWorkingHours)&&validation.userName(txtUserName)&&validation.password(txtPassword)&&validation.password(txtConformPassword)&&cmbJobRole.getSelectionModel()!=null){
                boolean b = new EmployeeController().addEmployee(new Employee(txtEmployeeID.getText(), txtEmployeeNIC.getText(), txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtContactNumber.getText(), cmbJobRole.getSelectionModel().getSelectedItem(), txtWorkingHours.getText(), txtBikeNo.getText(), txtDrivingLicense.getText(), txtUserName.getText(), String.valueOf(txtConformPassword.getText().hashCode())));
                if (b){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully .....", ButtonType.CLOSE);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    reset(null);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }
    }

    public void reset(ActionEvent actionEvent) {
        txtEmployeeID.clear();
        txtEmployeeNIC.clear();
        txtUserName.clear();
        txtEmployeeAddress.clear();
        txtContactNumber.clear();
        txtWorkingHours.clear();
        txtBikeNo.clear();
        txtEmployeeName.clear();
        txtDrivingLicense.clear();
        cmbJobRole.getSelectionModel().clearSelection();
        txtUserName.clear();
        txtPassword.clear();
        txtConformPassword.clear();
        paneLogonPassword.setVisible(true);

        txtEmployeeID.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtEmployeeNIC.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtUserName.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtEmployeeAddress.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtContactNumber.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtWorkingHours.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtBikeNo.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtEmployeeName.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtDrivingLicense.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtUserName.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtPassword.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
        txtConformPassword.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color:  #5d5d53");
    }

    public void searchEmployee(ActionEvent actionEvent) {
        if (new Validation().idValidation(txtEmployeeID)){
            try {
                Employee employee = new EmployeeController().searchEmployee(txtEmployeeID.getText());
                if (employee!=null){
                    paneLogonPassword.setVisible(false);
                    txtEmployeeNIC.setText(employee.getEmployeeNIC());
                    txtEmployeeName.setText(employee.getEmployeeName());
                    txtEmployeeAddress.setText(employee.getEmployeeAddress());
                    txtContactNumber.setText(employee.getEmployeeContactNumber());
                    cmbJobRole.setValue(employee.getJobRole());
                    txtWorkingHours.setText(employee.getWorkingHours());
                    txtBikeNo.setText(employee.getBikeNo());
                    txtDrivingLicense.setText(employee.getDrivingLicenseNumber());
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "NO Employee for this ID .....", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    paneLogonPassword.setVisible(true);
                }
            }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Employee ID .....", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }

    }

    public void updateEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Validation validation = new Validation();
        if (cmbJobRole.getSelectionModel().getSelectedItem().equals("Rider")){
            if (validation.idValidation(txtEmployeeID)&&validation.nameValidation(txtEmployeeName)&&validation.nicValidation(txtEmployeeNIC)&&validation.mobileNumberValidation(txtContactNumber)&&validation.addressValidation(txtEmployeeAddress,"XX")&&validation.bikeNo(txtBikeNo)&&validation.drivingLicense(txtDrivingLicense)&&validation.workingHours(txtWorkingHours)){
                if (new EmployeeController().updateEmployee(new Employee(txtEmployeeID.getText(), txtEmployeeNIC.getText(), txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtContactNumber.getText(), cmbJobRole.getSelectionModel().getSelectedItem(), txtWorkingHours.getText(), txtBikeNo.getText(), txtDrivingLicense.getText()))){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    reset(null);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }else{
            if (validation.idValidation(txtEmployeeID)&&validation.nameValidation(txtEmployeeName)&&validation.nicValidation(txtEmployeeNIC)&&validation.mobileNumberValidation(txtContactNumber)&&validation.addressValidation(txtEmployeeAddress,"XX")&&validation.workingHours(txtWorkingHours)&&!(cmbJobRole.getSelectionModel().getSelectedItem()==null)){
                boolean  b=new EmployeeController().updateEmployee(new Employee(txtEmployeeID.getText(), txtEmployeeNIC.getText(), txtEmployeeName.getText(), txtEmployeeAddress.getText(), txtContactNumber.getText(), cmbJobRole.getSelectionModel().getSelectedItem(), txtWorkingHours.getText(), txtBikeNo.getText(), txtDrivingLicense.getText()));
                if (b){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    reset(null);
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        if (new Validation().idValidation(txtEmployeeID)){
            if (new EmployeeController().deleteEmployee(txtEmployeeID.getText(),cmbJobRole.getValue())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                reset(null);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Employee ID Again .....", ButtonType.CLOSE);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }

    }
}
