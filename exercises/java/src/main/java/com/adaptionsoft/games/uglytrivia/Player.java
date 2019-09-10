package com.adaptionsoft.games.uglytrivia;

public class Player {

    private int coinCount;
    private int position;
    private boolean isInPenaltyBox;
    private String name;

    public Player(String name) {
        coinCount = 0;
        position = 0;
        isInPenaltyBox = false;
        this.name = name;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public int getPosition() {
        return position;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }


}
