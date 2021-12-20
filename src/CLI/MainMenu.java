package CLI;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import model.RoomType;

public class MainMenu {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("----------------------------------");
        System.out.println("1.Find and reserve a room");
        System.out.println("2.See my reservation");
        System.out.println("3.Create an account");
        System.out.println("4.Admin");
        System.out.println("5.Exit");
        System.out.println("----------------------------------");
        System.out.println("Enter number 1 to 5: ");
        Scanner scanner = new Scanner(System.in);
        String input = InputValidation.getValidNumber1To5(scanner);
        MainMenu.selectOptionMainMenu(input);
    }

    //chose MainMenu from 1 to 5
    /**
     *
     * @param number
     */
    public static void selectOptionMainMenu(String number){
        switch (number){
            case "1":
                optionOneFindAndReserveARoom();
            case "2":
                optionTwoSeeMyReservation();
            case "3":
                optionThreeCreateAnAccount();
            case "4":
                optionFourAdmin();
            case "5":
                optionFiveExit();
        }
    }

    //1.Find and reserve a room
    public static void optionOneFindAndReserveARoom(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input the checkIn and checkOut date: ");
        System.out.println("CheckIn date: Example year/month/day, 2021/12/16");
        Date checkInDate = InputValidation.getValidDate(scanner);
        System.out.println("CheckOut date，Example y/m/d, 2021/12/17");
        Date checkOutDate = InputValidation.getValidDate(scanner);
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        showAvailableRoom(availableRooms);
        System.out.println("Do you want to reserve?");
        String yOrN = scanner.nextLine();
        while(!yOrN.equals("y") && !yOrN.equals("n")) {
            System.out.println("Invalid input");
            yOrN = scanner.nextLine();
        }
        if (yOrN.equals("y")) {
            System.out.println("What kind of Room would you like to reserve? input single or double to chose");
            String roomType = scanner.nextLine();
            while(!roomType.equals("single") && !roomType.equals("double")){
                System.out.println("Invalid input");
                roomType = scanner.nextLine();
            }
            if(roomType.equals("single") && availableSingleRoomCount == 0){
                System.out.println("There are no single rooms available");
                mainMenu();
            }else if(roomType.equals("double") && availableDoubleRoomCount == 0){
                System.out.println("There are no double rooms available");
                mainMenu();
            }

            //从RoomList选取一间单人房or双人房
            IRoom reserveRoom = null;
            if(roomType.equals("single")){
                for(IRoom room: RoomList){
                    if(room.getRoomType() == RoomType.SINGLE){
                        reserveRoom = room;
                        break;
                    }
                }
            } else {
                for(IRoom room: RoomList){
                    if(room.getRoomType() == RoomType.DOUBLE){
                        reserveRoom = room;
                        break;
                    }
                }
            }

            System.out.println("Please input your email: ");
            String email = scanner.nextLine();
            while(!checkEmailFormat(email)){
                System.out.println("Invalid Input, Please input your email: ");
                email = scanner.nextLine();
            }
            HotelResource.bookARoom(email, reserveRoom, checkInDate, checkOutDate);
        } else{
            optionOneFindAndReserveARoom();
        }
    }


    //2.See my reservation
    public static void optionTwoSeeMyReservation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your email: ");
        String email = scanner.nextLine();
        while(!checkInputDateFormat(email)){
            System.out.println("Invalid Input, Please input your email: ");
            email = scanner.nextLine();
        }
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        for(Reservation reservation: reservations){
            System.out.println(reservation.toString());
        }
        mainMenu();
    }

    //3.Create an account
    public static void optionThreeCreateAnAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your email: ");
        String email = scanner.nextLine();
        while(!checkEmailFormat(email)){
            System.out.println("Invalid Input, Please input your email: ");
            email = scanner.nextLine();
        }
        System.out.println("Please input your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Please input your last name: ");
        String lastName = scanner.nextLine();
        HotelResource.createACustomer(email, firstName, lastName);
        mainMenu();
    }

    //4.Admin
    public static void optionFourAdmin(){
        AdminMenu.adminMenu();
    }

    //5.Exit
    public static void optionFiveExit(){}

    /**
     * check the availableRoom
     */
    public static void showAvailableRoom(Collection<IRoom> availableRooms){
        int availableSingleRoomCount = 0;
        int availableDoubleRoomCount = 0;
        for(IRoom room: availableRooms){
            if(room.getRoomType() == RoomType.SINGLE){
                availableSingleRoomCount += 1;
            }
            else{
                availableDoubleRoomCount += 1;
            }
        }
        if(availableDoubleRoomCount == 0 && availableSingleRoomCount == 0){
            System.out.println("Sorry to tell you, There are no available rooms");
            mainMenu();
        }
        System.out.println("There are " + availableSingleRoomCount + " single rooms" +
                " and " + availableDoubleRoomCount + " double rooms available!");
    }


}
