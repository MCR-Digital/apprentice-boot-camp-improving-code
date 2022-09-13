package com.adaptionsoft.games.uglytrivia;

import com.sun.source.tree.BreakTree;

import java.nio.file.attribute.UserPrincipal;

public class Player {
    private String playerName;
    private int coins;
    private Boolean penaltyBox;
    private int position;
    public Player(String name){
        coins = 0;
        penaltyBox = false;
        position = 0;
        this.playerName = name;
    }
    public void playersRolls(int roll) {
        position = position + roll;
        if (position> 11)
            position = position - 12;

        System.out.println(playerName
                + "'s new location is "
                + getPosition());


    }


    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public Boolean getPenaltyBox() {
        return penaltyBox;
    }

    public void setPenaltyBox(Boolean penaltyBox) {
        this.penaltyBox = penaltyBox;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }
}
