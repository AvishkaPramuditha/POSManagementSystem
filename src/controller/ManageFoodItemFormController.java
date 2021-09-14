package controller;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.Drink;
import model.Meal;
import model.Pizza;
import model.SubBurgersAndOthers;

import java.io.*;
import java.sql.SQLException;

public class ManageFoodItemFormController {


    public ComboBox<Meal> mealCombo;
    public ComboBox<Pizza> comboPizza;
    public ComboBox<SubBurgersAndOthers> combSub;
    public ComboBox<Drink> comboDrink;
    public TextField txtMealUnitPrice;
    public TextField txtMealPortion;
    public TextField txtMealDescription;
    public TextField txtMealID;
    public TextField txtPizzaID;
    public TextField txtPizzaDescription;
    public TextField txtPizzaSize;
    public TextField txtPizzaUnitPrice;
    public TextField txtPizzaQuantityOnHand;
    public TextField txtSandwichID;
    public TextField txtSandwichDescription;
    public TextField txtSandwichUnitPrice;
    public TextField txtSandwichQuantityOnHand;
    public TextField txtBeverageID;
    public TextField txtDrinkDescription;
    public TextField txtDrinkUnitPrice;
    public JFXToggleButton toggleDrinkAvailable;
    public JFXToggleButton toggleMealAvailable;
    public AnchorPane context;
    public ImageView mealImageView;
    public ImageView pizzaImageView;
    public ImageView subImageView;
    public ImageView drinkImageView;
    private File mealImage;
    private File pizzaImage;
    private File subImage;
    private File drinkImage;

    public void initialize(){
      refresh(null);
       setMealTextFields();
       setPizzaTextFields();
       setSubBurgersTextFields();
       setDrinkTextFields();

    }
    private void setMealTextFields(){
        mealCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {if (newValue!=null){
                Meal meal = new ItemController().getMealDetails(newValue.getMealID());
                    txtMealID.setText(meal.getMealID());
                    txtMealDescription.setText(meal.getDescription());
                    txtMealPortion.setText(meal.getPortion());
                    txtMealUnitPrice.setText(String.valueOf(meal.getUnitPrice()));
                    toggleMealAvailable.setSelected(meal.isAvailable());
                if(meal.getMealImage()!=null){
                    Image image=new Image( meal.getMealImage().toURI().toString(),220,190,true,true);
                    mealImage=meal.getMealImage();
                    mealImageView.setImage(image);
                }
            }
            } catch (SQLException | ClassNotFoundException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    private void setPizzaTextFields() {
        comboPizza.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {if (newValue!=null){
                Pizza pizza = new ItemController().getPizzaDetails(newValue.getPizzaID());
                txtPizzaID.setText(pizza.getPizzaID());
                txtPizzaDescription.setText(pizza.getDescription());
                txtPizzaSize.setText(pizza.getSize());
                txtPizzaUnitPrice.setText(String.valueOf(pizza.getUnitPrize()));
                txtPizzaQuantityOnHand.setText(String.valueOf(pizza.getQuantityOnHand()));
                if(pizza.getPizzaImage()!=null){
                    Image image=new Image( pizza.getPizzaImage().toURI().toString(),220,190,true,true);
                    pizzaImage=pizza.getPizzaImage();
                    pizzaImageView.setImage(image);
                }
            }

            } catch (SQLException | ClassNotFoundException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }
        private void setSubBurgersTextFields() {
            combSub.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue!=null){
                    SubBurgersAndOthers sandwich = new ItemController().getSubBurgersAndOthersDetails(newValue.getSandwichID());
                    txtSandwichID.setText(sandwich.getSandwichID());
                    txtSandwichDescription.setText(sandwich.getDescription());
                    txtSandwichUnitPrice.setText(String.valueOf(sandwich.getUnitPrice()));
                    txtSandwichQuantityOnHand.setText(String.valueOf(sandwich.getQuantityOnHand()));
                        if(sandwich.getSubImage()!=null){
                            Image image=new Image(sandwich.getSubImage().toURI().toString(),220,190,true,true);
                            subImage=sandwich.getSubImage();
                            subImageView.setImage(image);
                        }
                    }
                } catch (SQLException | ClassNotFoundException | IOException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
            private void setDrinkTextFields() {
                comboDrink.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        if (newValue!=null) {
                            Drink drink = new ItemController().getDrinkDetails(newValue.getBeverage());
                            txtDrinkUnitPrice.setText(String.valueOf(drink.getUnitPrice()));
                            txtBeverageID.setText(drink.getBeverage());
                            txtDrinkDescription.setText(drink.getDescription());
                            toggleDrinkAvailable.setSelected(drink.isAvailable());
                            if(drink.getDrinkImage()!=null){
                                Image image=new Image( drink.getDrinkImage().toURI().toString(),220,190,true,true);
                                drinkImage=drink.getDrinkImage();
                                drinkImageView.setImage(image);
                            }
                        }
                    } catch (SQLException | ClassNotFoundException | IOException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }

    public void subClearAll(ActionEvent actionEvent) {
        subImageView.setImage(null);
        subImage=null;
        txtSandwichUnitPrice.clear();
        txtSandwichDescription.clear();
        txtSandwichID.clear();
        txtSandwichQuantityOnHand.clear();
        combSub.getSelectionModel().clearSelection();
    }

    public void pizzaClearAll(ActionEvent actionEvent) {
       pizzaImageView.setImage(null);
       pizzaImage=null;
        txtPizzaID.clear();
        txtPizzaUnitPrice.clear();
        txtPizzaSize.clear();
        txtPizzaDescription.clear();
        txtPizzaQuantityOnHand.clear();
        comboPizza.getSelectionModel().clearSelection();
    }

    public void mealClearAll(ActionEvent actionEvent) {
        mealImage=null;
        mealImageView.setImage(null);
        txtMealID.clear();
        txtMealDescription.clear();
        txtMealPortion.clear();
        txtMealUnitPrice.clear();
        toggleMealAvailable.setSelected(false);
        mealCombo.getSelectionModel().clearSelection();

    }

    public void drinkClearAll(ActionEvent actionEvent) {
        drinkImageView.setImage(null );
        drinkImage=null;
        txtDrinkDescription.clear();
        txtDrinkUnitPrice.clear();
        txtBeverageID.clear();
        toggleDrinkAvailable.setSelected(false);
        comboDrink.getSelectionModel().clearSelection();
    }

    public void clearPageDrink(Event event) {
        drinkClearAll(null);
    }

    public void clearPageSub(Event event) {
        subClearAll(null);
    }

    public void clearPagePizza(Event event) {
        pizzaClearAll(null);
    }

    public void clearPageMeal(Event event) {
        mealClearAll(null);
    }

    public void addMeal(ActionEvent actionEvent) {
        try {

            if (new ItemController().addMeal(new Meal(txtMealID.getText(),txtMealDescription.getText(),txtMealPortion.getText(),Double.valueOf(txtMealUnitPrice.getText()),toggleMealAvailable.isSelected(),mealImage))){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Saved successfully.....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                mealClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }

    }

    public void addPizza(ActionEvent actionEvent) {
        try {
            if (new ItemController().addPizza(new Pizza(txtPizzaID.getText(), txtPizzaDescription.getText(), txtPizzaSize.getText(), Double.valueOf(txtPizzaUnitPrice.getText()), Integer.valueOf(txtPizzaQuantityOnHand.getText()),pizzaImage))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                pizzaClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void addSub(ActionEvent actionEvent) {
        try {
            if (new ItemController().addSub(new SubBurgersAndOthers(txtSandwichID.getText(), txtSandwichDescription.getText(), Double.valueOf(txtSandwichUnitPrice.getText()),Integer.valueOf(txtSandwichQuantityOnHand.getText()),subImage))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                subClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void addDrink(ActionEvent actionEvent) {
        try {
            if (new ItemController().addDrink(new Drink(txtBeverageID.getText(), txtDrinkDescription.getText(), Double.valueOf(txtDrinkUnitPrice.getText()),toggleDrinkAvailable.isSelected(),drinkImage))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                drinkClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void openAndSelectMealImage(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        mealImage=fileChooser.showOpenDialog(context.getScene().getWindow());
        if(mealImage!=null){
            Image image=new Image(mealImage.toURI().toString(),220,190,true,true);
            mealImageView.setImage(image);
        }

    }

    public void openAndSelectPizzaImage(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        pizzaImage=fileChooser.showOpenDialog(context.getScene().getWindow());
        if( pizzaImage!=null){
            Image image=new Image( pizzaImage.toURI().toString(),220,190,true,true);
            pizzaImageView.setImage(image);
        }
    }

    public void openAndSelectSubImage(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        subImage=fileChooser.showOpenDialog(context.getScene().getWindow());
        if( subImage!=null){
            Image image=new Image( subImage.toURI().toString(),220,190,true,true);
            subImageView.setImage(image);
        }
    }

    public void openAndSelectDrinkImage(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        drinkImage=fileChooser.showOpenDialog(context.getScene().getWindow());
        if(drinkImage!=null){
            Image image=new Image(drinkImage.toURI().toString(),220,190,true,true);
            drinkImageView.setImage(image);
        }
    }

    public void updateMeal(ActionEvent actionEvent) {
        try {
            if (new ItemController().updateMeal(new Meal(txtMealID.getText(),txtMealDescription.getText(),txtMealPortion.getText(),Double.valueOf(txtMealUnitPrice.getText()),toggleMealAvailable.isSelected(),mealImage),mealCombo.getSelectionModel().getSelectedItem().getMealID())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Update successfully.....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                mealClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();}
    }


    public void updatePizza(ActionEvent actionEvent) {
        try {
            if (new ItemController().updatePizza(new Pizza(txtPizzaID.getText(), txtPizzaDescription.getText(), txtPizzaSize.getText(), Double.valueOf(txtPizzaUnitPrice.getText()), Integer.valueOf(txtPizzaQuantityOnHand.getText()),pizzaImage),comboPizza.getSelectionModel().getSelectedItem().getPizzaID())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                pizzaClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void updateSub(ActionEvent actionEvent) {
        try {
            if (new ItemController().updateSub(new SubBurgersAndOthers(txtSandwichID.getText(), txtSandwichDescription.getText(), Double.valueOf(txtSandwichUnitPrice.getText()),Integer.valueOf(txtSandwichQuantityOnHand.getText()),subImage),combSub.getSelectionModel().getSelectedItem().getSandwichID())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                subClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void updateDrink(ActionEvent actionEvent) {
        try {
            if (new ItemController().updateDrink(new Drink(txtBeverageID.getText(), txtDrinkDescription.getText(), Double.valueOf(txtDrinkUnitPrice.getText()),toggleDrinkAvailable.isSelected(),drinkImage),comboDrink.getSelectionModel().getSelectedItem().getBeverageID())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                drinkClearAll(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (SQLException | ClassNotFoundException | FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }
    }

    public void deleteMeal(ActionEvent actionEvent) {
        try {
            if (new ItemController().deleteMeal(mealCombo.getSelectionModel().getSelectedItem().getMealID())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                mealClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}

    }

    public void deleteSub(ActionEvent actionEvent) {
        try {
            if (new ItemController().deleteSub(combSub.getSelectionModel().getSelectedItem().getSandwichID())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                subClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void deletePizza(ActionEvent actionEvent) {
        try {
            if (new ItemController().deletePizza(comboPizza.getSelectionModel().getSelectedItem().getPizzaID())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                pizzaClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void deleteDrink(ActionEvent actionEvent) {
        try {
            if (new ItemController().deleteDrink(comboDrink.getSelectionModel().getSelectedItem().getBeverageID())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update successfully .....", ButtonType.CLOSE);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                drinkClearAll(null);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, " Try Again ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            }
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void refresh(Event event) {
        try {
            mealCombo.setItems(new ItemController().getMealID_Description_Portion());
            comboPizza.setItems(new ItemController().getPizzaID_Description_Portion());
            combSub.setItems(new ItemController().getSubs_Description());
            comboDrink.setItems(new ItemController().getDrink_Description());
        }catch (SQLException | ClassNotFoundException e){e.printStackTrace();}

    }
}
