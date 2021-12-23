package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;


public class ReservationService {
    //state
    public static List<IRoom> roomList = new LinkedList<>();
    static Collection<Reservation> reservationList = new LinkedList<>();

    private static ReservationService reservationServiceInstance = null;
    private ReservationService(){}
    public static ReservationService getReservationServiceInstance(){
        if(reservationServiceInstance == null){
            reservationServiceInstance = new ReservationService();
        }
        return reservationServiceInstance;
    }
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
     *         如果被预定房间的checkout date小于这次的checkIn date，或者被预定房间的checkIn date大于这次的checkOut date，则符合条件，添加这个房间到roomlist中
     *         上面这一判断条件有可能会出现遍历某个reservation的时候，其中的Room时间范围满足上面条件，但是这个Room的其他时段不满足，所以要将这种Room记录一下，最后面再删掉
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRoomList = new HashSet<>();
        Collection<IRoom> notSuitRoom = new ArrayList<>();
        //1.the condition where reservationList is null
        if(reservationList.size() == 0){
            return roomList;
        }
        //2.looking for no booking room
        for(IRoom room: roomList){
            boolean isBook = false;
            for(Reservation reservation: reservationList){
                if(room.equals(reservation.getRoom())){
                    isBook = true;
                    break;
                }
            }
            if(!isBook){
                availableRoomList.add(room);
            }
        }
        //3.looking for booking room but time not in the same range
        // 遍历所有的reservation，如果room不重合，则添加进入availableRoomList中
        // 如果reservation中的room有和输入的日期重合的，则记录这个room到notSuitRoom中
        // 最后将availableRoomList的不满足条件的room去掉（即去掉notSuitRoom中的room）
        for(Reservation reservation: reservationList){
            if(reservation.getCheckInDate().after(checkOutDate) || reservation.getCheckOutDate().before(checkInDate)){
                availableRoomList.add(reservation.getRoom());
            }
            else{
                notSuitRoom.add(reservation.getRoom());
            }
        }
        for(IRoom room: notSuitRoom){
            availableRoomList.remove(room);
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
