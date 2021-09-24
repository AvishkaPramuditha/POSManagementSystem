package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import model.Package;
import view.tm.OrderTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
    public TableView<OrderTM> orderTblView;
    public ComboBox<String> cmbOrderType;
    public Text lblSubTot;
    public Text lblDelivery;
    public Text lblGrandTot;
    private String customerID;
    private  ObservableList<OrderTM> orderTMS= FXCollections.observableArrayList();
    public void initialize() {
        cmbOrderType.setItems(FXCollections.observableArrayList("Dine - IN","Take-Away","Delivery"));
        cmbOrderType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {calculation();});
        setCustomerID();
        btbCustomerAdd.setDisable(true);
        setMealsButton();
        setPizzaButton();
        setSubButton();
        setDrinkButton();
        setPackageButton();
        orderTblView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("foodCode"));
        orderTblView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        orderTblView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        orderTblView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderTblView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("amount"));

    }
    private void setPackageButton(){
        try {
            for (String packageCode:new PackageController().getPackageCode()
                 ) {
                Package packageDetail = new PackageController().getPackageDetail(packageCode, null);
                Image image = new Image("file:src/asset/pack.jpeg");
                ImageView view = new ImageView(image);
                view.setFitWidth(350);
                view.setFitHeight(250);
                Button button = new Button(packageDetail.getName() + " - Rs "+packageDetail.getPrice(),view);
                button.setFont(new Font(20));
                button.setContentDisplay(ContentDisplay.TOP);
                button.setStyle("-fx-font-weight: bold;");
                button.setPrefSize(300, 250);
                button.setOnAction(event -> {
                    try {
                        ArrayList<PackageDetail> packD = packageDetail.getPackageDetails();
                        OrderTM orderTM = new OrderTM(packageDetail.getPackageID(), packageDetail.getName(), packageDetail.getPrice(), 1, packageDetail.getPrice() * 1);
                        orderTblView.getItems().add(orderTM);
                        for (PackageDetail detail:packD
                        ) {
                            String description;
                            if (detail.getFoodType()=="Meal"){description=new ItemController().getMealDescription(detail.getFoodCode());}else if (detail.getFoodType()=="Pizza"){description=new ItemController().getPizzaDescription(detail.getFoodCode());}
                            else if(detail.getFoodType()=="Sub"){description=new ItemController().getSubDescription(detail.getFoodCode());}else {description=new ItemController().getDrinkDescription(detail.getFoodCode());}
                            OrderTM orderT = new OrderTM(packageDetail.getPackageID() + "-" + detail.getFoodCode(), description, 0, detail.getQuantity(), 0);
                            orderTMS.add(orderT);
                            calculation();
                        }
                    }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
                });
                flowPanePackages.getChildren().add(button);

            }
            orderTblView.getItems().addAll(orderTMS);

        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }
    private void setDrinkButton(){
        try {
            for (DrinkButton drink :new ItemController().getDrink()
            ) {
                if (drink.isAvailable()) {
                    Image image = new Image("file:" + new ItemController().getDrinkDetails(drink.getBeverageID()).getDrinkImage());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(250);
                    view.setFitHeight(200);
                    double price;
                    if (drink.getDiscountPrice() != 0) {
                        price = drink.getDiscountPrice();
                    } else {
                        price = drink.getUnitPrice();
                    }
                    Button button = new Button(drink.getDescription() + " - Rs " + price, view);
                    button.setFont(new Font(20));
                    button.setStyle("-fx-font-weight: bold;");
                    button.setContentDisplay(ContentDisplay.TOP);
                    button.setPrefSize(200, 160);
                    button.setOnAction(event -> {
                        OrderTM orderTM = new OrderTM(drink.getBeverageID(), drink.getDescription(), price, 1,price*1);
                        if (!searchOrderTM(orderTM)){
                            orderTMS.add(orderTM);
                            calculation();
                        }
                    });
                    flowPaneDrink.getChildren().add(button);
                }
            }
            orderTblView.getItems().addAll(orderTMS);
        }catch (ClassNotFoundException | SQLException | IOException e){e.printStackTrace();}
    }
    private void setSubButton(){
        try {
            for (SubButton sub :new ItemController().getSub()
            ) { AtomicInteger max= new AtomicInteger();
                if (sub.getQuantityOnHand()!=0) {
                    Image image = new Image("file:" + new ItemController().getSubBurgersAndOthersDetails(sub.getSandwichId()).getSubImage());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(250);
                    view.setFitHeight(200);
                    double price;
                    if (sub.getDiscountPrice() != 0) {
                        price = sub.getDiscountPrice();
                    } else {
                        price = sub.getUnitPrice();
                    }
                    Button button = new Button(sub.getDescription() + " - Rs " + price, view);
                    button.setFont(new Font(20));
                    button.setStyle("-fx-font-weight: bold;");
                    button.setContentDisplay(ContentDisplay.TOP);
                    button.setPrefSize(200, 160);
                    button.setOnAction(event -> {
                        if (sub.getQuantityOnHand()>max.get()){
                            OrderTM orderTM = new OrderTM(sub.getSandwichId(),sub.getDescription(), price, 1,price*1);
                            if (!searchOrderTM(orderTM)){
                                orderTMS.add(orderTM);
                                calculation();
                            }
                            max.getAndIncrement();
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "not Available right now.....");
                            alert.initOwner(mainContext.getScene().getWindow());
                            alert.showAndWait();
                        }
                    });
                    flowPaneSubmarineOthers.getChildren().add(button);
                }
            }
            orderTblView.getItems().addAll(orderTMS);
        }catch (ClassNotFoundException | SQLException | IOException e){e.printStackTrace();}
    }
    private void setPizzaButton(){
        try {
            for (PizzaButton pizza :new ItemController().getPizza()
            ) {    AtomicInteger max= new AtomicInteger();
                if (pizza.getQuantityOnHand()!=0) {
                    Image image = new Image("file:" + new ItemController().getPizzaDetails(pizza.getPizzaID()).getPizzaImage());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(250);
                    view.setFitHeight(200);
                    double price;
                    if (pizza.getDiscountPrice() != 0) {
                        price = pizza.getDiscountPrice();
                    } else {
                        price = pizza.getUnitPrice();
                    }
                    Button button = new Button(pizza.getDescription() + " " + pizza.getSize() + " - Rs " + price, view);
                    button.setFont(new Font(20));
                    button.setStyle("-fx-font-weight: bold;");
                    button.setContentDisplay(ContentDisplay.TOP);
                    button.setPrefSize(250, 160);
                    button.setOnAction(event -> {
                        if (pizza.getQuantityOnHand() > max.get()) {
                        OrderTM orderTM = new OrderTM(pizza.getPizzaID(), pizza.getDescription(), price, 1, price * 1);
                        if (!searchOrderTM(orderTM)) {
                            orderTMS.add(orderTM);
                            calculation();
                        }
                            max.getAndIncrement();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR, "not Available right now.....");
                            alert.initOwner(mainContext.getScene().getWindow());
                            alert.showAndWait();
                        }
                    });
                    flowPanePizza.getChildren().add(button);
                }
            }
            orderTblView.getItems().addAll(orderTMS);
        }catch (ClassNotFoundException | SQLException | IOException e){e.printStackTrace();}
    }
    private void setMealsButton(){
        try {
            for (MealButton meal :new ItemController().getMeals()
            ) {
                if (meal.isAvailable()) {
                    Image image = new Image("file:" + new ItemController().getMealDetails(meal.getMealID()).getMealImage());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(250);
                    view.setFitHeight(200);
                    double price;
                    if (meal.getDiscountPrice() != 0) {
                        price = meal.getDiscountPrice();
                    } else {
                        price = meal.getUnitPrice();
                    }
                    Button button = new Button(meal.getDescription() + " " + meal.getPortion() + " - Rs " + price, view);
                    button.setFont(new Font(20));
                    button.setStyle("-fx-font-weight: bold;");
                    button.setContentDisplay(ContentDisplay.TOP);
                    button.setPrefSize(200, 160);
                    button.setOnAction(event -> {
                        OrderTM orderTM = new OrderTM(meal.getMealID(), meal.getDescription(), price, 1,price*1);
                        if (!searchOrderTM(orderTM)){
                         orderTMS.add(orderTM);
                            calculation();
                        }
                    });
                    flowPaneMeal.getChildren().add(button);
                }
            }
            orderTblView.setItems(orderTMS);
        }catch (ClassNotFoundException | SQLException | IOException e){e.printStackTrace();}

    }

    public void moveToPaymentForm(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("payments");
        stage.initOwner(mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PaymentForm.fxml"))));
        stage.show();
    }

    public void moveToReservation(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Reservation");
        stage.initOwner(mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReservationForm.fxml"))));
        stage.show();
    }

    public void moveToManageOrder(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Order");
        stage.initOwner(mainContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageOrderForm.fxml"))));
        stage.show();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        mainContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
    }

    private boolean searchOrderTM(OrderTM tm) {
        for (OrderTM tm1 : orderTMS
        ) {
            if (tm1.getFoodCode().equals(tm.getFoodCode())&&tm1.getDescription().equals(tm.getDescription())) {
                tm1.setQuantity(tm1.getQuantity() + tm.getQuantity());
                tm1.setAmount(tm1.getQuantity()*tm1.getPrice());
                orderTblView.refresh();
                calculation();
                return true;
            }
        }
        return false;
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

    public void deleteItemFromTable(ActionEvent actionEvent) {
        OrderTM selectedItem = orderTblView.getSelectionModel().getSelectedItem();
       if (selectedItem.getPrice()!=0) {
           ObservableList<OrderTM> tmObservableList = FXCollections.observableArrayList(orderTMS);
           for (OrderTM v : tmObservableList
           ) {
               if (v.getFoodCode().contains(selectedItem.getFoodCode())) {
                   orderTMS.remove(v);
               }
           }
           orderTblView.setItems(orderTMS);
           calculation();
       }
    }

    public void clearTable(ActionEvent actionEvent) {
        orderTblView.getItems().clear();
        calculation();
    }

    public void increaseQuantity(ActionEvent actionEvent) {
        OrderTM selectedItem = orderTblView.getSelectionModel().getSelectedItem();
        if (selectedItem.getPrice()!=0){
            selectedItem.setQuantity(selectedItem.getQuantity()+1);
            selectedItem.setAmount(selectedItem.getQuantity()*selectedItem.getPrice());
            orderTblView.refresh();
            calculation();}
    }

    public void decreaseQuantity(ActionEvent actionEvent) {
        OrderTM selectedItem = orderTblView.getSelectionModel().getSelectedItem();
        if (selectedItem.getQuantity()!=0&&selectedItem.getPrice()!=0){
            selectedItem.setQuantity(selectedItem.getQuantity()-1);
            selectedItem.setAmount(selectedItem.getQuantity()*selectedItem.getPrice());
            orderTblView.refresh();
            calculation();}

    }
    private void calculation(){
        double subTot=0;
        double deliveryCharges=0;
        double grandTotal=0;
        for (OrderTM r:orderTMS
             ) {
            subTot+=r.getAmount();
        }
       if (cmbOrderType.getSelectionModel().getSelectedItem()=="Delivery"){deliveryCharges=subTot*5/100;}
       grandTotal=subTot+deliveryCharges;
       lblSubTot.setText(String.valueOf(subTot));
       lblDelivery.setText(String.valueOf(deliveryCharges));
       lblGrandTot.setText(String.valueOf(grandTotal));
    }
}
