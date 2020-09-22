package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.LinkedList;

public class RockDeck extends Deck {

    LinkedList<String> questions = new LinkedList<>();

    public RockDeck(int numberOfQuestions) {
        super(numberOfQuestions);
        for (int questionNumber = 0; questionNumber < numberOfQuestions; questionNumber++) {
            questions.addLast("Rock Question " + questionNumber);
        }
    }

    @Override
    public QuestionTypes getCategory() {
        return QuestionTypes.Rock;
    }

    @Override
    public String getNextQuestion() {
        return questions.getLast();
    }
}
