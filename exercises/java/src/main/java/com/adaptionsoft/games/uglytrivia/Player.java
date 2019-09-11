package com.adaptionsoft.games.uglytrivia;

public class Player {

    private String name;
    private int boardPosition;
    private int points;
    private boolean inPenaltyBox;
    private boolean gettingOutOfPenaltyBox;

    public Player(String name, int playerNumber) {
        this.name = name;
        System.out.println(name + " was added");
        System.out.println("They are player number " + playerNumber);
    }

    String getName() {
        return name;
    }

    int getBoardPosition() {
        return boardPosition;
    }

    void movePlayerForward(int spaces) {
        boardPosition = boardPosition + spaces;
        boardPosition = boardPosition % 12;
        System.out.println(name + "'s new location is " + boardPosition);
    }

    void updatePoints() {
        points++;
        System.out.println(name + " now has " + points + " Gold Coins.");
    }

    boolean checkIfPlayerHasWon() {
        return points == 6;
    }

    void putInPenaltyBox() {
        this.inPenaltyBox = true;
    }

    private void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        this.gettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    void adjustPenaltyBoxStatus(int rollValue) {
        if (inPenaltyBox) {
            checkPenaltyBoxStatus(rollValue);
        }
    }

    private void checkPenaltyBoxStatus(int rollValue) {
        if (rollValue % 2 != 0) {
            setGettingOutOfPenaltyBox(true);
            System.out.println(name + " is getting out of the penalty box");
        } else {
            setGettingOutOfPenaltyBox(false);
            System.out.println(name + " is not getting out of the penalty box");
        }
    }

    boolean playerIsMovable() {
        return gettingOutOfPenaltyBox || !inPenaltyBox;
    }
}
