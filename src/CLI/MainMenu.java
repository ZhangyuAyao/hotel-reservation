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
        String input = scanner.nextLine();
        while(!checkNumber1To5Format(input)){
            System.out.println("Enter number 1 to 5: ");
            input = scanner.nextLine();
        }
        MainMenu.selectOptionMainMenu(input);
    }

    //Check the input, make sure the input is 1 to 5
    public static boolean checkNumber1To5Format(String number){
        Pattern pattern = Pattern.compile("^[1-5]$");
        return pattern.matcher(number).matches();
    }

    //chose MainMenu from 1 to 5
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
        String checkInDateString = scanner.nextLine();
        while(!checkInputDateFormat(checkInDateString)){
            System.out.println("Invalid Input");
            checkInDateString = scanner.nextLine();
        }
        String[] tempDate = checkInDateString.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]));
        Date checkInDate = calendar.getTime();

        System.out.println("CheckOut date，Example y/m/d, 2021/12/17");
        String checkOutDateString = scanner.nextLine();
        while(!checkInputDateFormat(checkOutDateString)){
            System.out.println("Invalid Input");
            checkOutDateString = scanner.nextLine();
        }
        tempDate = checkOutDateString.split("/");
        calendar.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]));
        Date checkOutDate = calendar.getTime();

        Collection<IRoom> RoomList = HotelResource.findARoom(checkInDate, checkOutDate);

        int availableSingleRoomCount = 0;
        int availableDoubleRoomCount = 0;
        for(IRoom room: RoomList){
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

    public static boolean checkEmailFormat(String email){
        String emailRegEx = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegEx);
        return pattern.matcher(email).matches();
    }


    public static Boolean checkInputDateFormat(String checkInDate){
        String dateFormat = "^(20[0-9][0-9])/([0-9]|([0-1][0-2]))/([0-9]|([0-2][0-9])|(30))$";
        Pattern pattern = Pattern.compile(dateFormat);
        return pattern.matcher(checkInDate).matches();
    }


}
