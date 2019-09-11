package com.adaptionsoft.games.uglytrivia;

public class Category {

    private String categoryName;
    private int questionNumber;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    String getCategoryName() {
        return categoryName;
    }

    int getQuestionNumber() {
        return questionNumber;
    }

    void removeQuestionFromDeck() {
        questionNumber++;
    }
}
