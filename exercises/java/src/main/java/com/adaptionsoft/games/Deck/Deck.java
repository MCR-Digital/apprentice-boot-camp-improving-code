package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.List;

public class Deck implements Deckable {

    List<String> questions;

    public Deck(int numberOfQuestions) {

    }

    @Override
    public QuestionTypes getCategory() {
        return null;
    }

    @Override
    public String getNextQuestion() {
        return null;
    }
}
