package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;
import java.util.List;

public class Questions {
    public Questions(String questionType) {
        for (int i = 0; i < 50; i++) {
            questions.add(i, questionType + " Question " +i);
        }
        this.questionType = questionType;
    }

    String questionType;
    LinkedList questions = new LinkedList();

    public LinkedList getQuestions() {
        return questions;
    }

    public String getQuestionType() {
        return questionType;
    }
}
