package com.adaptionsoft.games.uglytrivia;

public class Player {
    Boolean isPlayerInPenaltyBox;
    String playerName;
    Integer coinPurse;

    public Player(String playerName) {
        this.isPlayerInPenaltyBox = false;
        this.playerName = playerName;
        this.coinPurse = 0;
    }
}
