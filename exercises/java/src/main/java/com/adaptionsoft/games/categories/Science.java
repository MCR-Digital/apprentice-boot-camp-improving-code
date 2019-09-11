package com.adaptionsoft.games.categories;

import com.adaptionsoft.games.QuestionCategories;

import java.util.LinkedList;

public class Science extends Category {

    private LinkedList<String> questions = new LinkedList<>();

    public Science() {
        int MAX_NUMBER_OF_QUESTIONS = 50;
        for (int index = 0; index < MAX_NUMBER_OF_QUESTIONS; index++) {
            questions.addLast("Science Question " + index);
        }
    }

    @Override
    public String getCategory() {
        return QuestionCategories.SCIENCE.getCategory();
    }

    @Override
    public LinkedList<String> getQuestions() {
        return questions;
    }
}
