package com.adaptionsoft.games.uglytrivia;

public class Question {

    private String category;
    private int questionNumber;

    public Question(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    int getQuestionNumber() {
        return questionNumber;
    }

    void removeQuestionFromDeck() {
        questionNumber++;
    }
}
