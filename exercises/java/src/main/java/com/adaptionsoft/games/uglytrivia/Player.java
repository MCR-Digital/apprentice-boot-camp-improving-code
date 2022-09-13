package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int position = 0;
    private int coinCount = 0;
    private boolean isLeavingPenaltyBox = false;
    private boolean isInPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isLeavingPenaltyBox() {
        return isLeavingPenaltyBox;
    }

    public void setIsLeavingPenaltyBox(boolean isLeavingPenaltyBox) {
        this.isLeavingPenaltyBox = isLeavingPenaltyBox;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setIsInPenaltyBox(boolean isInPenaltyBox) {
        this.isInPenaltyBox = isInPenaltyBox;
    }
    public void leavePenaltyBox() {
        setIsLeavingPenaltyBox(true);
        System.out.println(name + " is getting out of the penalty box");
    }
    public void remainInPenaltyBox() {
        setIsLeavingPenaltyBox(false);
        System.out.println(name + " is not getting out of the penalty box");
    }
    public int getPlayerPosition() {
        return position;
    }

    public void setPlayerPosition(int newPosition) {
        position = newPosition;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int newCoinCount) {
        this.coinCount = newCoinCount;
    }
}
