package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Order;
import model.OrderDetail;
import view.tm.OrderTM;

import java.io.IOException;
import java.sql.SQLException;

public class ManageOrderFormController {


    public AnchorPane context;
    public TextField txtSearchDT;
    public Text lblCustomerNameDT;
    public ComboBox<String> cmbOrderTypeDT;
    public ObservableList<OrderTM> tableItems=FXCollections.observableArrayList();
    public TableView<OrderTM> dineTakeTableView;
    public AnchorPane mainContext;
    public Text lblTotal;

    public void initialize(){
        cmbOrderTypeDT.setItems(FXCollections.observableArrayList("Dine - IN","Take-Away"));
        dineTakeTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("foodCode"));
        dineTakeTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        dineTakeTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        dineTakeTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dineTakeTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void moveToPayment(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Employee");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PaymentForm.fxml"))));
        stage.show();
    }

    public void searchOrder(ActionEvent actionEvent) {
        try {
            Order orderDetail = new OrderController().getOrderDetail(txtSearchDT.getText(), cmbOrderTypeDT.getSelectionModel().getSelectedItem());
            if (orderDetail!=null) {
                lblCustomerNameDT.setText(orderDetail.getCustName());
                lblTotal.setText(String.valueOf(orderDetail.getTotal()));
                for (OrderDetail detail : orderDetail.getOrderedItem()
                ) {
                    tableItems.add(new OrderTM(detail.getFoodID(), detail.getDescription(), detail.getSellingPrice(), detail.getQuantity(), (detail.getSellingPrice() * detail.getQuantity()), detail.getFoodType()));
                }
                dineTakeTableView.setItems(tableItems);
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "IInvalid OrderID ....", ButtonType.OK);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }
}
