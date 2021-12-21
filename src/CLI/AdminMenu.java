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
        String input = InputValidation.getValidNumber1To5(scanner);
        switch (input){
            case "1":
                optionOneSeeAllCustomers();
            case "2":
                optionTwoSeeAllRooms();
            case "3":
                optionThreeSeeAllReservation();
            case "4":
                optionFourAddARoom(scanner);
            case "5":
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

    //4.Add a or more room
    public static void optionFourAddARoom(Scanner scanner){
        List<IRoom> rooms = new ArrayList<>();
        boolean endFlag = true;
        while(endFlag){
            System.out.println("Please enter Room Number: ");
            String roomNumber = InputValidation.getValidNumber(scanner);
            System.out.println("Please enter Room Price: ");
            double price = Double.parseDouble(InputValidation.getValidNumber(scanner));
            System.out.println("Please enter Room Type: 1 for single bed, 2 for double bed");

            String type = InputValidation.getValidNumber1To2(scanner);
            RoomType roomtype;
            if(type.equals("1")){
                roomtype = RoomType.SINGLE;
            }
            else{
                roomtype = RoomType.DOUBLE;
            }
            rooms.add(new Room(roomNumber, price, roomtype));
            System.out.println("Do you want to add another room? y/n");
            String yOrN = InputValidation.getValidYOrN(scanner);
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
