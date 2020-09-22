package com.adaptionsoft.games.uglytrivia;

public class Player {
    Boolean isPlayerInPenaltyBox;
    String playerName;
    Integer coinPurse;
    private Integer positionOnBoard;
    Integer playerNumber;

    public Boolean getPlayerInPenaltyBox() {
        return isPlayerInPenaltyBox;
    }

    public void setPlayerInPenaltyBox(Boolean playerInPenaltyBox) {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerName + " was sent to the penalty box");
        isPlayerInPenaltyBox = playerInPenaltyBox;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getCoinPurse() {
        System.out.println(playerName + " now has " + coinPurse + " Gold Coins.");
        return coinPurse;
    }

    public void addCoinToPurse() {
        this.coinPurse = coinPurse+1;
    }

    public Integer getPositionOnBoard() {
        return positionOnBoard;
    }

    public void setPositionOnBoard(Integer positionOnBoard) {
        this.positionOnBoard = positionOnBoard;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player(String playerName, Integer playerNumber) {
        this.isPlayerInPenaltyBox = false;
        this.playerName = playerName;
        this.coinPurse = 0;
        this.positionOnBoard =0;
        this.playerNumber = playerNumber;
    }
}
