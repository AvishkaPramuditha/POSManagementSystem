package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminHomeFormController {

    public AnchorPane context;

    public void logout(ActionEvent actionEvent) throws IOException {
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }

    public void moveToManageFoodItem(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setTitle("Manage Food Item");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageFoodItemForm.fxml"))));
        stage.show();
    }

    public void moveToManageEmployee(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Employee");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageEmployeeForm.fxml"))));
        stage.show();
    }

    public void moveToReports(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Reports");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReportForm.fxml"))));
        stage.show();
    }

    public void moveToManageCustomer(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Customer");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml"))));
        stage.show();
    }

    public void moveToManageDiscount(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.setTitle("Manage Discount");
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageDiscountForm.fxml"))));
        stage.show();
    }

    public void moveToManageTrash(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.setTitle("Manage Trash");
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageTrashForm.fxml"))));
        stage.show();
    }

    public void moveToChangePassword(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Change Password");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChangePasswordForm.fxml"))));
        stage.show();
    }

    public void moveToManagePackage(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.setTitle("Manage Package");
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManagePackageForm.fxml"))));
        stage.show();
    }
}
