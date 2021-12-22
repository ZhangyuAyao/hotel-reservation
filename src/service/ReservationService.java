package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
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
            if(room.getRoomNumber().equals(roomId)){
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

    /**
     * This method is to find all the available room
     *     伪代码：
     *     1. 预定的房间为0，则所有房间都满足条件，返回所有房间。否则再执行下面的操作2、3
     *     2. 先寻找没有被reserve的room
     *         遍历所有room，其中如果roomID有不在reservation的roomID中，则符合预定条件
     *     2. 再考虑room被reserve的情况
     *         遍历所有reservation
     *         如果被预定房间的checkout date小于这次的checkIn date，则符合条件
     *         如果被预定房间的checkIn date大于这次的checkOut date，则符合条件
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRoomList = new HashSet<>();
        //the condition where reservationList is null
        if(reservationList.size() == 0){
            return roomList;
        }
        for(IRoom room: roomList){
            boolean isBook = false;
            for(Reservation reservation: reservationList){
                if(!room.getRoomNumber().equals(reservation.getRoom().getRoomNumber())){
                    isBook = true;
                }
            }
            if(!isBook){
                availableRoomList.add(room);
            }
        }
        for(Reservation reservation: reservationList){
            if(reservation.getCheckOutDate().before(checkInDate) || reservation.getCheckInDate().after(checkOutDate)){
                availableRoomList.add(reservation.getRoom());
            }
        }
        return availableRoomList;
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
        if(reservationList.size() == 0){
            System.out.println("There is no reservation");
        }
        else{
            for(Reservation reservation: reservationList){
                System.out.println(reservation);
            }
        }
    }


}
