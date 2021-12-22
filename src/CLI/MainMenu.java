package CLI;

import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class MainMenu {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("-----------------------------------------------");
        System.out.println("1.Find and reserve a room");
        System.out.println("2.See my reservation");
        System.out.println("3.Create an account");
        System.out.println("4.Admin");
        System.out.println("5.Exit");
        System.out.println("-----------------------------------------------");
        System.out.println("Enter number 1 to 5: ");
        Scanner scanner = new Scanner(System.in);
        String input = InputValidation.getValidNumber1To5(scanner);
        MainMenu.selectOptionMainMenu(input, scanner);
    }

    //chose MainMenu from 1 to 5
    /**
     *
     * @param number user input
     */
    public static void selectOptionMainMenu(String number, Scanner scanner){
        switch (number){
            case "1":
                optionOneFindAndReserveARoom(scanner);
                break;
            case "2":
                optionTwoSeeMyReservation(scanner);
                break;
            case "3":
                optionThreeCreateAnAccount(scanner);
                break;
            case "4":
                optionFourAdmin();
                break;
            case "5":
                break;
        }
    }

    //1.Find and reserve a room
    public static void optionOneFindAndReserveARoom(Scanner scanner){
        System.out.println("Do you have an account? y/n");
        String yOrN = InputValidation.getValidYOrN(scanner);
        if(yOrN.equals("n")){
            System.out.println("Please create an account first");
            mainMenu();
            return;
        }
        System.out.println("Please input your email: ");
        String email = InputValidation.getValidEmail(scanner);
        if(HotelResource.getCustomer(email) == null){
            System.out.println("Account " + email + " is not exist");
            mainMenu();
            return;
        }
        System.out.println("Please input the checkIn and checkOut date: ");
        System.out.println("Please input checkIn date: year/month/day, eg: 2021/12/12");
        Calendar checkInDate = InputValidation.getValidDate(scanner);
        System.out.println("Please input checkOut date: year/month/day, eg: 2021/12/13");
        Calendar checkOutDate = InputValidation.getValidDate(scanner);
        reserveProcess(checkInDate, checkOutDate, email, scanner);
        mainMenu();
    }


    //2.See my reservation
    public static void optionTwoSeeMyReservation(Scanner scanner){
        System.out.println("Please input your email: ");
        String email = InputValidation.getValidEmail(scanner);
        if(HotelResource.getCustomer(email) == null){
            System.out.println("Account " + email + " is not exist");
            mainMenu();
            return;
        }
        Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
        if(reservations.size() == 0){
            System.out.println("You don't have any reservation");
        }
        for(Reservation reservation: reservations){
            System.out.println(reservation.toString());
        }
        mainMenu();
    }

    //3.Create an account
    public static void optionThreeCreateAnAccount(Scanner scanner){
        System.out.println("Please input your email: ");
        String email = InputValidation.getValidEmail(scanner);
        if(HotelResource.getCustomer(email) != null){
            System.out.println("You already have an account: " + email);
        }
        else{
            System.out.println("Please input your first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Please input your last name: ");
            String lastName = scanner.nextLine();
            HotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully");
        }
        mainMenu();
    }

    //4.Admin
    public static void optionFourAdmin(){
        AdminMenu.adminMenu();
    }

    /**
     * get all availableRoom
     * if there are no availableRoom, get all recommended room(check in and check out plus 7 days)
     */
    public static void reserveProcess(Calendar checkInDate, Calendar checkOutDate, String email, Scanner scanner){
        Collection<IRoom> availableRooms = HotelResource.findARoom(checkInDate.getTime(), checkOutDate.getTime());
        System.out.println(checkInDate.getTime());
        System.out.println(checkOutDate.getTime());
        if(availableRooms.size() == 0){
            System.out.println("Sorry to tell you, There are no available rooms");
            checkInDate.add(Calendar.DAY_OF_MONTH, 7);
            checkOutDate.add(Calendar.DAY_OF_MONTH,7);
            Collection<IRoom> recommendRooms = HotelResource.findARoom(checkInDate.getTime(), checkOutDate.getTime());
            if(recommendRooms.size() == 0) {
                mainMenu();
            }
            else{
                System.out.println("Here are some recommended rooms for you in 7 days: ");
                System.out.println("Check in date: " + checkInDate.getTime());
                System.out.println("Check in date: " + checkOutDate.getTime());
                for (IRoom room : recommendRooms) { //display all recommended room to user
                    System.out.println(room.toString());
                }
                System.out.println("Do you want to reserve? y/n");
                String inputYOrN = InputValidation.getValidYOrN(scanner);
                if (inputYOrN.equals("y")) {
                    System.out.println("Which room would you like to reserve? input room number: ");
                    String roomNumber = InputValidation.getValidExistRoomNumber(scanner, recommendRooms);
                    IRoom room = HotelResource.getRoom(roomNumber);
                    Reservation reservation = HotelResource.bookARoom(email, room, checkInDate.getTime(), checkOutDate.getTime());
                    System.out.println("successful reservation!");
                    System.out.println(reservation);
                }
            }
        }
        else{
            //display all available room to user
            for(IRoom room: availableRooms) {
                System.out.println(room);
            }
            System.out.println("Do you want to reserve? y/n");
            String inputYOrN = InputValidation.getValidYOrN(scanner);
            if (inputYOrN.equals("y")) {
                System.out.println("Which room would you like to reserve? input room number: ");
                String roomNumber = InputValidation.getValidExistRoomNumber(scanner, availableRooms);
                IRoom room = HotelResource.getRoom(roomNumber);
                Reservation reservation = HotelResource.bookARoom(email, room, checkInDate.getTime(), checkOutDate.getTime());
                System.out.println("successful reservation!");
                System.out.println(reservation);
            }
        }
    }


}
