package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.*;
import model.Package;
import view.tm.PackageTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagePackageFormController {
    public Pane context;
    public TextField txtPackageCode;
    public TextField txtPackageName;
    public ComboBox<Item> cmbFoodItem;
    public TextField txtQuantity;
    public TableView<PackageTM> tblView;
    public TextField txtPackagePrice;
    public ComboBox<String> cmbPackageName;
    public ComboBox<String> cmbPackageCode;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    private ObservableList<PackageTM> packageTMS= FXCollections.observableArrayList();

    public void initialize(){
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        setItemCombo();
        loadPackageCombo();
        tblView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("foodCode"));
        tblView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("quantity"));

        cmbPackageCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try{
               if (newValue!=null){
                   Package package1= new PackageController().getPackageDetail(newValue, null);
                   setDataToFields(package1);
                   txtPackageCode.setDisable(true);
                   btnSave.setDisable(true);
                   btnDelete.setDisable(false);btnUpdate.setDisable(false);
               }else {btnDelete.setDisable(true);btnUpdate.setDisable(true);}
            }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        });
        cmbPackageName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if (newValue!=null){
                    Package package1= new PackageController().getPackageDetail(null, newValue);
                    setDataToFields(package1);
                    txtPackageCode.setDisable(true);
                    btnDelete.setDisable(false);btnUpdate.setDisable(false);
                    btnSave.setDisable(true);}else {btnDelete.setDisable(true);btnUpdate.setDisable(true);}
            }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
        });
    }
    public void addItem(ActionEvent actionEvent) {
        String foodType=null;
        Item item=cmbFoodItem.getSelectionModel().getSelectedItem();
        if (item instanceof Meal){foodType="Meal";}else if (item instanceof Pizza){foodType="Pizza";}else if (item instanceof SubBurgersAndOthers){foodType="Sub";}else{foodType="Drink";}
        if (!searchTM(cmbFoodItem.getSelectionModel().getSelectedItem().getID(),Integer.parseInt(txtQuantity.getText()))) {
            packageTMS.add(new PackageTM(item.getID(),item.getDescription(), Integer.parseInt(txtQuantity.getText()),foodType));
            tblView.setItems(packageTMS);
            txtQuantity.clear();
            cmbFoodItem.getSelectionModel().clearSelection();
        }
    }

    public void deleteItem(ActionEvent actionEvent) {
       tblView.getItems().remove(tblView.getSelectionModel().getSelectedItem());

    }

    public void savePackage(ActionEvent actionEvent) {
        ArrayList<PackageDetail> list=new ArrayList<>();
        for (PackageTM tm:packageTMS
             ) {
         list.add(new PackageDetail(tm.getFoodType(),tm.getFoodCode(),tm.getQuantity()));
        }
        Package aPackage = new Package(txtPackageCode.getText(), txtPackageName.getText(), Double.valueOf(txtPackagePrice.getText()), list);
        boolean b = new PackageController().addPackage(aPackage);
        Alert alert;
        if (b){
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved  Successfully", ButtonType.OK);
            clear(null);
            loadPackageCombo();

        }else {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
        }
        alert.initOwner(context.getScene().getWindow());
        alert.show();
    }

    public void clear(ActionEvent actionEvent) {
        txtPackageCode.clear();
        txtQuantity.clear();
        txtPackagePrice.clear();
        txtPackageName.clear();
        cmbFoodItem.getSelectionModel().clearSelection();
        cmbPackageName.getSelectionModel().clearSelection();
        cmbPackageCode.getSelectionModel().clearSelection();
        tblView.getItems().clear();
        tblView.refresh();
        btnSave.setDisable(false);
        txtPackageCode.setDisable(false);
    }

    public void update(ActionEvent actionEvent) {
         boolean b;
        try {
            b = new PackageController().deletePackage(txtPackageCode.getText(),null);
            ArrayList<PackageDetail> list=new ArrayList<>();
            for (PackageTM tm:packageTMS
            ) {
                list.add(new PackageDetail(tm.getFoodType(),tm.getFoodCode(),tm.getQuantity()));
            }
            Package aPackage = new Package(txtPackageCode.getText(), txtPackageName.getText(), Double.valueOf(txtPackagePrice.getText()), list);
            b = new PackageController().addPackage(aPackage);
            Alert alert;
            if (b){
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Update  Successfully", ButtonType.OK);
                clear(null);
                loadPackageCombo();

            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePackage(ActionEvent actionEvent) {
        try {
            boolean b = new PackageController().deletePackage(cmbPackageCode.getSelectionModel().getSelectedItem(), cmbPackageName.getSelectionModel().getSelectedItem());
            Alert alert;
            if (b){
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete  Successfully", ButtonType.OK);
                clear(null);
                loadPackageCombo();

            }else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    private void setItemCombo(){
        try{
            cmbFoodItem.getItems().addAll(new ItemController().getMealID_Description_Portion());
            cmbFoodItem.getItems().addAll(new ItemController().getPizzaID_Description_Portion());
            cmbFoodItem.getItems().addAll(new ItemController().getSubs_Description());
            cmbFoodItem.getItems().addAll(new ItemController().getDrink_Description());
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }
    private  boolean searchTM(String foodCode ,int quantity){
        for (PackageTM tm:packageTMS
             ) {
            if (tm.getFoodCode().equals(foodCode)){
              tm.setQuantity(tm.getQuantity()+quantity);
              txtQuantity.clear();
              cmbFoodItem.getSelectionModel().clearSelection();
              tblView.refresh();
              return true;
            }
        }
        return false;
    }

    private void loadPackageCombo(){
        try {
            cmbPackageCode.setItems(new PackageController().getPackageCodes()[0]);
            cmbPackageName.setItems(new PackageController().getPackageCodes()[1]);
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }
     private void setDataToFields(Package data) throws SQLException, ClassNotFoundException {
        txtPackageCode.setText(data.getPackageID());
        txtPackageName.setText(data.getName());
        txtPackagePrice.setText(String.valueOf(data.getPrice()));
         ObservableList<PackageTM> tms = FXCollections.observableArrayList();
         for (PackageDetail detail:data.getPackageDetails()
              ) {
             String description;
             if (detail.getFoodType()=="Meal"){description=new ItemController().getMealDescription(detail.getFoodCode());}else if (detail.getFoodType()=="Pizza"){description=new ItemController().getPizzaDescription(detail.getFoodCode());
             }else if (detail.getFoodType()=="Sub"){description=new ItemController().getSubDescription(detail.getFoodCode());}else{description=new ItemController().getDrinkDescription(detail.getFoodCode());}
             tms.add(new PackageTM(detail.getFoodCode(),description,detail.getQuantity(),detail.getFoodType()));
         }
              tblView.setItems((packageTMS=tms));

     }

}
