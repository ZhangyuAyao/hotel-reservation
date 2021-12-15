package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;


public class ReservationService {
    //state
    public static List<Room> roomList = new LinkedList<>();
    public static Collection<Reservation> reservationList = new LinkedList<>();


    //method
    public static void addRoom(Room room){
        roomList.add(room);
    }

    public static Room getARoom(String roomId){
        //检索列表中的roomId，返回相应的Room
        for(Room room: roomList){
            if(room.getRoomNumber() == roomId){
                return room;
            }
        }
        return null;
    }

    public static Reservation reserveARoom(Customer customer, Room room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> storeRoomList = new ArrayList<>();
        for(Reservation reservation: reservationList){
            if(reservation.getCheckInDate() == checkInDate && reservation.getCheckOutDate() == checkOutDate){
                roomList.add(reservation.getRoom());
            }
        }
        return storeRoomList;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer){
        Collection<Reservation> storeReservationList = new ArrayList<>();
        for(Reservation reservation: reservationList){
            if(reservation.getCustomer() == customer){
                storeReservationList.add(reservation);
            }
        }
        return storeReservationList;
    }

    public static void printAllReservation(){
        for(Reservation reservation: reservationList){
            System.out.println(reservation);
        }
    }


}
