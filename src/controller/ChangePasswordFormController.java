package controller;

import com.jfoenix.controls.JFXTextField;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordFormController {


    public ComboBox<String> cmbUserName;
    public AnchorPane context;
    public PasswordField txtCurrentPassword;
    public PasswordField txtConformNewPassword;
    public PasswordField txtNewPassword;

    public void initialize(){
         setCmbUserNames();
    }


    public void changePassword(ActionEvent actionEvent) {
        if (checkCurrentPassword(txtCurrentPassword.getText(),cmbUserName.getSelectionModel().getSelectedItem())){
            if (txtNewPassword.getText().equals(txtConformNewPassword.getText())){
                if (passwordChange(cmbUserName.getSelectionModel().getSelectedItem(),txtNewPassword.getText())){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password change successfully", ButtonType.CLOSE);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();
                    clear();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "try again", ButtonType.OK);
                    alert.initOwner(context.getScene().getWindow());
                    alert.show();

                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password did not match", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Current Password is incorrect", ButtonType.CLOSE);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    private void setCmbUserNames(){
               try {
                 ResultSet  resultSet = DbConnection.getInstance().getConnection().prepareStatement("Select userName from password").executeQuery();
                   ObservableList<String> list= FXCollections.observableArrayList();
                   while (resultSet.next()){
                       list.add(resultSet.getString(1));
                   }
                   cmbUserName.setItems(list);
               } catch (SQLException | ClassNotFoundException e) {
                   e.printStackTrace();
               }
    }

    private boolean checkCurrentPassword(String currentPassword,String userName){
        try {
            ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("select password from password where userName='" +userName+ "'").executeQuery();
            if (resultSet.next()){
                if (resultSet.getString(1).equals(currentPassword)){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}

        return false;
    }
   private boolean passwordChange(String userName,String newPassword)  {
       try {
           return DbConnection.getInstance().getConnection().prepareStatement("UPDATE password Set password='"+newPassword+"' where UserName='"+userName+"'").executeUpdate()>0;
       } catch (SQLException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       return false;
   }

   private void clear(){
        cmbUserName.getSelectionModel().clearSelection();
        txtNewPassword.clear();
        txtCurrentPassword.clear();
        txtConformNewPassword.clear();
   }

    public void clearFields(Event event) {
        clear();
    }
}
