package model;

import java.util.Date;

public class Reservation {
    protected Customer customer;
    protected Room room;
    protected Date checkInDate;
    protected Date checkOutDate;

    @Override
    public String toString(){
        return "customer: " + customer
                + " room: " + room
                + " checkInDate: " + checkInDate
                + " checkOutDate: " + checkOutDate;
    }

}
