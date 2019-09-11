package com.adaptionsoft.games.uglytrivia;

public class Player {
    public String name;
    public int playerPositionOnBoard;

    public Player(String name) {
        this.name = name;
        this.playerPositionOnBoard = 0;
    }

    public String getName() {
        return name;
    }
}
