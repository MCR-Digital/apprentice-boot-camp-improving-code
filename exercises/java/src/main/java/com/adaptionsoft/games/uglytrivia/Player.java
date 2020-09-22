package com.adaptionsoft.games.uglytrivia;

public class Player {

    private String name;
    private int positionOnBoard = 0;
    private int coinsInPurse = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionOnBoard() {
        return positionOnBoard;
    }

    public void setPositionOnBoard(int positionOnBoard) {
        this.positionOnBoard = positionOnBoard;
    }

    public int getCoinsInPurse() {
        return coinsInPurse;
    }

    public void setCoinsInPurse(int coinsInPurse) {
        this.coinsInPurse = coinsInPurse;
    }
}
