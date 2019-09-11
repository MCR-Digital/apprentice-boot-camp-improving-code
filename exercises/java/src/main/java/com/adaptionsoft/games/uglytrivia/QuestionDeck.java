package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;

public class QuestionDeck {

    LinkedList popQuestionCards = new LinkedList();
    LinkedList scienceQuestionCards = new LinkedList();
    LinkedList sportsQuestionCards = new LinkedList();
    LinkedList rockQuestionCards = new LinkedList();

    HashMap<String, LinkedList> questionDeck = new HashMap<>();

    public  QuestionDeck(){
        for (int i = 0; i < 50; i++) {
            popQuestionCards.addLast(createQuestion("Pop", i));
            scienceQuestionCards.addLast((createQuestion("Science", i)));
            sportsQuestionCards.addLast((createQuestion("Sports", i)));
            rockQuestionCards.addLast(createQuestion("Rock", i));
        }
        questionDeck.put("Pop", popQuestionCards);
        questionDeck.put("Science", scienceQuestionCards);
        questionDeck.put("Sports", sportsQuestionCards);
        questionDeck.put("Rock", rockQuestionCards);
    }

    private String createQuestion(String topic, int index) {
        return topic + " Question " + index;
    }

    public void askQuestion(String currentCategory) {
        System.out.println(questionDeck.get(currentCategory).removeFirst());
    }
}
