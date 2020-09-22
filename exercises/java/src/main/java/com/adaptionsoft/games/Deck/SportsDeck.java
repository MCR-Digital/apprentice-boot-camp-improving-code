package com.adaptionsoft.games.Deck;

import com.adaptionsoft.games.uglytrivia.QuestionTypes;

import java.util.LinkedList;

public class SportsDeck extends Deck {

    LinkedList<String> questions = new LinkedList<>();

    public SportsDeck(int numberOfQuestions) {
        super(numberOfQuestions);
        for (int questionNumber = 0; questionNumber < numberOfQuestions; questionNumber++) {
            questions.addLast("Sport Question " + questionNumber);
        }
    }

    @Override
    public QuestionTypes getCategory() {
        return QuestionTypes.Sports;
    }

    @Override
    public String getNextQuestion() {
        return questions.getLast();
    }
}
