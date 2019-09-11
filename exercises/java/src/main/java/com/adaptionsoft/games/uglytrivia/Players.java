package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
    int currentPlayer = 0;
    ArrayList<Player> players = new ArrayList<>();

    public Players() {
    }

    public void add(String playerName) {
        players.add(new Player(playerName));
    }

    public int size() {
        return players.size();
    }
}
