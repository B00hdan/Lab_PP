package com.games.droidbattle;
import com.games.droidbattle.arena.City;
import com.games.droidbattle.arena.Factory;
import com.games.droidbattle.arena.Landfill;
import com.games.droidbattle.droid.BaseDroid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Battle {
    private final String table = new String(new char[36]).replace("\0", "~");
    Random random = new Random();
    PrintWriter printStream;
    private final List<BaseDroid> droids;
    private List<BaseDroid> redTeam;
    private List<BaseDroid> blueTeam;
    private final int numbInTeam;
    private char markTeamAttacker;
    private BaseDroid attacker;
    private BaseDroid defender;
    private int currentRound = 0;

    public Battle(List<BaseDroid> droids, int numbInTeam) throws IOException {
        this.droids = droids;
        this.numbInTeam = numbInTeam;
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();
        printStream = new PrintWriter(new BufferedWriter(new FileWriter("temp.txt", true)));
    }
    public void startFight(int arena) throws InterruptedException, IOException {
        createTeams();
        chooseArena(arena);
        do {
            prepareRound();
            int actualDamage = doFight();
            printRoundInfo(actualDamage);
            TimeUnit.SECONDS.sleep(1);
        } while (defendTeamAlive());
        printWinner();

        printStream.close();
    }
    public void createTeams() {
        System.out.println(table);
        System.out.print("Who will be include to red team?: ");
        addInList(redTeam);
        System.out.println(table);
        System.out.print("Who will be include to blue team?: ");
        addInList(blueTeam);
        System.out.println(table);
    }
    private void addInList(List<BaseDroid> list){
        Scanner buff = new Scanner(System.in);
        int numb;
        for (int n = numbInTeam; n > 0; n--) {
            numb = buff.nextInt();
            list.add(droids.get(numb - 1).copyDroid());
        }
    }
    private void chooseArena(int type){
        new City();
        City arena = switch (type) {
            case 2 -> new Factory();
            case 3 -> new Landfill();
            default -> new City();
        };
        redTeam = arena.setArena(redTeam);
        blueTeam = arena.setArena(blueTeam);
    }
    final public void prepareRound() {
        currentRound++;
        printConsoleFile("\nRound #" + currentRound);
        initFighters();
    }
    private void initFighters() {
        do {
            if (random.nextBoolean()) {
                attacker = redTeam.get(random.nextInt(numbInTeam));
                defender = blueTeam.get(random.nextInt(numbInTeam));
                markTeamAttacker = 'r';
            } else {
                attacker = blueTeam.get(random.nextInt(numbInTeam));
                defender = redTeam.get(random.nextInt(numbInTeam));
                markTeamAttacker = 'b';
            }
        }while ( !attacker.isAlive() && !defender.isAlive());
    }
    private int doFight() {
        if(attacker.isStun()){
            printConsoleFile(attacker.getName() + " is stunned for " +
                    attacker.getStun() + " turns");
            attacker.setStun(attacker.getStun() - 1);
            return 0;
        }
        int chanceAbility = 40;
        if(random.nextInt(100) < chanceAbility)
            doAbility();
        return defender.getHit(attacker.getDamage());
    }
    private void doAbility() {
        switch (attacker.getType()) {
            case "Tiny":
                printConsoleFile(defender.getName() + " get fast critical hit with "
                        + defender.getHit(attacker.ability()) + " damage");
                break;
            case "Healer":
                int ally = random.nextInt(numbInTeam);
                if (markTeamAttacker == 'r') {
                    printHealInfo(redTeam, ally);
                } else {
                    printHealInfo(blueTeam, ally);
                }
                break;
            case "Massive":
                break;
            case "Shocker":
                printConsoleFile(defender.getName() + " have been stunned for "
                        + defender.setStun(attacker.ability()) + " turns");

        }
    }
    private void printConsoleFile(String text) {
        System.out.println(text);
        printStream.println(text);
    }
    private void printRoundInfo(int actualDamage) {
        printConsoleFile(defender.getName() + " get hit with " + actualDamage
                + " damage from " + attacker.getName());
        printConsoleFile(table);
        printConsoleFile("Red team:                 Blue team:");
        for (int n = 0; n <numbInTeam; n++)
            printConsoleFile( n+1 + ")" + redTeam.get(n) + ";  " + blueTeam.get(n));
        printConsoleFile(table);
    }
    private int makeHeal (int index){
        int actualHeal = 0;
        if(markTeamAttacker == 'r'){
            actualHeal = redTeam.get(index).getHeal(attacker.ability());
        } else{
            actualHeal = blueTeam.get(index).getHeal(attacker.ability());
        }
        return actualHeal;
    }
    private void printHealInfo(List<BaseDroid> team, int index){
        printConsoleFile(team.get(index).getName() + " heals for " + makeHeal(index));
    }
    private boolean defendTeamAlive(){
        if(markTeamAttacker == 'b'){
            return checkTeamAlive(redTeam);
        } else {
            return checkTeamAlive(blueTeam);
        }
    }
    private boolean checkTeamAlive(List<BaseDroid> team){
        boolean alive = false;
        for (BaseDroid elem: team){
            alive = alive || elem.isAlive();
        }
        return alive;
    }
    private void printWinner(){
        if (markTeamAttacker == 'r'){
            printConsoleFile("The winner is Red team!");
        } else {
            printConsoleFile("The winner is Blue team!");
        }
    }
}
