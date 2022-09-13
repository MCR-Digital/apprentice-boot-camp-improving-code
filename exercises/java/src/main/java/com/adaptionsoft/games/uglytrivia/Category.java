package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Category {
    private final String name;
    private final LinkedList<String> questions = new LinkedList<>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedList<String> getQuestions() {
        return questions;
    }

    public String askQuestion() {
        return questions.removeFirst();
    }

    public void addQuestion(int num) {
        questions.addLast(String.format("%s Question %d", name, num));
    }
}
