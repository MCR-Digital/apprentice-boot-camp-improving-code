package com.adaptionsoft.games.uglytrivia;

import static com.adaptionsoft.games.uglytrivia.Game.NUMBER_OF_BOARD_PLACES;

public class Player {
    private final String name;
    private int places = 0;

    public String getName() {
        return name;
    }

    public int movePlaces(int roll){
        places += roll;
        if (places >= NUMBER_OF_BOARD_PLACES) {
            places -= NUMBER_OF_BOARD_PLACES;
        }
        return places;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Player(String name) {
        this.name = name;
    }

    public int getPlaces() {
        return places;
    }
}
