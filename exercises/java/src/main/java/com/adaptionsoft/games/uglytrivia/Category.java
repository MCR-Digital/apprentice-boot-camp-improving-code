package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

class Category {

    private final int maxAmountOfQuestions = 50;
    private List<String> questions = new ArrayList<>();
    private String name;


    public Category(String name) {

        for (int index = 0; index < maxAmountOfQuestions; index++) {
            questions.add(("Pop Question " + index));
        }

        this.name = name;
    }

    String name() {
        return "";

    }


    String getNextQuestion() {


        return questions.remove(0);
    }

    boolean matchesPosition() {


        return true;
    }
}
