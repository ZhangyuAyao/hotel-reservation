package CLI;

import api.AdminResource;
import api.HotelResource;
import com.sun.tools.javac.Main;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class Tester {
    public static void main(String[] args) {
//        if (MainMenu.checkInputDateFomat("2029/1/2"))
//            System.out.println("Yes");
//        MainMenu.option1();
        Scanner scanner = new Scanner(System.in);
//        if(MainMenu.checkNumber1To5Format(scanner.nextLine())){
//            System.out.println("1-5");
//        }
//        else{
//            System.out.println("other");
//        }
//        MainMenu.optionOneFindAndReserveARoom();
//        System.out.println("year: ");
//        Integer year = scanner.nextInt();
//        System.out.println("month: ");
//        Integer month = scanner.nextInt();
//        System.out.println("day: ");
//        Integer day = scanner.nextInt();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
//        calendar.set(year, month, day);
//        System.out.println(calendar.getTime());

        //1. add room
        //2. create count
        //3. add reservation
        //4. reserve
        List<IRoom> addRooms = new ArrayList<>();
        addRooms.add(new Room("1", 1.0, RoomType.SINGLE));
        addRooms.add(new Room("2", 2.0, RoomType.DOUBLE));
        AdminResource.addRoom(addRooms);

        Calendar checkInDate = Calendar.getInstance();
        Calendar checkOutDate = Calendar.getInstance();
        HotelResource.createACustomer("zhangyu@123.com", "y", "z");
        while(scanner.nextLine().equals("1")){
            checkInDate.set(2021, 11, 12);
            checkOutDate.set(2021, 11, 13);
            MainMenu.reserveProcess(checkInDate, checkOutDate, "zhangyu@123.com", scanner);
            System.out.println("continue? 1/0");
        }
//        Calendar checkInDate = Calendar.getInstance();
//        Calendar checkOutDate = Calendar.getInstance();
//        checkInDate.set(2021, 11, 12);
//        checkOutDate.set(2021, 11, 13);
//        System.out.println(checkInDate.getTime());
//        System.out.println(checkOutDate.getTime());
//        checkInDate.add(Calendar.DATE, 7);
//        checkOutDate.add(Calendar.DATE, 7);
//        System.out.println(checkInDate.getTime());
//        System.out.println(checkOutDate.getTime());
//        checkInDate.set(2021, 11, 12);
//        checkOutDate.set(2021, 11, 13);
//        System.out.println(checkInDate.getTime());
//        System.out.println(checkOutDate.getTime());
    }

}
