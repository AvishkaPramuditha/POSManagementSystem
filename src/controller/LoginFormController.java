package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Password;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public JFXRadioButton rdioAdmin;
    public JFXPasswordField txtPassword;
    public JFXTextField txtUserName;
    public AnchorPane context;

    public void initialize(){
    }

    public void btnLogin(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().isEmpty()&&txtPassword.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR,"Enter userName And Password", ButtonType.CLOSE);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }else {
            Alert alert;
            try {
                if(rdioAdmin.isSelected()){
                    Password  admin = checkPassword(txtUserName.getText(), "Admin");
                    if (admin==null){
                        alert=new Alert(Alert.AlertType.ERROR,"UserName IS Wrong", ButtonType.CLOSE);
                        alert.initOwner(context.getScene().getWindow());
                        alert.show();
                    }else {
                        if (admin.getPassword().equals(String.valueOf(txtPassword.getText().hashCode()))){
                            alert=new Alert(Alert.AlertType.CONFIRMATION,"Successfully logged OK To Continue", ButtonType.OK);
                            alert.initOwner(context.getScene().getWindow());
                            alert.showAndWait();
                            context.getChildren().clear();
                            FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/AdminHomeForm.fxml"));
                            Parent load = loader.load();
                            AdminHomeFormController controller = loader.getController();
                            controller.lblAdmin.setText(admin.getEmployeeName());
                            context.getChildren().add(load);
                            System.out.println("aaaaa");
                        }else{
                            alert=new Alert(Alert.AlertType.ERROR,"Password Is Wrong", ButtonType.CLOSE);

                            alert.initOwner(context.getScene().getWindow());
                            alert.show();
                        }
                    }
                }else{
                    Password cashier = checkPassword(txtUserName.getText(), "Cashier");
                    if (cashier==null){
                        alert=new Alert(Alert.AlertType.ERROR,"UserName IS Wrong", ButtonType.CLOSE);
                        alert.initOwner(context.getScene().getWindow());
                        alert.show();
                    }else{
                        if (cashier.getPassword().equals(String.valueOf(txtPassword.getText().hashCode()))){
                            alert=new Alert(Alert.AlertType.CONFIRMATION,"Successfully logged OK To Continue", ButtonType.OK);
                            alert.initOwner(context.getScene().getWindow());
                            alert.showAndWait();
                            context.getChildren().clear();
                            FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/CashierMainForm.fxml"));
                            Parent load = loader.load();
                            CashierMainFormController controller = loader.getController();
                            controller.lblUser.setText(cashier.getEmployeeName());
                            controller.cashierID=(cashier.getEmployeeID());
                            context.getChildren().add(load);
                        }else{
                            alert=new Alert(Alert.AlertType.ERROR,"Password Is Wrong", ButtonType.CLOSE);
                            alert.initOwner(context.getScene().getWindow());
                            alert.show();
                        }
                    }
                }
            }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}
        }
       /* if(rdioAdmin.isSelected()){
            context.getChildren().clear();
            context.getChildren().add(FXMLLoader.load(getClass().getResource("../view/AdminHomeForm.fxml")));
        }else{
            context.getChildren().clear();
            context.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CashierMainForm.fxml")));
        }*/
    }

    public void btnShutDown(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "do you want to Update Trash And Finish the Day", ButtonType.YES, ButtonType.NO);
        alert.initOwner(context.getScene().getWindow());
        alert.showAndWait().ifPresent(buttonType ->{
            if (buttonType==ButtonType.YES){
                new ManageTrashFormController().finishedDay();
                Stage stage = (Stage) context.getScene().getWindow();
                stage.close();
            }else {
                Stage stage = (Stage) context.getScene().getWindow();
                stage.close();
            }
        } );

    }

    private Password checkPassword(String userName, String jobRole) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT e.EmployeeID,e.EmployeeName,p.userName,p.password From employee e INNER JOIN password p ON e.EmployeeID=p.EmployeeID WHERE p.UserName='" + userName + "' AND e.jobRole='" + jobRole + "'").executeQuery();
        if (resultSet.next()){
           return new Password(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        }
        return null;
    }
}
