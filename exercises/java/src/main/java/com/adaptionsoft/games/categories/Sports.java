package com.adaptionsoft.games.categories;

import com.adaptionsoft.games.QuestionCategories;

import java.util.LinkedList;

public class Sports extends Category {

    private LinkedList<String> questions = new LinkedList<>();

    public Sports() {
        int MAX_NUMBER_OF_QUESTIONS = 50;
        for (int index = 0; index < MAX_NUMBER_OF_QUESTIONS; index++) {
            questions.addLast("Sports Question " + index);
        }
    }

    @Override
    public String getCategory() {
        return QuestionCategories.SPORTS.getCategory();
    }

    @Override
    public LinkedList<String> getQuestions() {
        return questions;
    }

    @Override
    String questionNumber() {
        return "Sports Question ";
    }
}
