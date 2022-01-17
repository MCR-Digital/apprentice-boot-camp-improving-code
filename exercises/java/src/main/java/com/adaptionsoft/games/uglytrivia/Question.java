package com.adaptionsoft.games.uglytrivia;

public class Question {
    private final String category;
    private final int index;

    public Question(String category, int index) {
        this.category = category;
        this.index = index;
    }

    @Override
    public String toString() {
        return category + " Question " + index;
    }
}
