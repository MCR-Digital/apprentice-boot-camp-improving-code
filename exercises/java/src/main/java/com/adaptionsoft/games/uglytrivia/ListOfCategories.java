package com.adaptionsoft.games.uglytrivia;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ListOfCategories {

    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";
    private ArrayList popQuestions = new ArrayList();
    private ArrayList scienceQuestions = new ArrayList();
    private ArrayList sportsQuestions = new ArrayList();
    private ArrayList rockQuestions = new ArrayList();

    HashMap<String, List<String>> mapOfListsOfQuestions = new HashMap<>();

    List<String> listOfQuestions;
    String category;

    public ListOfCategories() {
        this.mapOfListsOfQuestions.put(POP, popQuestions);
        this.mapOfListsOfQuestions.put(SCIENCE, scienceQuestions);
        this.mapOfListsOfQuestions.put(SPORTS, sportsQuestions);
        this.mapOfListsOfQuestions.put(ROCK, rockQuestions);
    }
//    public ListOfCategories(String category, List<String> listOfCategories) {
//    this.category = category;
//    this.listOfQuestions = listOfCategories;
//    }

    public void setMapOfListsOfQuestions(String category, List<String> listOfQuestions) {

        this.mapOfListsOfQuestions.put(category, listOfQuestions);
    }

    public HashMap<String, List<String>> getMapOfListsOfQuestions(){
        return this.mapOfListsOfQuestions;
    }

}
