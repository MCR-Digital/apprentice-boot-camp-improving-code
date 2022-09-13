package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] coinCount = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    List<String> board = new ArrayList<>();
    static final int NUMBER_OF_QUESTIONS = 50;
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            popQuestions.addLast(createQuestion(i,"Pop"));
            scienceQuestions.addLast(createQuestion(i,"Science"));
            sportsQuestions.addLast(createQuestion(i,"Sports"));
            rockQuestions.addLast(createQuestion(i,"Rock"));
        }
        for (int i = 0; i < 3; i++) {
            board.add("Pop");
            board.add("Science");
            board.add("Sports");
            board.add("Rock");
        }
    }

    public String createQuestion(int index, String category) {
        return ( category + " Question " + index);
    }

    public void addNewPlayer(String playerName) {
        players.add(playerName);
        places[numberOfPlayers()] = 0;
        coinCount[numberOfPlayers()] = 0;
        isInPenaltyBox[numberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());

    }

    public int numberOfPlayers() {
        return players.size();
    }

    public void turn(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);
        if (isInPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                movePlayer(roll);
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            movePlayer(roll);
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }

    private void movePlayer(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > board.size() - 1) places[currentPlayer] = places[currentPlayer] - board.size();
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        System.out.println("The category is " + currentCategory());
    }

    private String currentCategory() {
        return board.get( places[currentPlayer]);
    }

    public boolean wasCorrectlyAnswered() {
        if (isInPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                return correctAnswer();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            System.out.println("Answer was corrent!!!!");
            return correctAnswer();
        }
    }

    private boolean correctAnswer() {
        coinCount[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + coinCount[currentPlayer]
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        isInPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(coinCount[currentPlayer] == 6);
    }
}
