package com.challenge.controller;
import com.challenge.model.Army;
import com.challenge.model.Soldier;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

class TankThread extends Thread{
    private Army enemy;
    private Army ally;
    public TankThread(Army enemy, Army ally){
        this.enemy = enemy;
        this.ally = ally;
    }
    @Override
    public void run() {
        for (int k = 0; k < 10; k ++) {
            int soldierIndex = new Random().nextInt(enemy.getSoldiers().length - 1);
            enemy.getSoldiers()[soldierIndex].shell();
        }
    }
}
class GunThread extends Thread{
    private Army enemy;
    private Army ally;

    private GameLevel gameLevel;

    public GunThread(Army enemy, Army ally, GameLevel gameLevel) {
        this.enemy = enemy;
        this.ally = ally;
        this.gameLevel = gameLevel;
    }

    @Override
    public void run() {
        for (int k = 0; k < 10; k ++) {
            int soldierIndex = new Random().nextInt(enemy.getSoldiers().length - 1);
            enemy.getSoldiers()[soldierIndex].shoot();
        }
        int choice;
        for (int k = 0; k < 10; k ++) {
        int soldierIndex = new Random().nextInt(ally.getSoldiers().length - 1);
        choice = new Random().nextInt(10);
        boolean success =false;
        if (gameLevel == GameLevel.EASY && choice <= 8)
            success = true;
        else if (gameLevel == GameLevel.MEDIUM && choice <= 5)
                success = true;
        else if (gameLevel == GameLevel.HARD && choice <= 2)
            success = true;
        if (success && ally.getSoldiers()[soldierIndex].isAlive())
            ally.getSoldiers()[soldierIndex].shot();
        }
    }
}
public class WarGameWorld1 {
    private boolean GameIsInitialized = false;
    private String currentPlayer;
    private final File dataFile = new File("game_data.data");
    private final File profileFile = new File("game_profile.data");
    private final InputStreamReader inputReader = new InputStreamReader(System.in);
    private final BufferedReader readUserInputs = new BufferedReader(inputReader);
    private final static int maxSoldiers = 20;
    private Army ally;
    private Army enemy;
    private GameLevel gameLevel;

    public WarGameWorld1(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.setupGame();
    }

    private void setupGame() {
        // Create 2 armies (Ally and Enemy)
        ally = new Army();
        enemy = new Army();
        // 1000 soldiers per army
        Soldier[] allySoldiers = new Soldier[maxSoldiers];
        Soldier[] enemySoldiers = new Soldier[maxSoldiers];
        for (int k = 0; k < maxSoldiers; k++) {
            allySoldiers[k] = new Soldier("ALLY_00" + k);
            enemySoldiers[k] = new Soldier("ENEMY_00" + k);
        }
        ally.setSoldiers(allySoldiers);
        enemy.setSoldiers(enemySoldiers);
    }

    private void runGame() throws InterruptedException {
        this.newUser();
        GunThread allyThread = new GunThread(ally, enemy, gameLevel);
        allyThread.start();
        GunThread enemyThread = new GunThread(enemy, ally, gameLevel);
        enemyThread.start();
    }

        private void newUser () {
            //reading user inputs
            String userInputs;
            try {
                OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(profileFile, true));
                BufferedWriter writer1 = new BufferedWriter(outputWriter);
                System.out.println("Create Account to play with:\nEnter Username:");
                userInputs = readUserInputs.readLine();
                System.out.println("Please Enter your Password:");
                writer1.write(userInputs + "," + readUserInputs.readLine() + "\n");
                writer1.flush();
                writer1.close();
                System.out.println("User created\n 1. Continue to Game\n 0. Exit from the Game");
                userInputs = readUserInputs.readLine();
                if (userInputs.equals("1")) {
                    this.initializeGame();
                } else
                    System.out.println("Exiting...");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        private void initializeGame () {
            ArrayList<String[]> profileData = new ArrayList<>(); //this 2D to hold profileName and Password from the file
            String userInputs = "";
            String selection = "";
            try {
                InputStreamReader inputReaderFile = new InputStreamReader(new FileInputStream(profileFile));
                BufferedReader readerFile = new BufferedReader(inputReaderFile);
                this.processInit();
                while (readerFile.ready()) {
                    profileData.add(readerFile.readLine().split("[,]"));
                }
                System.out.println("\n Select a profile to start the Game:");
                while (true) {
                    for (int j = 0; j < profileData.size(); j++) {
                        System.out.println(" " + (j + 1) + ". " + profileData.get(j)[0]);
                    }
                    System.out.println(" *. Add New Profile\n #. Exit");
                    selection = readUserInputs.readLine();

                    if (selection.equals("*")) {
                        this.newUser();
                        break;
                    } else if (selection.equals("#")) {
                        System.out.println("Exiting...");
                        break;
                    } else {
                        boolean breakThisLoop = false;
                        for (int i = 0; i < profileData.size(); i++) {
                            if (selection.equals(Integer.toString((i + 1)))) {
                                String[] playerProfile = profileData.get(i);
                                System.out.println("Enter Password for Profile: " + playerProfile[0]);
                                while (true) {
                                    userInputs = readUserInputs.readLine();
                                    if (userInputs.equals(playerProfile[1])) {
                                        this.GameIsInitialized = true;
                                        this.currentPlayer = playerProfile[0];
                                        System.out.println("\n*************************** " + playerProfile[0].toUpperCase() +
                                                " is now playing ****************************\n");
                                        breakThisLoop = true;
                                        break; //breaks this while loop
                                    } else {
                                        System.out.println("Wrong Password! Enter Passwd again.");
                                        this.GameIsInitialized = false;
                                    }
                                }
                                break; //breaks this for loop
                            } else {
                                if (i == profileData.size() - 1) {//check if the loop has finished without a match of section
                                    System.out.println("Wrong Choice. Select a profile to start the Game:");
                                }
                                this.GameIsInitialized = false;
                            }
                        }
                        if (breakThisLoop)
                            break;
                    }
                }
                readerFile.close();
                //System.out.println("Select Game Mode:");

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private boolean allSoldiersAreDead (Army army){
            for (int k = 0; k < army.getSoldiers().length; k++)
                if (army.getSoldiers()[k].isAlive())
                    return false;
            return true;
        }
        private boolean noWeaponHasBullets (Army army){
            for (int k = 0; k < army.getSoldiers().length; k++)
                if (army.getSoldiers()[k].gunHasBullets())
                    return false;
            return true;
        }
    private void processInit() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        String hex = "2d2d2d2d2d2d2d2d2d2d2d2d2";
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String tempInHex = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(tempInHex, 16);
            result.append((char) decimal);
        }
        System.out.println(result.toString());
        Thread.sleep(1200);
    }
        public void run () throws InterruptedException {

            // - Setup the game [Soldiers, Army (Ally, Enemy), Weapon Arsenal]
            // - Run the game [ Soldiers shoot at enemy, Control Weapons + Arsenal ]
            // - Control the game. Determine, when the game ends...
            // [1 - All soldiers are dead,
            // [2 - No weapon has bullets
            while (true) {
                this.runGame();
                if (allSoldiersAreDead(ally) || allSoldiersAreDead(enemy)
                        || noWeaponHasBullets(ally) || noWeaponHasBullets(enemy))
                Thread.sleep(2000);
            break;
            }
        }
    }


