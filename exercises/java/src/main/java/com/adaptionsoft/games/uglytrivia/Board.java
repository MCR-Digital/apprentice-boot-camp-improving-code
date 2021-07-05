package com.adaptionsoft.games.uglytrivia;

public class Board {
    private String[] spaces;

    public Board() {
        this.spaces = new String[12];
        spaces[0] = "Pop";
        spaces[1] = "Science";
        spaces[2] = "Sports";
        spaces[3] = "Rock";
        spaces[4] = "Pop";
        spaces[5] = "Science";
        spaces[6] = "Sports";
        spaces[7] = "Rock";
        spaces[8] = "Pop";
        spaces[9] = "Science";
        spaces[10] = "Sports";
        spaces[11] = "Rock";
    }

    public String getCategoryFromPosition(int currentPlayerPosition) {
        return spaces[currentPlayerPosition];
    }
}
