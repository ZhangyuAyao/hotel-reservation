package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.Date;
import java.util.Collection;

public abstract class HotelResource {
    public abstract Customer getCustomer(String email);
    public abstract void createACustomer(String email, String firstName, String lastName);
    public abstract IRoom getRoom(String roomNumber);
    public abstract Reservation bookARoom(String cusmtomerEmail, IRoom room, Date checkInDate, Date checkOutDate);
    public abstract Collection<Reservation> getCustomersReservations(String customerEmail);
}
