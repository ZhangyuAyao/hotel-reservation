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
        MainMenu.selectOptionMainMenu(input, scanner);
    }

    //chose MainMenu from 1 to 5
    /**
     *
     * @param number
     */
    public static void selectOptionMainMenu(String number, Scanner scanner){
        switch (number){
            case "1":
                optionOneFindAndReserveARoom(scanner);
            case "2":
                optionTwoSeeMyReservation(scanner);
            case "3":
                optionThreeCreateAnAccount(scanner);
            case "4":
                optionFourAdmin();
            case "5":
                optionFiveExit();
        }
    }

    //1.Find and reserve a room
    public static void optionOneFindAndReserveARoom(Scanner scanner){
        System.out.println("Please input the checkIn and checkOut date: ");
        System.out.println("CheckIn date: Example year/month/day, 2021/12/16");
        Date checkInDate = InputValidation.getValidDate(scanner);
        System.out.println("CheckOut date，Example y/m/d, 2021/12/17");
        Date checkOutDate = InputValidation.getValidDate(scanner);
        reserveProcess(checkInDate, checkOutDate, scanner);
    }


    //2.See my reservation
    public static void optionTwoSeeMyReservation(Scanner scanner){
        System.out.println("Please input your email: ");
        String email = InputValidation.getValidEmail(scanner);
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        for(Reservation reservation: reservations){
            System.out.println(reservation.toString());
        }
        mainMenu();
    }

    //3.Create an account
    public static void optionThreeCreateAnAccount(Scanner scanner){
        System.out.println("Please input your email: ");
        String email = InputValidation.getValidEmail(scanner);
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
     * get all availableRoom and reserve process
     */
    public static void reserveProcess(Date checkInDate, Date checkOutDate, Scanner scanner){
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        int availableSingleRoomCount = 0;
        int availableDoubleRoomCount = 0;
        for(IRoom room: availableRooms){
            if(room.getRoomType().equals(RoomType.SINGLE)){
                availableSingleRoomCount += 1;
            }
            else{
                availableDoubleRoomCount += 1;
            }
        }
        if(availableDoubleRoomCount == 0 && availableSingleRoomCount == 0){
            System.out.println("Sorry to tell you, There are no available rooms");
            mainMenu();
            return;
        }
        System.out.println("There are " + availableSingleRoomCount + " single rooms" +
                " and " + availableDoubleRoomCount + " double rooms available!");

        System.out.println("Do you want to reserve?");
        String inputYOrN = InputValidation.getValidYOrN(scanner);

        if (inputYOrN.equals("y")) {
            System.out.println("What kind of room would you like to reserve? 1 for single, 2 for double");
            String roomType = InputValidation.getValidNumber1To2(scanner);
            if(roomType.equals("1") && availableSingleRoomCount == 0){
                System.out.println("There are no single rooms available");
                mainMenu();
                return;
            }else if(roomType.equals("2") && availableDoubleRoomCount == 0){
                System.out.println("There are no double rooms available");
                mainMenu();
                return;
            }

            //从RoomList选取一间单人房or双人房,1 for single, 2 for double
            IRoom reserveRoom = null;
            if(roomType.equals("1")){
                for(IRoom room: availableRooms){
                    if(room.getRoomType().equals(RoomType.SINGLE)){
                        reserveRoom = room;
                        break;
                    }
                }
            } else {
                for(IRoom room: availableRooms){
                    if(room.getRoomType().equals(RoomType.DOUBLE)){
                        reserveRoom = room;
                        break;
                    }
                }
            }

            System.out.println("Please input your email: ");
            String email = InputValidation.getValidEmail(scanner);
            HotelResource.bookARoom(email, reserveRoom, checkInDate, checkOutDate);
        } else{
            optionOneFindAndReserveARoom(scanner);
        }

    }
}
