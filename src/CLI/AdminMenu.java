package CLI;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void adminMenu(){
        System.out.println("----------------------------------");
        System.out.println("1.See all Customers");
        System.out.println("2.See all Rooms");
        System.out.println("3.See all Reservation");
        System.out.println("4.Add a room");
        System.out.println("5.Back to main Menu");
        System.out.println("----------------------------------");
        System.out.println("Enter number 1 to 5: ");
        Scanner scanner = new Scanner(System.in);
        //  TODO: Format check
        switch (scanner.nextInt()){
            case 1:
                optionOneSeeAllCustomers();
            case 2:
                optionTwoSeeAllRooms();
            case 3:
                optionThreeSeeAllReservation();
            case 4:
                optionFourAddARoom();
            case 5:
                optionFiveBackToMainMenu();
        }
    }

    //1.See all Customers
    public static void optionOneSeeAllCustomers(){
        Collection<Customer> customers = AdminResource.getAllCustomers();
        for(Customer customer: customers){
            System.out.println(customer.toString());
        }
        AdminMenu.adminMenu();
    }

    //2.See all Rooms
    public static void optionTwoSeeAllRooms(){
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        for(IRoom room: rooms){
            System.out.println(room.toString());
        }
        AdminMenu.adminMenu();
    }

    //3.See all Reservation
    public static void optionThreeSeeAllReservation(){
        AdminResource.displayAllReservations();
        AdminMenu.adminMenu();
    }

    //4.Add a room
    public static void optionFourAddARoom(){
        Scanner scanner = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<>();
        boolean endFlag = true;
        while(endFlag){
            // TODO: 2021/12/18  格式校验
            System.out.println("Please enter Room Number: ");
            String roomNumber = scanner.nextLine();
            System.out.println("Please enter Room Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Please enter Room Type: 1 for single, 2 for double");
            // TODO: 增加1和2格式校验
            String type = scanner.nextLine();
            RoomType roomtype;
            if(type.equals("1")){
                roomtype = RoomType.SINGLE;
            }
            else{
                roomtype = RoomType.DOUBLE;
            }
            rooms.add(new Room(roomNumber, price, roomtype));

            System.out.println("Do you want to add another room? y/n");
            String yOrN = scanner.nextLine();
            while(!yOrN.equals("y") && !yOrN.equals("n")){
                System.out.println("Invalid input");
                System.out.println("Do you want to add another room? y/n");
                yOrN = scanner.nextLine();
            }
            if(yOrN.equals("n")){
                endFlag = false;
            }
        }
        AdminResource.addRoom(rooms);
        AdminMenu.adminMenu();
    }

    //5.Back to main Menu
    public static void optionFiveBackToMainMenu(){
        MainMenu.mainMenu();
    }
}
