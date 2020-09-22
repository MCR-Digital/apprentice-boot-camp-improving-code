package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.LinkedList;

public class ScienceDeck extends Deck {

    LinkedList<String> questions = new LinkedList<>();

    public ScienceDeck(int numberOfQuestions) {
        super(numberOfQuestions);
        for (int questionNumber = 0; questionNumber < numberOfQuestions; questionNumber++) {
            questions.addLast("Science Question " + questionNumber);
        }
    }

    @Override
    public QuestionTypes getCategory() {
        return QuestionTypes.Science;
    }

    @Override
    public String getNextQuestion() {
        return questions.getLast();
    }

}
