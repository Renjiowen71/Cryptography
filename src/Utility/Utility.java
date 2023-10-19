package Utility;


import java.util.Scanner;

public class Utility {
    public static Scanner SCANNER = new Scanner(System.in);
    public static String getMessage(){
        System.out.println("Insert Message ");
        return SCANNER.nextLine();
    }
}
