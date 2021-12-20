package CLI;

import java.util.Calendar;
import java.util.Date;
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

    public static Date getValidDate(Scanner scanner){
        boolean isDone = false;
        String inputDate = null;
        while(!isDone) {
            try {
                inputDate = scanner.nextLine();
                isDate(inputDate);
                isDone = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        String[] tempDate = inputDate.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2]));
        return calendar.getTime();
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

    /**
     * check the input, make sure it's in range of 1 to 5
     * otherwise, throw an exception
     *
     * @param number: input data
     * @return boolean: true for right input, false for wrong input
     */
    public static void isNumber1To5(String number){
        Pattern pattern = Pattern.compile("^[1-5]$");
        if(!pattern.matcher(number).matches()){
            throw new IllegalArgumentException("Invalid number, please enter a number between 1 to 5: ");
        }
    }

    /**
     * if it is not a Date, throw an exception to indicate the wrong input
     * @param date
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
     * @param email
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
     * @param input
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

}
