package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CashierMainFormController {


    public FlowPane flowPaneMeal;
    public FlowPane flowPanePizza;
    public FlowPane flowPaneSubmarineOthers;
    public FlowPane flowPaneDrink;
    public FlowPane flowPanePackages;
    public AnchorPane mainContext;

    public void initialize(){
/*
        for (int i = 0; i < 10; i++) {
            Button button=new Button(String.valueOf(i));
            button.setPrefSize(171,150);
            flowPaneMeal.getChildren().add(button);
        }

        for (int i = 0; i < 15; i++) {
            Button button=new Button(String.valueOf(i));
            button.setPrefSize(171,150);
            flowPanePizza.getChildren().add(button);
        }

        for (int i = 0; i < 8; i++) {
            Button button=new Button(String.valueOf(i));
            button.setPrefSize(171,150);
            flowPaneSubmarineOthers.getChildren().add(button);
        }

        for (int i = 0; i < 12; i++) {
            Button button=new Button(String.valueOf(i));
            button.setPrefSize(171,150);
            flowPaneDrink.getChildren().add(button);
        }

        for (int i = 0; i < 5; i++) {
            Button button=new Button(String.valueOf(i));
            button.setPrefSize(171,150);
            flowPanePackages.getChildren().add(button);
        }

*/

    }

    public void moveToPaymentForm(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("payments");
        stage.initOwner((Stage)mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PaymentForm.fxml"))));
        stage.show();
    }

    public void moveToReservation(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Reservation");
        stage.initOwner((Stage)mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReservationForm.fxml"))));
        stage.show();
    }

    public void moveToManageOrder(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Order");
        stage.initOwner((Stage)mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml"))));
        stage.show();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        mainContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }
}
