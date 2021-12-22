package CLI;

import model.Customer;
import model.IRoom;

import java.net.CookieHandler;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * throw and catch input exception
 */
public class InputValidation {

    public static String getValidNumber1To5(Scanner scanner){
        boolean isDone = false;
        String inputNumber = null;
        while(!isDone) {
            try {
                inputNumber = scanner.nextLine();
                isNumber1To5(inputNumber);
                isDone = true; //if there is no exception, isDone turn to true
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return inputNumber;
    }

    public static Calendar getValidDate(Scanner scanner){
        boolean isDone = false;
        String inputDate;
        String[] tempDate;
        Calendar calendar = null;
        while(!isDone) {
            try {
                inputDate = scanner.nextLine();
                isDate(inputDate);
                isDone = true;
                tempDate = inputDate.split("/");
                calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
       return calendar;
    }

    public static String getValidEmail(Scanner scanner){
        boolean isDone = false;
        String inputEmail = null;
        while(!isDone) {
            try {
                inputEmail = scanner.nextLine();
                isEmail(inputEmail);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return inputEmail;
    }

    public static String getValidYOrN(Scanner scanner){
        boolean isDone = false;
        String input = null;
        while(!isDone) {
            try {
                input = scanner.nextLine();
                isYesOrNo(input);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return input;
    }

    public static String getValidNumber1To2(Scanner scanner){
        boolean isDone = false;
        String input = null;
        while(!isDone) {
            try {
                input = scanner.nextLine();
                is1Or2(input);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return input;
    }

    public static String getValidNumber(Scanner scanner){
        boolean isDone = false;
        String number = null;
        while(!isDone) {
            try {
                number = scanner.nextLine();
                isNumber(number);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return number;
    }

    //get an existing room number
    public static String getValidExistRoomNumber(Scanner scanner, Collection<IRoom> availableRooms){
        boolean isDone = false;
        String roomNumber = null;
        while(!isDone) {
            try {
                roomNumber = scanner.nextLine();
                isNumber(roomNumber);
                isRoomNumber(roomNumber, availableRooms);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return roomNumber;
    }

    //get a unique room number, that means doesn't exist in the database
    public static String getValidUniqueRoomNumber(Scanner scanner, Collection<IRoom> roomList){
        boolean isDone = false;
        String roomNumber = null;
        while(!isDone) {
            try {
                roomNumber = scanner.nextLine();
                isNumber(roomNumber);
                isUniqueRoomNumber(roomNumber, roomList);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return roomNumber;
    }

    public static String getValidUniqueEmail(Scanner scanner, Collection<Customer> customers){
        boolean isDone = false;
        String uniqueEmail = null;
        while(!isDone) {
            try {
                uniqueEmail = scanner.nextLine();
                isEmail(uniqueEmail);
                isUniqueEmail(uniqueEmail, customers);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return uniqueEmail;
    }

    /**
     * check the input, make sure it's in range of 1 to 5
     * otherwise, throw an exception
     *
     * @param number: input data
     */
    public static void isNumber1To5(String number){
        Pattern pattern = Pattern.compile("^[1-5]$");
        if(!pattern.matcher(number).matches()){
            throw new IllegalArgumentException("Invalid number, please enter a number between 1 to 5: ");
        }
    }

    /**
     * if it is not a Date, throw an exception to indicate the wrong input
     * @param date input String date
     */
    public static void isDate(String date){
        String dateFormat = "^(20[0-9][0-9])/([0-9]|([0-1][0-2]))/([0-9]|([0-2][0-9])|(30))$";
        Pattern pattern = Pattern.compile(dateFormat);
        if(!pattern.matcher(date).matches()){
            throw new IllegalArgumentException("Invalid email, please enter correct email format");
        }
    }

    /**
     * if it is not an Email, throw an exception to indicate the wrong input
     * @param email input email
     */
    public static void isEmail(String email){
        String emailRegEx = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegEx);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email, please enter a right email: ");
        }
    }


    /**
     * if it is not y or n, throw an exception to indicate the wrong input
     * @param input user's input
     */
    public static void isYesOrNo(String input){
        if(!input.equals("y") && !input.equals("n")){
            throw new IllegalArgumentException("Invalid input, please enter y/n: ");
        }
    }

    public static void is1Or2(String input){
        if(!input.equals("1") && !input.equals("2")){
            throw new IllegalArgumentException("Invalid input, please enter 1 or 2 (1 for single, 2 for double): ");
        }
    }

    /**
     * check number: number can only be a number, can not be string or others.
     * @param number input number
     */
    public static void isNumber(String number){
        String numberRegEx = "^([0-9]+)$";
        Pattern pattern = Pattern.compile(numberRegEx);
        if(!pattern.matcher(number).matches()){
            throw new IllegalArgumentException("Invalid number, please enter a right number: (can only be a number)");
        }
    }

    /**
     * check if the input roomNumber exist in the list
     * @param roomNumber user's input roomNumber
     * @param availableRooms existing room, use to check the input
     */
    public static void isRoomNumber(String roomNumber, Collection<IRoom> availableRooms){
        boolean match = false;
        for(IRoom room: availableRooms) {
            if(room.getRoomNumber().equals(roomNumber)){
                match = true;
            }
        }
        if(!match){
            throw new IllegalArgumentException("Room number doesn't exist, please enter again");
        }
    }

    public static void isUniqueEmail(String inputEmail, Collection<Customer> customers){
        boolean match = false;
        for(Customer customer: customers) {
            if(customer.getEmail().equals(inputEmail)){
                match = true;
            }
        }
        if(match){
            throw new IllegalArgumentException("the email is already exist, please input again");
        }
    }

    public static void isUniqueRoomNumber(String roomNumber, Collection<IRoom> roomList){
        boolean match = false;
        for(IRoom room: roomList) {
            if(room.getRoomNumber().equals(roomNumber)){
                match = true;
            }
        }
        if(match){
            throw new IllegalArgumentException("the room number is already exist, please input again");
        }
    }

}
