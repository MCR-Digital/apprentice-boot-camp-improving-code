package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
    private int currentPlayer = 0;
    private ArrayList<Player> players = new ArrayList<>();

    public Players() {
    }

    public void add(String playerName) {
        players.add(new Player(playerName));
    }

    public int size() {
        return players.size();
    }

    public void changeCurrentPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public Player currentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
