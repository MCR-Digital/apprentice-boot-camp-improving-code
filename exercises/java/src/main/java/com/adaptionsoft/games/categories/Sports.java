package com.adaptionsoft.games.categories;

import com.adaptionsoft.games.QuestionCategories;

import java.util.LinkedList;

public class Sports extends Category {

    LinkedList<String> questions = new LinkedList<>();

    @Override
    String getCategory() {
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
