package com.games.droidbattle;

import com.games.droidbattle.droid.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    boolean exit = false;
    static Scanner buff = new Scanner(System.in);
    static String table = new String(new char[26]).replace("\0", "~");
    static List<BaseDroid> droids = new ArrayList<>();
    public void menu() throws InterruptedException, IOException {
        while(!exit) {
            System.out.println("\n"+table);
            System.out.println("|          MENU          |");
            System.out.println(table);
            System.out.println("1)Start new battle");
            System.out.println("2)Create droids");
            System.out.println("3)See all droids");
            System.out.println("4)Info about droids");
            System.out.println("5)Exit");
            System.out.println(table);
            System.out.print("Choose option:");

            switch (buff.nextInt()) {
                case 1 -> battle();
                case 2 -> createDroid();
                case 3 -> getAllDroids();
                case 4 -> getInfoAboutTypes();
                case 5 -> exit = true;
                default -> System.out.println("This option does`t exist, try again");
            }
        }

    }
    public void battle() throws InterruptedException, IOException {
        if(droids.isEmpty()){
            System.out.println("You don't have droids to battle");
            return;
        }
        System.out.print("How much droids will be in one team? (1 - min): ");
        int numb = buff.nextInt();
        if(numb < 1){ return; }
        Battle newGame = new Battle(droids, numb);
        newGame.startFight(chooseArena());
    }
    public void createDroid(){
        System.out.println("\n"+table);
        System.out.println("|         Creator        |");
        System.out.println(table);
        System.out.print("Enter name:");
        String newName = buff.next();

        System.out.println("\n"+table);
        System.out.println("|          Type          |");
        System.out.println(table);
        System.out.println("1)Tiny");
        System.out.println("2)Healer");
        System.out.println("3)Massive");
        System.out.println("4)Shocker");

        while (true) {
            System.out.print("Choose type:");
            switch (buff.nextInt()) {
                case 1 -> {
                    droids.add(new TinyDroid(newName));
                    return;
                }
                case 2 -> {
                    droids.add(new HealerDroid(newName));
                    return;
                }
                case 3 -> {
                    droids.add(new MassiveDroid(newName));
                    return;
                }
                case 4 -> {
                    droids.add(new ShockerDroid(newName));
                    return;
                }
                default -> System.out.println("This option does`t exist, try again");
            }
        }
    }
    public int chooseArena(){
        System.out.println(table);
        System.out.println("|          Arena         |");
        System.out.println(table);
        System.out.println("1)City");
        System.out.println("2)Factory");
        System.out.println("3)Landfill");
        while(true){
            System.out.print("Choose arena: ");
            int selected = buff.nextInt();
            if(selected == 1 || selected == 2 || selected == 3)
                return selected;
            else {
                System.out.println("This option does`t exist, try again");
            }
        }

    }
    public void getAllDroids(){
        System.out.println("\n"+table);
        System.out.println("|         Droids         |");
        System.out.println(table);
        for(int i = 0; i< droids.size();i++) {
            System.out.printf("|%2d|Name: %s Type: %s\n", i + 1,droids.get(i).getName(),droids.get(i).getType());
        }
        System.out.println(table);
    }
    public void getInfoAboutTypes(){
        System.out.println("\n"+table);
        System.out.println("|     Droids ability     |");
        System.out.println(table);
        System.out.println("Tiny: can make double attack");
        System.out.println("Healer: can restore health");
        System.out.println("Massive: have a lot of power");
        System.out.println("Shocker: can stun other droids");
        System.out.println(table);
    }
}
