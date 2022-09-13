package com.adaptionsoft.games.uglytrivia;

public class Player {
    private static final int MAX_TILES = 12;
    private final String playerName;
    private int currentTile;
    private int coins;
    private boolean isInPenaltyBox;

    public Player (String playerName) {
        this.playerName = playerName;
        this.currentTile = 0;
        this.coins = 0;
        this.isInPenaltyBox = false;
    }

    public String getPlayerName() {
        return playerName;
    }


    public int getCurrentTile() {
        return currentTile;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void putInPenaltyBox() {
        this.isInPenaltyBox = true;
    }

    void playerMoves(int roll) {
        currentTile = currentTile + roll;
        if (currentTile > 11) currentTile = currentTile - MAX_TILES;

        System.out.println(playerName
                + "'s new location is "
                + currentTile);
    }

    public void addCoins (int coins) {
        this.coins += coins;
    }
}
