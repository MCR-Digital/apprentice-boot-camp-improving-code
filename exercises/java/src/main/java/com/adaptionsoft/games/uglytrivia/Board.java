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


}
