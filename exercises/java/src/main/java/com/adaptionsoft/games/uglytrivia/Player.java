package com.adaptionsoft.games.uglytrivia;

public class Player {

    private int coinCount;
    private int position;
    private boolean isInPenaltyBox;

    public Player() {
        coinCount = 0;
        position = 0;
        isInPenaltyBox = false;
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
