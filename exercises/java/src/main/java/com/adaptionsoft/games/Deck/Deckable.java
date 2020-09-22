package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.List;

public interface Deckable {

    List<String> questions = null;

    QuestionTypes getCategory();

    String getNextQuestion();

}
