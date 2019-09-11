package com.adaptionsoft.games.uglytrivia;

import java.util.Random;
import java.util.concurrent.Callable;

public class Player {
    private String name;
    private int coin = 0;
    private int placeOnBoard = 0;
    private boolean isInPenalty = false;

    private int diceValue;

    public Player(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    int getPurse() {
        return coin;
    }

    int getPlaceOnBoard() {
        return placeOnBoard;
    }

    void addCoin() {
        this.coin++;
    }

    void setPlaceOnBoard(int placeDisplacement) {
        placeOnBoard += placeDisplacement;
    }

    void isGettingOutOfPenaltyBox(boolean gettingOut) {
        if(gettingOut) {
            System.out.println(name + " is getting out of the penalty box");
        }
        else {
            System.out.println(name + " is not getting out of the penalty box");
        }
    }

    void roll(String[] args) {
        Random rand = new Random(Integer.parseInt(args[0]));

        diceValue = rand.nextInt(5) + 1;

        System.out.println(name + " is the current player");
        System.out.println("They have rolled a " + diceValue);
    }

    void move() {
        setPlaceOnBoard(diceValue);
    }


}
