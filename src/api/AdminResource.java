package api;

import model.Customer;
import model.IRoom;
import model.Room;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }

    public void addRoom(List<Room> rooms){
        for(Room room: rooms){
            ReservationService.addRoom(room);
        }
    }

    public Collection<Room> getAllRooms(){
        return ReservationService.roomList;
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.customersList;
    }

    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }
}
