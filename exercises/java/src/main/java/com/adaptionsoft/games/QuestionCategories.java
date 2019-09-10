package com.adaptionsoft.games;

public enum QuestionCategories {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private String category;

    QuestionCategories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }
}
