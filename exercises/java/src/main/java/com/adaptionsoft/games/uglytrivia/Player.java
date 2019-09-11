package com.adaptionsoft.games.uglytrivia;

public class Player {
    public String name;
    public int currentGameBoardPosition;

    public Player(String name) {
        this.name = name;
        this.currentGameBoardPosition = 0;
    }

    public String getName() {
        return name;
    }

    public int getCurrentGameBoardPosition() {
        return currentGameBoardPosition;
    }
}
