package com.adaptionsoft.games.categories;

import com.adaptionsoft.games.QuestionCategories;

import java.util.LinkedList;

public class Rock extends Category {

    LinkedList<String> questions = new LinkedList<>();

    @Override
    String getCategory() {
        return QuestionCategories.ROCK.getCategory();
    }

    @Override
    public LinkedList<String> getQuestions() {
        return questions;
    }

    @Override
    String questionNumber() {
        return "Rock Question ";
    }
}
