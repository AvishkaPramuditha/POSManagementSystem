package view.tm;

public class ReservationTM {
    private String reservationID;
    private String customerName;
    private String customerMobile;
    private String time;
    private int participant;
    private String date;

    public ReservationTM() {
    }

    public ReservationTM(String reservationID, String date, String time, int participant) {
        this.reservationID = reservationID;
        this.time = time;
        this.participant = participant;
        this.date = date;
    }

    public ReservationTM(String reservationID, String customerName, String customerMobile, String time, int participant) {
        this.setReservationID(reservationID);
        this.setCustomerName(customerName);
        this.setCustomerMobile(customerMobile);
        this.setTime(time);
        this.setParticipant(participant);
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
