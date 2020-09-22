package com.adaptionsoft.games.uglytrivia;

public class Player {
    private Boolean PlayerInPenaltyBox;
    private String playerName;
    private Integer coinPurse;
    private Integer positionOnBoard;
   private Integer playerNumber;

    public Boolean isPlayerInPenaltyBox() {
        return PlayerInPenaltyBox;
    }

    public void setPlayerInPenaltyBox(Boolean playerInPenaltyBox) {
        PlayerInPenaltyBox = playerInPenaltyBox;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getCoinPurse() {
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
        this.PlayerInPenaltyBox = false;
        this.playerName = playerName;
        this.coinPurse = 0;
        this.positionOnBoard =0;
        this.playerNumber = playerNumber;
    }
}
