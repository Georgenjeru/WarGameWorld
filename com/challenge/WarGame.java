package com.challenge;


import com.challenge.controller.GameLevel;
import com.challenge.controller.WarGameWorld1;

import java.util.Scanner;

public class WarGame {
    public static void main(String[] args) throws InterruptedException, NullPointerException {
        System.out.println("***Start Game****\n Please Select Game Level:\n1.EASY\n2.MEDIUM\n3.HARD");
        String choice = new Scanner(System.in).nextLine();
        int selection = Integer.parseInt(choice);
        switch (selection) {
            case 1:
                new WarGameWorld1(GameLevel.EASY).run();
                Long start = System.currentTimeMillis();
                break;
            case 2:
                new WarGameWorld1(GameLevel.MEDIUM).run();
            case 3:
                new WarGameWorld1(GameLevel.HARD).run();
                break;
        }

    }

}