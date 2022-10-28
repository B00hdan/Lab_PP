import console.ConsoleMenu;

import java.util.Scanner;

public class Main {
    static ConsoleMenu newMenu = new ConsoleMenu();
    public static void main(String[] args) {
        String comand;
        Scanner buffer = new Scanner(System.in);
        while(true) {
            System.out.print("\\User\\" + newMenu.diskLocation());
            comand = buffer.nextLine();
            newMenu.execute(comand);
        }
    }
}