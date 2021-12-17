package CLI;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String string = scanner.toString();
        while(string != "5"){
            if(string == "1"){
                //1.input checkIn and checkOut Date 2.通过Date查找空闲房间 3.选择空闲房间 4.是否确认预订
                System.out.println("Please input your : ");
                scanner = new Scanner(System.in);
                String email = scanner.nextLine();

            }else if(string == "2"){
                //1.提示输入email 2.返回预订的房间roomId、checkIn、checkOut date
            }else if(string == "3"){
                //1.提示输入email 2.检查是否重复创建 3.如果重复创建，则提示：邮箱号已被注册；
            }else if(string == "4"){
                //1. 进入Admin菜单
                    //1.
            }else if(string == "5"){
                return;
            }
            scanner = new Scanner(System.in);
            string = scanner.toString();
        }


    }
}
