package com.adaptionsoft.games.categories;

public class Player {
    String playerName;
    Integer boardLocation;
    Integer purse;
    Boolean isInPenaltyBox;
    Boolean isGettingOutOfPenaltyBox;

    public Player(String playerName, Integer playerNumber) {
        this.playerName = playerName;
        this.boardLocation = 1;
        this.purse = 0;
        this.isInPenaltyBox = false;
        this.isGettingOutOfPenaltyBox = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + playerNumber);
    }
}
