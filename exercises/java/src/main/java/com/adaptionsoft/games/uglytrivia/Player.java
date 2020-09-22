package com.adaptionsoft.games.uglytrivia;

public class Player {
    Boolean isPlayerInPenaltyBox;
    String playerName;
    Integer coinPurse;
    Integer positionOnBoard;
    Integer playerNumber;

    public Boolean getPlayerInPenaltyBox() {
        return isPlayerInPenaltyBox;
    }

    public void setPlayerInPenaltyBox(Boolean playerInPenaltyBox) {
        isPlayerInPenaltyBox = playerInPenaltyBox;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getCoinPurse() {
        return coinPurse;
    }

    public void setCoinPurse(Integer coinPurse) {
        this.coinPurse = coinPurse;
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
