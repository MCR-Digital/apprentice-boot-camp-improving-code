package com.adaptionsoft.games.uglytrivia;

public class Player {

    private int coinCount;
    private boolean isInPenaltyBox;
    private String name;

    public Player(String name) {
        coinCount = 0;
        isInPenaltyBox = false;
        this.name = name;
    }

    public int getCoinCount() {
        return coinCount;
    }


    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public String getName() {
        return name;
    }
}
