package model;

import java.util.Date;

public class Reservation {
    protected Customer customer;
    protected Room room;
    protected Date checkInDate;
    protected Date checkOutDate;

    public Reservation(Customer customer, Room room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    //getter
    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    //setter
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString(){
        return "customer: " + customer
                + " room: " + room
                + " checkInDate: " + checkInDate
                + " checkOutDate: " + checkOutDate;
    }

}
