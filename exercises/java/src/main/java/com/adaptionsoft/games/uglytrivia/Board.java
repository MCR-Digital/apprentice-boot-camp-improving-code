package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Board {

    String[] boardSquares= {"Pop", "Science", "Sports", "Rock"};
    ArrayList<Player> players = new ArrayList<>();
    int[] playerBoardPosition = new int[6];
    QuestionDeck questionDeck;

    public Board() {
        questionDeck = new QuestionDeck();
    }

    public String currentCategory(int currentPlayer) {
        int currentPosition = playerBoardPosition[currentPlayer];
        return boardSquares[currentPosition % 4];
    }

    public void askQuestion(String currentCategory) {
        questionDeck.askQuestion(currentCategory);
    }


}
