package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;


public class ReservationService {
    //state
    public static List<IRoom> roomList = new LinkedList<>();
    public static Collection<Reservation> reservationList = new LinkedList<>();


    //method
    public static void addRoom(IRoom room){
        roomList.add(room);
    }

    public static IRoom getARoom(String roomId){
        //检索列表中的roomId，返回相应的Room
        for(IRoom room: roomList){
            if(room.getRoomNumber() == roomId){
                return room;
            }
        }
        return null;
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    //This method is to find all the available room
//    伪代码：
//    1. 先寻找没有被reserve的room
//        遍历所有room，其中如果roomID有不在reservation的roomID中，则符合预定条件
//    2. 再考虑room被reserve的情况
//        遍历所有reservation
//        如果被预定房间的checkout date小于这次的checkin date，则符合条件
//
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> storeRoomList = new HashSet<>();
        for(IRoom room: roomList){
            for(Reservation reservation: reservationList){
                if(!room.getRoomNumber().equals(reservation.getRoom().getRoomNumber())){
                    storeRoomList.add(room);
                }
            }
        }
        for(Reservation reservation: reservationList){
            if(reservation.getCheckOutDate().before(checkInDate)){
                storeRoomList.add(reservation.getRoom());
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
