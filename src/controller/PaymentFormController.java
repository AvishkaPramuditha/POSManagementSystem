package controller;

import com.jfoenix.controls.JFXButton;
import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentFormController {
    public AnchorPane context;
    public Text total;
    public Text orderID;
    public TextField cash;
    public Text balance;
    public String customerID;
    public  String cashierID;
    public JFXButton btnClose;
    public JFXButton btnOk;


    public void initialize(){
        btnClose.setDisable(false);
        btnOk.setDisable(true);
    }
    public void closePaymentStage(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }

    public void DoTransaction(ActionEvent actionEvent) {
        double totalD= Double.parseDouble(total.getText());
        double paidAmountD= Double.parseDouble(cash.getText());
        double balanceD=paidAmountD-totalD;
        balance.setText(String.valueOf(balanceD));
        try {
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO transaction VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1,orderID.getText());
            preparedStatement.setString(2,customerID);
            preparedStatement.setString(3,cashierID);
            preparedStatement.setDouble(4,totalD);
            preparedStatement.setDouble(5,paidAmountD);
            preparedStatement.setDouble(6,balanceD);
            boolean b=preparedStatement.executeUpdate()>0;
            if (b){
                btnClose.setDisable(true);
                btnOk.setDisable(false);
                System.out.println("print bill");
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void returnToHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/CashierMainForm.fxml"));
        Parent parent=loader.load();
        CashierMainFormController controller = loader.getController();
        controller.cancelOrder(null);
        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }
}
