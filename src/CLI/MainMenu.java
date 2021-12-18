package CLI;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import api.HotelResource;
import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

public class MainMenu {
    //find and reserve a room
    public static void showMainOption(){
        System.out.println("1.Find and reserve a room");
        System.out.println("2.See my reservation");
        System.out.println("3.Create an account");
        System.out.println("4.Admin");
        System.out.println("5.Exit");
    }

    public static void option1(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the checkIn and checkOut date: ");
        System.out.println("CheckIn date: Example year/month/day, 2021/12/16");
        String checkInDateString = scanner.nextLine();
        while(!checkInputDateFomat(checkInDateString)){
            System.out.println("Invalid Input");
            checkInDateString = scanner.nextLine();
        }
        String[] tempDate = checkInDateString.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]));
        Date checkInDate = calendar.getTime();

        System.out.println("CheckOut date，Example y/m/d, 2021/12/17");
        String checkOutDateString = scanner.nextLine();
        while(!checkInputDateFomat(checkOutDateString)){
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
            showMainOption();
            return;
        }
        System.out.println("There are " + availableSingleRoomCount + " single rooms" +
                            " and " + availableDoubleRoomCount + " double rooms available!");
        System.out.println("Do you want to reserve?");
        String yOrN = scanner.nextLine();
        while(yOrN != "y" && yOrN != "n") {
            System.out.println("Invalid input");
            yOrN = scanner.nextLine();
        }
        if (yOrN == "y") {
            System.out.println("What kind of Room would you like to reserve? input single or double to chose");
            String roomType = scanner.nextLine();
            while(roomType != "single" && roomType != "double"){
                System.out.println("Invalid input");
                roomType = scanner.nextLine();
            }
            if(roomType == "single" && availableSingleRoomCount == 0){
                System.out.println("There are no single rooms available");
                showMainOption();
                return;
            }else if(roomType == "double" && availableDoubleRoomCount == 0){
                System.out.println("There are no double rooms available");
                showMainOption();
                return;
            }

            //从RoomList选取一间单人房or双人房
            IRoom reserveRoom = null;
            if(roomType == "single"){
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

            String email = inputEmail();
            HotelResource.bookARoom(email, reserveRoom, checkInDate, checkOutDate);
        } else{
            showMainOption();
            return;
        }
    }

    public static String inputEmail(){
        String emailRegEx = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegEx);
        System.out.println("Please input your emil: ");
        Scanner email = new Scanner(System.in);
        String emailString = email.nextLine();
        while(!pattern.matcher(emailString).matches()) {
            System.out.println("Invalid input");
            email = new Scanner(System.in);
            emailString = email.nextLine();
        }
        return emailString;
    }


    public static Boolean checkInputDateFomat(String checkInDate){
        String dateFomat = "^(20[0-9][0-9])/([0-9]|([0-1][0-2]))/([0-9]|([0-2][0-9])|(30))$";
        Pattern pattern = Pattern.compile(dateFomat);
        return pattern.matcher(checkInDate).matches();
    }


}
