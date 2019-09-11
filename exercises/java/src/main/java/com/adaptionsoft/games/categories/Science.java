package com.adaptionsoft.games.categories;

import com.adaptionsoft.games.QuestionCategories;

import java.util.LinkedList;

public class Science extends Category {

    LinkedList<String> questions = new LinkedList<>();

    @Override
    public String getCategory() {
        return QuestionCategories.SCIENCE.getCategory();
    }

    @Override
    public LinkedList<String> getQuestions() {
        return questions;
    }

    @Override
    String questionNumber() {
       return "Science Question ";
    }
}
