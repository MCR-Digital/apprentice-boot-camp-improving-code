package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.LinkedList;

public class PopDeck extends Deck {

    LinkedList<String> questions = new LinkedList<>();

    public PopDeck(int numberOfQuestions) {
        super(numberOfQuestions);
        for (int questionNumber = 0; questionNumber < numberOfQuestions; questionNumber++) {
            questions.addLast("Pop Question " + questionNumber);
        }
    }

    @Override
    public QuestionTypes getCategory() {
        return QuestionTypes.Pop;
    }

    @Override
    public String getNextQuestion() {
        return questions.getLast();
    }
}
