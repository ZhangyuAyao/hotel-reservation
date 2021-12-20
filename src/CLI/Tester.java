package CLI;

import java.util.Scanner;

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
        int type = scanner.nextInt();
        System.out.println("Type is: " + type);
    }

}
