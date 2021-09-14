package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXRadioButton rdioAdmin;
    public JFXTextField txtPassword;
    public JFXTextField txtUserName;
    public AnchorPane context;
    public JFXButton btnShutDown;


    public void initialize(){
     
    }





    public void btnLogin(ActionEvent actionEvent) throws IOException {
      if(rdioAdmin.isSelected()){
          context.getChildren().clear();
          context.getChildren().add(FXMLLoader.load(getClass().getResource("../view/AdminHomeForm.fxml")));
      }else{
          context.getChildren().clear();
          context.getChildren().add(FXMLLoader.load(getClass().getResource("../view/CashierMainForm.fxml")));
      }



    }


    public void btnShutDown(MouseEvent mouseEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }
}
