package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customer;
import model.Meal;

import java.io.IOException;
import java.sql.SQLException;

public class CashierMainFormController {


    public FlowPane flowPaneMeal;
    public FlowPane flowPanePizza;
    public FlowPane flowPaneSubmarineOthers;
    public FlowPane flowPaneDrink;
    public FlowPane flowPanePackages;
    public AnchorPane mainContext;
    public TextField txtCustomerMobile;
    public TextField txtCustomerName;
    public TextArea txtCustomerAddress;
    public JFXButton btbCustomerAdd;
    private String customerID;

    public void initialize() throws SQLException, ClassNotFoundException, IOException {
        setCustomerID();
        btbCustomerAdd.setDisable(true);

        for (Meal meal :new ItemController().getMealID_Description_Portion()
                ) {
            Image image= new Image(String.valueOf("file:"+new ItemController().getMealDetails(meal.getMealID()).getMealImage()));
            ImageView view=new ImageView(image);
            view.setFitWidth(171);
            view.setFitHeight(150);
            Button button=new Button(meal.getDescription()+" - "+meal.getPortion(),view);
            button.setFont(new Font(20));
            button.setStyle("-fx-font-weight: bold;");
            button.setContentDisplay(ContentDisplay.TOP);
            button.setPrefSize(171,160);
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

    public void addCustomer(ActionEvent actionEvent) {
        try {
            boolean b = new CustomerController().addCustomer(new Customer(customerID, txtCustomerName.getText(), txtCustomerAddress.getText(), txtCustomerMobile.getText()));
            Alert alert;
            if (b){
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully", ButtonType.OK);
                 clearFields();
            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(mainContext.getScene().getWindow());
            alert.show();
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void searchCustomer(ActionEvent actionEvent) {
        try {
            Customer customerDetail = new CustomerController().getCustomerDetail(txtCustomerMobile.getText());
            Alert alert;
            if (customerDetail==null){
                alert = new Alert(Alert.AlertType.CONFIRMATION, "NO Customer Please ADD.. ", ButtonType.OK);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
                setCustomerID();
                txtCustomerName.clear();
                txtCustomerAddress.clear();
                btbCustomerAdd.setDisable(false);

            }else {
                  txtCustomerName.setText(customerDetail.getCustomerName());
                  txtCustomerAddress.setText(customerDetail.getCustomerAddress());
                  customerID=customerDetail.getCustID();
                  btbCustomerAdd.setDisable(true);

            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }
    private void setCustomerID(){
        try {
            customerID=new CustomerController().getCustomerID();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void clearFields(){
        txtCustomerAddress.clear();
        txtCustomerName.clear();
        txtCustomerMobile.clear();
        btbCustomerAdd.setDisable(true);
    }
}
