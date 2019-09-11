package com.adaptionsoft.games.categories;

import java.util.LinkedList;

public abstract class Category {

    abstract String getCategory();

    abstract LinkedList<String> getQuestions();

    abstract String questionNumber();
}
