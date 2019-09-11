package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int coin = 0;
    private int placeOnBoard = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCoin() {
        return coin;
    }

    public int getPlaceOnBoard() {
        return placeOnBoard;
    }

    public void addCoin() {
        this.coin++;
    }

    public void setPlaceOnBoard(int placeDisplacement) {
        placeOnBoard += placeDisplacement;
    }
}
