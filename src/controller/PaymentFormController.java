package controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PaymentFormController {
    public AnchorPane context;

    public void closePaymentStage(ActionEvent actionEvent) {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }
}
