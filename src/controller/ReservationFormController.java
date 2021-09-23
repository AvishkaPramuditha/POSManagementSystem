package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Customer;
import model.Reservation;
import view.tm.ReservationTM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationFormController {

    public JFXDatePicker viewDatePicker;
    public TableView<ReservationTM> tblView;
    public TextField addTabCustomerMobile;
    public TextField addTabCustomerName;
    public JFXDatePicker addTabDatePicker;
    public JFXTimePicker addTabTimePicker;
    public TextField addTabParticipants;
    public Text lblReservation;
    public AnchorPane context;
    public JFXButton btnCustomerAdd;
    public JFXButton btnAddReservation;
    public TextField txtManageCustomerMobile;
    public TextField txtManageCustomerName;
    public JFXDatePicker manageDatePicker;
    public JFXTimePicker manageTimePicker;
    public TextField txtManageParticipant;
    public TableView<ReservationTM> manageTblView;
    public TableColumn viewRID;
    public TableColumn viewCMobile;
    public TableColumn viewCName;
    public TableColumn viewTime;
    public TableColumn viewParticipant;
    public TableColumn manageRID;
    public TableColumn manageRDate;
    public TableColumn manageRTime;
    public TableColumn manageRParticipants;
    private String customerID;

    public void initialize() {
        setReservationID();
        btnCustomerAdd.setDisable(true);
        btnAddReservation.setDisable(true);
        tblView.getColumns().add(viewRID);
        tblView.getColumns().add(viewCName);
        tblView.getColumns().add(viewCMobile);
        tblView.getColumns().add(viewTime);
        tblView.getColumns().add(viewParticipant);
        viewRID.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        viewCName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        viewCMobile.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));
        viewTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        viewParticipant.setCellValueFactory(new PropertyValueFactory<>("participant"));
        manageRID.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        manageRDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        manageRTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        manageRParticipants.setCellValueFactory(new PropertyValueFactory<>("participant"));

        manageTblView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                manageDatePicker.setValue(LocalDate.parse(newValue.getDate()));
                manageTimePicker.setValue(LocalTime.parse(newValue.getTime()));
                txtManageParticipant.setText(String.valueOf(newValue.getParticipant()));
            }

        });
    }

    public void addReservation(ActionEvent actionEvent) {
        try {Alert alert;
            boolean b = new ReservationController().addReservation(new Reservation(lblReservation.getText(), customerID,addTabDatePicker.getValue().toString(),addTabTimePicker.getValue().toString(), Integer.valueOf(addTabParticipants.getText())));
            if (b) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully", ButtonType.OK);
                btnAddReservation.setDisable(false);

            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(context.getScene().getWindow());
            alert.show();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void cancel(ActionEvent actionEvent) {
        addTabDatePicker.getEditor().clear();
        addTabTimePicker.getEditor().clear();
        addTabCustomerName.clear();
        addTabCustomerMobile.clear();
        addTabParticipants.clear();
        btnAddReservation.setDisable(true);

    }

    private void setReservationID() {
        try {
            lblReservation.setText(new ReservationController().ReservationID());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addCustomer(ActionEvent actionEvent) {
        try {
            boolean b = new CustomerController().addCustomer(new Customer().setCustomerDetail(customerID, addTabCustomerName.getText(), addTabCustomerMobile.getText()));
            Alert alert;
            if (b) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully", ButtonType.OK);
                btnAddReservation.setDisable(false);

            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchCustomer(ActionEvent actionEvent) {
        try {
            Customer customerDetail = new CustomerController().getCustomerDetail(addTabCustomerMobile.getText());
            Alert alert;
            if (customerDetail == null) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "NO Customer Please ADD.. ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
                btnCustomerAdd.setDisable(false);
                customerID = new CustomerController().getCustomerID();
                addTabCustomerName.clear();
            } else {
                addTabCustomerName.setText(customerDetail.getCustomerName());
                customerID = customerDetail.getCustID();
                btnCustomerAdd.setDisable(true);
                btnAddReservation.setDisable(false);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void getReservations(Event event) {
        try {
            tblView.setItems(new ReservationController().getReservationOnDate(Date.valueOf(viewDatePicker.getValue())));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearView(Event event) {
        tblView.getColumns().clear();
        viewDatePicker.getEditor().clear();
    }

    public void clearReservation(Event event) {
        addTabParticipants.clear();
        addTabCustomerMobile.clear();
        addTabCustomerName.clear();
        addTabTimePicker.getEditor().clear();
        addTabDatePicker.getEditor().clear();
    }

    public void clearManageReservation(Event event) {
        txtManageParticipant.clear();
        txtManageCustomerName.clear();
        txtManageCustomerMobile.clear();
        manageDatePicker.getEditor().clear();
        manageTimePicker.getEditor().clear();
        manageTblView.getItems().clear();
    }

    public void manageUpdate(ActionEvent actionEvent) {
          try {
              String date= manageDatePicker.getValue().toString();
              String time= manageTimePicker.getValue().toString();
              boolean b = new ReservationController().updateReservation(new Reservation(manageTblView.getSelectionModel().getSelectedItem().getReservationID(), date,time, Integer.valueOf(txtManageParticipant.getText())));
              Alert alert;
              if (b ){
                  alert = new Alert(Alert.AlertType.CONFIRMATION, "Update Successfully", ButtonType.OK);
                  manageTimePicker.getEditor().clear();
                  manageDatePicker.getEditor().clear();
                  txtManageParticipant.clear();
                  manageTblView.setItems(new ReservationController().getReservationOnCustomer(customerID));

              } else {
                  alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
              }
              alert.initOwner(context.getScene().getWindow());
              alert.show();
          }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void Managedelete(ActionEvent actionEvent) {
        try {
            boolean b = new ReservationController().deleteReservation(manageTblView.getSelectionModel().getSelectedItem().getReservationID());
            Alert alert;
            if (b) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully", ButtonType.OK);
                manageTimePicker.getEditor().clear();
                manageDatePicker.getEditor().clear();
                txtManageParticipant.clear();
                manageTblView.setItems(new ReservationController().getReservationOnCustomer(customerID));

            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Try Again", ButtonType.OK);
            }
            alert.initOwner(context.getScene().getWindow());
            alert.show();
        }catch (ClassNotFoundException | SQLException e){e.printStackTrace();}
    }

    public void searchCustomerManage(ActionEvent actionEvent) {
        try {
            Customer customerDetail = new CustomerController().getCustomerDetail(txtManageCustomerMobile.getText());
            Alert alert;
            if (customerDetail == null) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "NO Customer for this number .. ", ButtonType.OK);
                alert.initOwner(context.getScene().getWindow());
                alert.show();
            } else {
                txtManageCustomerName.setText(customerDetail.getCustomerName());
                customerID = customerDetail.getCustID();
                manageTblView.setItems(new ReservationController().getReservationOnCustomer(customerID));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

