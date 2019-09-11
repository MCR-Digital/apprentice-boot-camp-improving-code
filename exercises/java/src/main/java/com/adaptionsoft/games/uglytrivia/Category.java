package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

class Category {

    private List<String> questions = new ArrayList<>();


    public Category(String name) {

        for (int index = 0; index < 50; index++) {
            questions.add(("Pop Question " + index));
        }
    }



    String getNextQuestion() {


        return questions.remove(0);
    }
}
