package controller;

import ValidationFields.Validation;
import com.jfoenix.controls.JFXButton;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import model.Package;
import view.tm.OrderTM;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderFormController {


    public AnchorPane context;
    public TextField txtSearchDT;
    public Text lblCustomerNameDT;
    public ComboBox<String> cmbOrderTypeDT;
    public ObservableList<OrderTM> tableItems=FXCollections.observableArrayList();
    public ObservableList<OrderTM> tableItemsD=FXCollections.observableArrayList();
    public TableView<OrderTM> dineTakeTableView;
    public AnchorPane mainContext;
    public Text lblTotal;
    public TextField txtQuantity;
    public ComboBox<Meal> cmbMeal;
    public ComboBox<Drink> cmbDrink;
    public ComboBox<Pizza> cmbPizza;
    public ComboBox<SubBurgersAndOthers> cmbSub;
    public ComboBox<String> cmbPackage;
    public JFXButton btnAdd;
    public Tab tabDineIn;
    public TextField txtCashDT;
    public Text lblBalanceDT;
    public TextField txtSearchD;
    public Text lblCustomerNameD;
    public TableView<OrderTM> tableViewD;
    public ComboBox<Meal> cmbMealD;
    public ComboBox<Drink> cmbDrinkD;
    public ComboBox<Pizza> cmbPizzaD;
    public ComboBox<String> cmbPackageD;
    public ComboBox<SubBurgersAndOthers> cmbSubD;
    public TextField txtQuantityD;
    public Text lblSubTotalD;
    public Text lblGrandTotalD;
    public Text lblDeliveryChargesD;
    public ComboBox<Employee> cmbDriver;
    public TextArea txtAddress;
    public TextField txtCashD;
    public Text lblBalanceD;
    private String customerID;
    private ArrayList<OrderTM> deleted=new ArrayList<>();
    private ArrayList<OrderTM> added=new ArrayList<>();

    public void initialize(){
        btnAdd.setDisable(true);
        cmbOrderTypeDT.setItems(FXCollections.observableArrayList("Dine - IN","Take-Away"));
        dineTakeTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("foodCode"));
        dineTakeTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        dineTakeTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        dineTakeTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dineTakeTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("amount"));

        tableViewD.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("foodCode"));
        tableViewD.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tableViewD.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewD.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableViewD.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("amount"));
        setCmbDT();
        setCmbD();

    }
    private void setCmbDT(){
        try {
            cmbMeal.setItems(new ItemController().getMealID_Description_Portion());
            cmbPizza.setItems(new ItemController().getPizzaID_Description_Portion());
            cmbSub.setItems(new ItemController().getSubs_Description());
            cmbDrink.setItems(new ItemController().getDrink_Description());
            cmbPackage.setItems(new PackageController().getPackageCodes()[1]);
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }
    private void setCmbD(){
        try {
            cmbMealD.setItems(new ItemController().getMealID_Description_Portion());
            cmbPizzaD.setItems(new ItemController().getPizzaID_Description_Portion());
            cmbSubD.setItems(new ItemController().getSubs_Description());
            cmbDrinkD.setItems(new ItemController().getDrink_Description());
            cmbPackageD.setItems(new PackageController().getPackageCodes()[1]);
            cmbDriver.setItems(new EmployeeController().getDrivers());
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }

    public void searchOrder(ActionEvent actionEvent) {
        if (tabDineIn.isSelected()) {
            if (new Validation().orderID(txtSearchDT)&&!cmbOrderTypeDT.getSelectionModel().isEmpty()){
                try {
                    dineTakeTableView.getItems().clear();
                    customerID = null;
                    Order orderDetail = new OrderController().getOrderDetail(txtSearchDT.getText(), cmbOrderTypeDT.getSelectionModel().getSelectedItem());
                    if (orderDetail != null) {
                        lblCustomerNameDT.setText(orderDetail.getCustName());
                        customerID = orderDetail.getCustId();
                        lblTotal.setText(String.valueOf(orderDetail.getTotal()));
                        for (OrderDetail detail : orderDetail.getOrderedItem()
                        ) {
                            if (detail.getFoodType().equals("Package")){
                                for (int i = 0; i < detail.getQuantity(); i++) {
                                    ArrayList<PackageDetail> packageItems = new PackageController().getPackageItems(detail.getFoodID());
                                    tableItems.add(new OrderTM(detail.getFoodID(), detail.getDescription(), detail.getSellingPrice(), 1, (detail.getSellingPrice() * detail.getQuantity()), detail.getFoodType()));
                                    for (PackageDetail detail1:packageItems
                                    ) {
                                        String description;
                                        if (detail1.getFoodType().equals("Meal")){description=new ItemController().getMealDescription(detail1.getFoodCode());}else if (detail1.getFoodType().equals("Pizza")){description=new ItemController().getPizzaDescription(detail1.getFoodCode());}
                                        else if(detail1.getFoodType().equals("Sub")){description=new ItemController().getSubDescription(detail1.getFoodCode());}else {description=new ItemController().getDrinkDescription(detail1.getFoodCode());}
                                        OrderTM orderT = new OrderTM(detail.getFoodID() + "-" + detail1.getFoodCode(), description, 0, detail1.getQuantity(), 0,"X");
                                        tableItems.add(orderT);
                                    }
                                }
                                continue;
                            }
                            tableItems.add(new OrderTM(detail.getFoodID(), detail.getDescription(), detail.getSellingPrice(), detail.getQuantity(), (detail.getSellingPrice() * detail.getQuantity()), detail.getFoodType()));
                        }
                        dineTakeTableView.setItems(tableItems);
                        btnAdd.setDisable(false);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Invalid OrderID ....", ButtonType.OK);
                        alert.initOwner(mainContext.getScene().getWindow());
                        alert.show();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }else{
            if (new Validation().orderID(txtSearchD)){
                try {
                    tableViewD.getItems().clear();
                    customerID = null;
                    Order orderDetail = new OrderController().getOrderDetail(txtSearchD.getText(), "Delivery");
                    DeliveryInformation deliveryInformation = new DeliveryController().getDeliveryInformation(txtSearchD.getText());
                    if (orderDetail != null&&deliveryInformation!=null) {
                        lblCustomerNameD.setText(orderDetail.getCustName());
                        customerID = orderDetail.getCustId();
                        lblSubTotalD.setText(String.valueOf(orderDetail.getSubTotal()));
                        lblDeliveryChargesD.setText(String.valueOf(orderDetail.getDeliveryCharges()));
                        lblGrandTotalD.setText(String.valueOf(orderDetail.getTotal()));
                        cmbDriver.setValue(new Employee(deliveryInformation.getEmployeeId(),deliveryInformation.getEmployeeName()));
                        txtAddress.setText(deliveryInformation.getCustMobileNumber()+"\n"+deliveryInformation.getCustAddress());
                        for (OrderDetail detail : orderDetail.getOrderedItem()
                        ) {
                            if (detail.getFoodType().equals("Package")){
                                for (int i = 0; i < detail.getQuantity(); i++) {
                                    ArrayList<PackageDetail> packageItems = new PackageController().getPackageItems(detail.getFoodID());
                                    tableItemsD.add(new OrderTM(detail.getFoodID(), detail.getDescription(), detail.getSellingPrice(), 1, (detail.getSellingPrice() * detail.getQuantity()), detail.getFoodType()));
                                    for (PackageDetail detail1:packageItems
                                    ) {
                                        String description;
                                        if (detail1.getFoodType().equals("Meal")){description=new ItemController().getMealDescription(detail1.getFoodCode());}else if (detail1.getFoodType().equals("Pizza")){description=new ItemController().getPizzaDescription(detail1.getFoodCode());}
                                        else if(detail1.getFoodType().equals("Sub")){description=new ItemController().getSubDescription(detail1.getFoodCode());}else {description=new ItemController().getDrinkDescription(detail1.getFoodCode());}
                                        OrderTM orderT = new OrderTM(detail.getFoodID() + "-" + detail1.getFoodCode(), description, 0, detail1.getQuantity(), 0,"X");
                                        tableItemsD.add(orderT);
                                    }
                                }
                                continue;
                            }
                            tableItemsD.add(new OrderTM(detail.getFoodID(), detail.getDescription(), detail.getSellingPrice(), detail.getQuantity(), (detail.getSellingPrice() * detail.getQuantity()), detail.getFoodType()));
                        }
                        tableViewD.setItems(tableItemsD);
                        btnAdd.setDisable(false);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Invalid OrderID ....", ButtonType.OK);
                        alert.initOwner(mainContext.getScene().getWindow());
                        alert.show();
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Invalid Order ID Format .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }
        }
    }
    private boolean searchOrderTM(OrderTM tm) {
        if (tabDineIn.isSelected()){
            for (OrderTM tm1 : tableItems
            ) {
                if (tm1.getFoodCode().equals(tm.getFoodCode())&&tm1.getDescription().equals(tm.getDescription())) {
                    tm1.setQuantity(tm1.getQuantity() + tm.getQuantity());
                    tm1.setAmount(tm1.getQuantity()*tm1.getPrice());
                    dineTakeTableView.refresh();
                    return true;
                }
            }
            return false;
        }else{
            for (OrderTM tm1 : tableItemsD
            ) {
                if (tm1.getFoodCode().equals(tm.getFoodCode())&&tm1.getDescription().equals(tm.getDescription())) {
                    tm1.setQuantity(tm1.getQuantity() + tm.getQuantity());
                    tm1.setAmount(tm1.getQuantity()*tm1.getPrice());
                    tableViewD.refresh();
                    return true;
                }
            }
            return false;
        }
    }
    public void deleteItem(ActionEvent actionEvent) {
        if (tabDineIn.isSelected()){
            if (!dineTakeTableView.getSelectionModel().isEmpty()){
                OrderTM selectedItem = dineTakeTableView.getSelectionModel().getSelectedItem();
                if (selectedItem.getPrice()!=0) {
                    ObservableList<OrderTM> tmObservableList = FXCollections.observableArrayList(tableItems);
                    for (OrderTM v : tmObservableList
                    ) {
                        if (v.getFoodCode().contains(selectedItem.getFoodCode())) {
                            tableItems.remove(v);
                            deleted.add(v);
                        }
                    }
                    dineTakeTableView.setItems(tableItems);
                    calculation();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Select The Item From The Table .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }else{
            if (!tableViewD.getSelectionModel().isEmpty()){
                OrderTM selectedItem = tableViewD.getSelectionModel().getSelectedItem();
                if (selectedItem.getPrice()!=0) {
                    ObservableList<OrderTM> tmObservableList = FXCollections.observableArrayList(tableItemsD);
                    for (OrderTM v : tmObservableList
                    ) {
                        if (v.getFoodCode().contains(selectedItem.getFoodCode())) {
                            tableItemsD.remove(v);
                            deleted.add(v);
                        }
                    }
                    tableViewD.setItems(tableItemsD);
                    calculation();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please Select The Item From The Table .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }

    }

    public void addQuantity(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (tabDineIn.isSelected()){
            if (new Validation().quantityValidation(txtQuantity)&&(!cmbMeal.getSelectionModel().isEmpty()||!cmbPizza.getSelectionModel().isEmpty()||!cmbSub.getSelectionModel().isEmpty()||!cmbPackage.getSelectionModel().isEmpty()||!cmbDrink.getSelectionModel().isEmpty())||!dineTakeTableView.getSelectionModel().isEmpty()){
                OrderTM selectedItem = dineTakeTableView.getSelectionModel().getSelectedItem();
                int quantity= Integer.parseInt(txtQuantity.getText());
                if (selectedItem!=null&&selectedItem.getPrice()!=0){
                    selectedItem.setQuantity(selectedItem.getQuantity()+Integer.valueOf(txtQuantity.getText()));
                    selectedItem.setAmount(selectedItem.getQuantity()*selectedItem.getPrice());
                    dineTakeTableView.refresh();}else{
                    MealButton meal = null;
                    PizzaButton pizza=null;
                    SubButton sub=null;
                    DrinkButton drink=null;
                    if (!cmbMeal.getSelectionModel().isEmpty()){  meal= new ItemController().getMealDetailWithDiscount(cmbMeal.getSelectionModel().getSelectedItem().getMealID());}
                    if (!cmbPizza.getSelectionModel().isEmpty()){pizza = new ItemController().getPizzaDetailWithDiscount(cmbPizza.getSelectionModel().getSelectedItem().getPizzaID()); }
                    if (!cmbSub.getSelectionModel().isEmpty()){ sub= new ItemController().getSubDetailWithDiscount(cmbSub.getSelectionModel().getSelectedItem().getSandwichID());}
                    if (!cmbDrink.getSelectionModel().isEmpty()){drink= new ItemController().getDrinkDetailWithDiscount(cmbDrink.getSelectionModel().getSelectedItem().getBeverageID());}
                    if (meal!=null){
                        double d=meal.getUnitPrice();
                        if (meal.getDiscountPrice()!=0){d=meal.getDiscountPrice();}
                        OrderTM tm = new OrderTM(meal.getMealID(), meal.getDescription(), d, quantity, d * quantity, "Meal");
                        if (!searchOrderTM(tm)){
                            tableItems.add(tm);
                            added.add(tm);
                        }
                    }else if (pizza!=null){
                        double d=pizza.getUnitPrice();
                        if (pizza.getDiscountPrice()!=0){d=pizza.getDiscountPrice();}
                        OrderTM tm = new OrderTM(pizza.getPizzaID(), pizza.getDescription(),d, quantity, d * quantity, "Pizza");
                        if (!searchOrderTM(tm)){  tableItems.add(tm);   added.add(tm);}
                    }else if (sub!=null){
                        double d=sub.getUnitPrice();
                        if (sub.getDiscountPrice()!=0){d=sub.getDiscountPrice();}
                        OrderTM tm = new OrderTM(sub.getSandwichId(), sub.getDescription(), d, quantity, d * quantity, "Sub");
                        if (!searchOrderTM(tm)){  tableItems.add(tm);   added.add(tm);}
                    }else if (drink!=null){
                        double d=drink.getUnitPrice();
                        if (drink.getDiscountPrice()!=0){d=drink.getDiscountPrice();}
                        OrderTM tm = new OrderTM(drink.getBeverageID(), drink.getDescription(), d, quantity, d * quantity, "Drink");
                        if (!searchOrderTM(tm)){  tableItems.add(tm);   added.add(tm);}
                    }else{
                        try {
                            if (!cmbPackage.getSelectionModel().isEmpty()){
                                Package packageDetail = new PackageController().getPackageDetail(null,cmbPackage.getSelectionModel().getSelectedItem());
                                ArrayList<PackageDetail> packD = packageDetail.getPackageDetails();
                                for (int i = 0; i <quantity ; i++) {
                                    OrderTM orderTM = new OrderTM(packageDetail.getPackageID(), packageDetail.getName(), packageDetail.getPrice(), 1, packageDetail.getPrice() * 1,"Package");
                                    tableItems.add(orderTM);
                                    added.add(orderTM);
                                    for (PackageDetail detail:packD
                                    ) {
                                        String description;
                                        if (detail.getFoodType()=="Meal"){description=new ItemController().getMealDescription(detail.getFoodCode());}else if (detail.getFoodType()=="Pizza"){description=new ItemController().getPizzaDescription(detail.getFoodCode());}
                                        else if(detail.getFoodType()=="Sub"){description=new ItemController().getSubDescription(detail.getFoodCode());}else {description=new ItemController().getDrinkDescription(detail.getFoodCode());}
                                        OrderTM orderT = new OrderTM(packageDetail.getPackageID() + "-" + detail.getFoodCode(), description, 0, detail.getQuantity(), 0,"X");
                                        tableItems.add(orderT);
                                    }
                                }
                            }

                        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
                    }
                }
                dineTakeTableView.setItems(tableItems);
                reset(null);
                txtQuantity.clear();
                dineTakeTableView.getSelectionModel().clearSelection();
                calculation();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }else{
            if (new Validation().quantityValidation(txtQuantityD)&&(!cmbMealD.getSelectionModel().isEmpty()||!cmbPizzaD.getSelectionModel().isEmpty()||!cmbSubD.getSelectionModel().isEmpty()||!cmbPackageD.getSelectionModel().isEmpty()||!cmbDrinkD.getSelectionModel().isEmpty())||!tableViewD.getSelectionModel().isEmpty()){
                OrderTM selectedItem = tableViewD.getSelectionModel().getSelectedItem();
                int quantity= Integer.parseInt(txtQuantityD.getText());
                if (selectedItem!=null&&selectedItem.getPrice()!=0){
                    selectedItem.setQuantity(selectedItem.getQuantity()+Integer.valueOf(txtQuantityD.getText()));
                    selectedItem.setAmount(selectedItem.getQuantity()*selectedItem.getPrice());
                    tableViewD.refresh();}else{
                    MealButton meal = null;
                    PizzaButton pizza=null;
                    SubButton sub=null;
                    DrinkButton drink=null;
                    if (!cmbMealD.getSelectionModel().isEmpty()){  meal= new ItemController().getMealDetailWithDiscount(cmbMealD.getSelectionModel().getSelectedItem().getMealID());}
                    if (!cmbPizzaD.getSelectionModel().isEmpty()){pizza = new ItemController().getPizzaDetailWithDiscount(cmbPizzaD.getSelectionModel().getSelectedItem().getPizzaID()); }
                    if (!cmbSubD.getSelectionModel().isEmpty()){ sub= new ItemController().getSubDetailWithDiscount(cmbSubD.getSelectionModel().getSelectedItem().getSandwichID());}
                    if (!cmbDrinkD.getSelectionModel().isEmpty()){drink= new ItemController().getDrinkDetailWithDiscount(cmbDrinkD.getSelectionModel().getSelectedItem().getBeverageID());}
                    if (meal!=null){
                        double d=meal.getUnitPrice();
                        if (meal.getDiscountPrice()!=0){d=meal.getDiscountPrice();}
                        OrderTM tm = new OrderTM(meal.getMealID(), meal.getDescription(), d, quantity, d * quantity, "Meal");
                        if (!searchOrderTM(tm)){
                            tableItemsD.add(tm);
                            added.add(tm);
                        }
                    }else if (pizza!=null){
                        double d=pizza.getUnitPrice();
                        if (pizza.getDiscountPrice()!=0){d=pizza.getDiscountPrice();}
                        OrderTM tm = new OrderTM(pizza.getPizzaID(), pizza.getDescription(),d, quantity, d * quantity, "Pizza");
                        if (!searchOrderTM(tm)){  tableItemsD.add(tm);   added.add(tm);}
                    }else if (sub!=null){
                        double d=sub.getUnitPrice();
                        if (sub.getDiscountPrice()!=0){d=sub.getDiscountPrice();}
                        OrderTM tm = new OrderTM(sub.getSandwichId(), sub.getDescription(), d, quantity, d * quantity, "Sub");
                        if (!searchOrderTM(tm)){  tableItemsD.add(tm);   added.add(tm);}
                    }else if (drink!=null){
                        double d=drink.getUnitPrice();
                        if (drink.getDiscountPrice()!=0){d=drink.getDiscountPrice();}
                        OrderTM tm = new OrderTM(drink.getBeverageID(), drink.getDescription(), d, quantity, d * quantity, "Drink");
                        if (!searchOrderTM(tm)){  tableItemsD.add(tm);   added.add(tm);}
                    }else{
                        try {
                            if (!cmbPackageD.getSelectionModel().isEmpty()){
                                Package packageDetail = new PackageController().getPackageDetail(null,cmbPackageD.getSelectionModel().getSelectedItem());
                                ArrayList<PackageDetail> packD = packageDetail.getPackageDetails();
                                for (int i = 0; i <quantity ; i++) {
                                    OrderTM orderTM = new OrderTM(packageDetail.getPackageID(), packageDetail.getName(), packageDetail.getPrice(), 1, packageDetail.getPrice() * 1,"Package");
                                    tableItemsD.add(orderTM);
                                    added.add(orderTM);
                                    for (PackageDetail detail:packD
                                    ) {
                                        String description;
                                        if (detail.getFoodType()=="Meal"){description=new ItemController().getMealDescription(detail.getFoodCode());}else if (detail.getFoodType()=="Pizza"){description=new ItemController().getPizzaDescription(detail.getFoodCode());}
                                        else if(detail.getFoodType()=="Sub"){description=new ItemController().getSubDescription(detail.getFoodCode());}else {description=new ItemController().getDrinkDescription(detail.getFoodCode());}
                                        OrderTM orderT = new OrderTM(packageDetail.getPackageID() + "-" + detail.getFoodCode(), description, 0, detail.getQuantity(), 0,"X");
                                        tableItemsD.add(orderT);
                                    }
                                }
                            }
                        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
                    }
                }
                tableViewD.setItems(tableItemsD);
                reset(null);
                txtQuantityD.clear();
                tableViewD.getSelectionModel().clearSelection();
                calculation();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }

    }

    public void cmbOnHide(Event event) {
        if (tabDineIn.isSelected()){
            dineTakeTableView.getSelectionModel().clearSelection();
            if (!(cmbMeal.getSelectionModel().isEmpty()&&cmbPizza.getSelectionModel().isEmpty()&&cmbSub.getSelectionModel().isEmpty()&&cmbDrink.getSelectionModel().isEmpty()&&cmbPackage.getSelectionModel().isEmpty())){
                cmbMeal.setDisable(true);
                cmbPizza.setDisable(true);
                cmbSub.setDisable(true);
                cmbDrink.setDisable(true);
                cmbPackage.setDisable(true);
            }
        }else{
            tableViewD.getSelectionModel().clearSelection();
            if (!(cmbMealD.getSelectionModel().isEmpty()&&cmbPizzaD.getSelectionModel().isEmpty()&&cmbSubD.getSelectionModel().isEmpty()&&cmbDrinkD.getSelectionModel().isEmpty()&&cmbPackageD.getSelectionModel().isEmpty())){
                cmbMealD.setDisable(true);
                cmbPizzaD.setDisable(true);
                cmbSubD.setDisable(true);
                cmbDrinkD.setDisable(true);
                cmbPackageD.setDisable(true);
            }
        }

    }

    public void reset(ActionEvent actionEvent) {
        if (tabDineIn.isSelected()){
            cmbMeal.setDisable(false);
            cmbPizza.setDisable(false);
            cmbSub.setDisable(false);
            cmbDrink.setDisable(false);
            cmbPackage.setDisable(false);
            cmbMeal.getSelectionModel().clearSelection();
            cmbPizza.getSelectionModel().clearSelection();
            cmbSub.getSelectionModel().clearSelection();
            cmbDrink.getSelectionModel().clearSelection();
            cmbPackage.getSelectionModel().clearSelection();
        }else {
            cmbMealD.setDisable(false);
            cmbPizzaD.setDisable(false);
            cmbSubD.setDisable(false);
            cmbDrinkD.setDisable(false);
            cmbPackageD.setDisable(false);
            cmbMealD.getSelectionModel().clearSelection();
            cmbPizzaD.getSelectionModel().clearSelection();
            cmbSubD.getSelectionModel().clearSelection();
            cmbDrinkD.getSelectionModel().clearSelection();
            cmbPackageD.getSelectionModel().clearSelection();
        }

    }
    private void calculation(){
        double subTot=0;
        double deliveryCharges=0;
        double grandTotal=0;
        if (tabDineIn.isSelected()){
            for (OrderTM r:tableItems
            ) {
                subTot+=r.getAmount();
            }
            lblTotal.setText(String.valueOf(subTot));
        }else{
            for (OrderTM r:tableItemsD
            ) {
                subTot+=r.getAmount();
            }
            lblSubTotalD.setText(String.valueOf(subTot));
           deliveryCharges=subTot*5/100;
           grandTotal=subTot+deliveryCharges;
           lblDeliveryChargesD.setText(String.valueOf(deliveryCharges));
           lblGrandTotalD.setText(String.valueOf(grandTotal));
        }
    }
    public void cancelOrder(ActionEvent actionEvent) {

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO);
        alert1.initOwner(mainContext.getScene().getWindow());
        alert1.showAndWait().ifPresent(re->{
           if (re==ButtonType.YES){
               try{
                   boolean b;
                   if (tabDineIn.isSelected()){
                       if(new Validation().orderID(txtSearchDT)){
                           b = new OrderController().deleteOrder(txtSearchDT.getText());
                       }else{
                           b=false;
                       }

                   }else{
                       if (new Validation().orderID(txtSearchD)){
                           b = new OrderController().deleteOrder(txtSearchD.getText());
                       }else{
                           b=false;
                       }
                   }
                   Alert alert;
                   if (b) {
                       alert = new Alert(Alert.AlertType.CONFIRMATION, "Order Cancelled Successfully !", ButtonType.OK);
                       clearDelivery();
                       clearDineTakeAway();
                   }else {
                       alert=new Alert(Alert.AlertType.CONFIRMATION, "Something Went Wrong try Again ", ButtonType.CLOSE);
                   }
                   alert.initOwner(mainContext.getScene().getWindow());
                   alert.show();
               }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

           }else{return;}
       });
    }
    private  boolean setupPackageOrderItem(ObservableList<OrderTM> od,OrderDetail oderItem){
        for (OrderTM detail:od
        ) {
            if (detail.getFoodCode().equals(oderItem.getFoodID())&&detail.getDescription().equals(oderItem.getDescription())){
                detail.setQuantity(detail.getQuantity()+oderItem.getQuantity());
                dineTakeTableView.refresh();
                return true;
            }
        }
        return false;
    }
    private boolean isIN(OrderDetail od, ObservableList<OrderTM> tb){
        for (OrderTM tm:tb
             ) {
            if (tm.getFoodCode().equals(od.getFoodID())&&tm.getDescription().equals(od.getDescription())){
                return true;
            }
        }
        return false;
    }

    public void modifyOrder(ActionEvent actionEvent) {
        if (tabDineIn.isSelected()){
            if (new Validation().orderID(txtSearchDT)&&!cmbOrderTypeDT.getSelectionModel().isEmpty()&&!tableItems.isEmpty()){
                tableItems.removeAll(added);
                boolean b = true;
                boolean b1 = true;
                boolean b2 = true;
                ArrayList<OrderDetail> orderItem=new ArrayList<>();
                try {
                    ArrayList<OrderDetail> pd=new ArrayList<>();
                    for (OrderTM tm:added
                    ) {
                        OrderDetail detail = new OrderDetail(tm.getFoodCode(), tm.getDescription(), tm.getPrice(), tm.getQuantity(), tm.getFoodType());
                        if (tm.getFoodType().equals("Package")){
                            if (isIN(detail,tableItems)){
                                setupPackageOrderItem(tableItems,detail);
                            }else{
                                pd.add(detail);
                            }
                        }else {
                            pd.add(detail);
                        }
                    }
                    if (!deleted.isEmpty()){
                        b=new OrderController().deleteItem(txtSearchDT.getText(),deleted);}

                    for (OrderTM tm:tableItems
                    ) {
                        OrderDetail detail = new OrderDetail(tm.getFoodCode(), tm.getDescription(), tm.getPrice(), tm.getQuantity(), tm.getFoodType());
                        orderItem.add(detail);
                    }
                    if (!pd.isEmpty()){
                        b1=new OrderController().saveOrderDetail(pd,txtSearchDT.getText());}
                    b2 = new OrderController().updateOrder(new Order(txtSearchDT.getText(), null, null, null, cmbOrderTypeDT.getSelectionModel().getSelectedItem(), Double.valueOf(lblTotal.getText()), 0,  Double.valueOf(lblTotal.getText()), "NonPaid", orderItem));
                }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
                Alert alert;
                if (b&&b1&&b2){
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Order Modified ....", ButtonType.OK);
                }else {
                    alert = new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK);
                }
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();}
        }else{
            if (new Validation().orderID(txtSearchD)&&!cmbDriver.getSelectionModel().isEmpty()){
                tableItemsD.removeAll(added);
                boolean b = true;
                boolean b1 = true;
                boolean b2 = true;
                boolean b3=true;
                ArrayList<OrderDetail> orderItem=new ArrayList<>();
                try {
                    ArrayList<OrderDetail> pd=new ArrayList<>();
                    for (OrderTM tm:added
                    ) {
                        OrderDetail detail = new OrderDetail(tm.getFoodCode(), tm.getDescription(), tm.getPrice(), tm.getQuantity(), tm.getFoodType());
                        if (tm.getFoodType().equals("Package")){
                            if (isIN(detail,tableItemsD)){
                                setupPackageOrderItem(tableItemsD,detail);
                            }else{
                                pd.add(detail);
                            }
                        }else {
                            pd.add(detail);
                        }
                    }
                    if (!deleted.isEmpty()){
                        b=new OrderController().deleteItem(txtSearchD.getText(),deleted);}

                    for (OrderTM tm:tableItemsD
                    ) {
                        OrderDetail detail = new OrderDetail(tm.getFoodCode(), tm.getDescription(), tm.getPrice(), tm.getQuantity(), tm.getFoodType());
                        orderItem.add(detail);
                    }
                    if (!pd.isEmpty()){
                        b1=new OrderController().saveOrderDetail(pd,txtSearchD.getText());}
                    b2 = new OrderController().updateOrder(new Order(txtSearchD.getText(), null, null, null, "Delivery", Double.valueOf(lblSubTotalD.getText()), Double.valueOf(lblDeliveryChargesD.getText()), Double.valueOf(lblGrandTotalD.getText()), "NonPaid", orderItem));
                    b3=new DeliveryController().updateDriver(txtSearchD.getText(),cmbDriver.getSelectionModel().getSelectedItem().getEmployeeID());
                }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
                Alert alert;
                if (b&&b1&&b2&&b3){
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Order Modified ....", ButtonType.OK);
                }else {
                    alert = new Alert(Alert.AlertType.ERROR, "Try Again", ButtonType.OK);
                }
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Check Fields Again .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }
        }
        added.clear();
        deleted.clear();
    }

    public void doPayment(ActionEvent actionEvent) {
        boolean b = false;
        if (tabDineIn.isSelected()){
            if (new Validation().priceValidation(txtCashDT)){
                double totalD= Double.parseDouble(lblTotal.getText());
                double paidAmountD= Double.parseDouble(txtCashDT.getText());
                double balanceD=paidAmountD-totalD;
                lblBalanceDT.setText(String.valueOf(balanceD));
                try {
                    PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO transaction VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1,txtSearchDT.getText());
                    preparedStatement.setString(2,customerID);
                    preparedStatement.setString(3,CashierMainFormController.cashierID);
                    preparedStatement.setDouble(4,totalD);
                    preparedStatement.setDouble(5,paidAmountD);
                    preparedStatement.setDouble(6,balanceD);
                    b=preparedStatement.executeUpdate()>0;
                    if(b){b=new OrderController().orderPaid(txtSearchDT.getText());}

                }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Invalid Input .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }

        }else{
            if (new Validation().priceValidation(txtCashD)){
                double totalD= Double.parseDouble(lblGrandTotalD.getText());
                double paidAmountD= Double.parseDouble(txtCashD.getText());
                double balanceD=paidAmountD-totalD;
                lblBalanceD.setText(String.valueOf(balanceD));
                try {
                    PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO transaction VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1,txtSearchD.getText());
                    preparedStatement.setString(2,customerID);
                    preparedStatement.setString(3,CashierMainFormController.cashierID);
                    preparedStatement.setDouble(4,totalD);
                    preparedStatement.setDouble(5,paidAmountD);
                    preparedStatement.setDouble(6,balanceD);
                    b=preparedStatement.executeUpdate()>0;
                    if(b){b=new OrderController().orderPaid(txtSearchD.getText());}
                }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Invalid Input .....", ButtonType.CLOSE);
                alert.initOwner(mainContext.getScene().getWindow());
                alert.show();
            }
        }
        Alert alert;
        if (b){
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Paid  ....", ButtonType.OK);
        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(mainContext.getScene().getWindow());
        alert.show();
    }


    private void clearDineTakeAway(){
        cmbOrderTypeDT.getSelectionModel().clearSelection();
        txtSearchDT.clear();
        lblCustomerNameDT.setText(null);
        dineTakeTableView.getItems().clear();
        txtCashDT.clear();
        txtQuantity.clear();
        lblTotal.setText(null);
        lblBalanceDT.setText(null);
        cmbMeal.getSelectionModel().clearSelection();
        cmbPizza.getSelectionModel().clearSelection();
        cmbSub.getSelectionModel().clearSelection();
        cmbPackage.getSelectionModel().clearSelection();
        cmbDrink.getSelectionModel().clearSelection();
        txtSearchDT.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");
        txtCashDT.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");
        txtQuantity.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");

    }
    private void clearDelivery(){
        cmbDriver.getSelectionModel().select(null);
        txtSearchD.clear();
        lblCustomerNameD.setText(null);
        tableViewD.getItems().clear();
        txtCashD.clear();
        txtQuantityD.clear();
        cmbMealD.getSelectionModel().clearSelection();
        cmbPizzaD.getSelectionModel().clearSelection();
        cmbSubD.getSelectionModel().clearSelection();
        cmbPackageD.getSelectionModel().clearSelection();
        cmbDrinkD.getSelectionModel().clearSelection();
        lblSubTotalD.setText(null);
        lblDeliveryChargesD.setText(null);
        lblGrandTotalD.setText(null);
        lblBalanceD.setText(null);
        txtAddress.clear();
        txtSearchD.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");
        txtCashD.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");
        txtQuantityD.setStyle("-fx-border-radius :8;-fx-background-radius:8;-fx-border-width:3;-fx-border-color: #2c3e50");
    }
    public void dineTakeTab(Event event) {
        clearDineTakeAway();
        added.clear();
        deleted.clear();
    }

    public void deliveryTab(Event event) {
        clearDelivery();
        added.clear();
        deleted.clear();
    }
}
