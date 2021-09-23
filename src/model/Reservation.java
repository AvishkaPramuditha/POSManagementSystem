package model;

public class Reservation {
    private String reservationID;
    private String custID;
    private String reservationDate;
    private String reservationTime;
    private int participants;

    public Reservation(String reservationID, String reservationDate, String reservationTime, int participants) {
        this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.participants = participants;
    }

    public Reservation(String reservationID, String custID, String reservationDate, String reservationTime, int participants) {
        this.setReservationID(reservationID);
        this.setCustID(custID);
        this.setReservationDate(reservationDate);
        this.setReservationTime(reservationTime);
        this.setParticipants(participants);
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getReservationDate() {
        return reservationDate;
    }


    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
