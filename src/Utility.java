import java.util.Scanner;

public class Utility {
    public static Scanner SCANNER = new Scanner(System.in);
    public static String run(){
        System.out.println("Insert message : ");
        return SCANNER.nextLine();
    }
}
