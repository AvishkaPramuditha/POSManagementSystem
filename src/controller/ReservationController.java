package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reservation;
import view.tm.ReservationTM;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationController {
    public boolean addReservation(Reservation reservation) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO reservation  VALUES (?,?,?,?,?)");
        preparedStatement.setString(1,reservation.getReservationID());
        preparedStatement.setString(2,reservation.getCustID());
        preparedStatement.setString(3,reservation.getReservationDate());
        preparedStatement.setString(4,reservation.getReservationTime());
        preparedStatement.setInt(5,reservation.getParticipants());
        return preparedStatement.executeUpdate()>0;
    }
    public String ReservationID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT ReservationID FROM Reservation ORDER BY ReservationID DESC LIMIT 1").executeQuery();
        if (resultSet.next()){
            Integer integer = Integer.valueOf(resultSet.getString(1).split("-")[1])+1;
            if (integer<9){
                return "R-000"+integer;
            }else if (integer<99){ return "R-00"+integer;}else if (integer<999){ return "R-0"+integer;}else {return "R-"+integer;}

        }else{
            return "R-0001";
        }
    }

    public ObservableList<ReservationTM> getReservationOnDate(Date date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT r.ReservationID ,c.CustName ,c.CustMobileNumber ,r.ReservationTime,r.participants FROM reservation r INNER JOIN customer c On c.CustID=r.CustID WHERE r. ReservationDate ='" + date.toString() + "'").executeQuery();
        ObservableList<ReservationTM> list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new ReservationTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5)));
        }
        return list;
    }

    public ObservableList<ReservationTM> getReservationOnCustomer(String custID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT ReservationID ,ReservationDate ,ReservationTime,participants FROM reservation  WHERE CustID='" + custID + "'").executeQuery();
        ObservableList<ReservationTM> list= FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new ReservationTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),Integer.valueOf(resultSet.getString(4))));
        }
        return list;
    }

    public boolean deleteReservation(String reservationID) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement("Delete FROM reservation WHERE ReservationID='"+reservationID+"'").executeUpdate()>0;
    }

    public boolean updateReservation(Reservation reservation) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement("UPDATE reservation SET ReservationDate=?,ReservationTime=?,Participants=? WHERE ReservationID=?");
        preparedStatement.setString(1,reservation.getReservationDate());
        preparedStatement.setString(2,reservation.getReservationTime());
        preparedStatement.setInt(3,reservation.getParticipants());
        preparedStatement.setString(4,reservation.getReservationID());
        return preparedStatement.executeUpdate()>0;
    }
}
