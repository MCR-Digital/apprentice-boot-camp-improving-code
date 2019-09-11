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
        if (boardPosition > 11) {
            boardPosition = boardPosition - 12;
        }
        System.out.println(name + "'s new location is " + boardPosition);
    }

    void updatePoints() {
        points++;
        System.out.println(name + " now has " + points + " Gold Coins.");
    }

    boolean checkIfPlayerHasWon() {
        return points == 6;
    }

    boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    boolean isGettingOutOfPenaltyBox() {
        return gettingOutOfPenaltyBox;
    }

    void putInPenaltyBox() {
        this.inPenaltyBox = true;
    }

    void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        this.gettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}
