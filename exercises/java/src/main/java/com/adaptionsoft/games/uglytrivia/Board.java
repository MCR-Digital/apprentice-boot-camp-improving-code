package com.adaptionsoft.games.uglytrivia;

public class Board {
    private final String[] boardPlacesCategories = {"Pop",
            "Science",
            "Sports",
            "Rock",
            "Pop",
            "Science",
            "Sports",
            "Rock",
            "Pop",
            "Science",
            "Sports",
            "Rock"};


    public String getCurrentCategory(int playerPlaces) {
        return boardPlacesCategories[playerPlaces];
    }
}
