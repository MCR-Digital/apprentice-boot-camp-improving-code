package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private static final int NUMBER_OF_CATEGORY_QUESTIONS = 50;
    private static final String POP_QUESTION = "Pop Question ";
    private static final String SCIENCE_QUESTION = "Science Question ";
    private static final String SPORTS_QUESTION = "Sports Question ";
    private static final String ROCK_QUESTION = "Rock Question ";

    private List<String> players = new ArrayList<>();
    private int[] boardSquareIndex = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        generateQuestionsForEachCategory();
    }

    private void generateQuestionsForEachCategory() {
        for (int questionNumber = 0; questionNumber < NUMBER_OF_CATEGORY_QUESTIONS; questionNumber++) {
            popQuestions.addLast(POP_QUESTION + questionNumber);
            scienceQuestions.addLast(SCIENCE_QUESTION + questionNumber);
            sportsQuestions.addLast((SPORTS_QUESTION + questionNumber));
            rockQuestions.addLast(ROCK_QUESTION + questionNumber);
        }
    }

    public void initialisePlayer(String playerName) {
        players.add(playerName);
        boardSquareIndex[getNumberOfPlayers()] = 0;
        purses[getNumberOfPlayers()] = 0;
        inPenaltyBox[getNumberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private int getNumberOfPlayers() {
        return players.size();
    }

    public void rollDice(int rollValue) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + rollValue);

        if (inPenaltyBox[currentPlayer]) {
            if (rollValue % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

            } else {
                isGettingOutOfPenaltyBox = false;
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            }
        }

        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            movePlayerForward(rollValue);
        }

    }

    private void movePlayerForward(int rollValue) {
        boardSquareIndex[currentPlayer] = boardSquareIndex[currentPlayer] + rollValue;
        if (boardSquareIndex[currentPlayer] > 11)
            boardSquareIndex[currentPlayer] = boardSquareIndex[currentPlayer] - 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + boardSquareIndex[currentPlayer]);
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory().equals("Pop"))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (boardSquareIndex[currentPlayer] == 0) return "Pop";
        if (boardSquareIndex[currentPlayer] == 4) return "Pop";
        if (boardSquareIndex[currentPlayer] == 8) return "Pop";
        if (boardSquareIndex[currentPlayer] == 1) return "Science";
        if (boardSquareIndex[currentPlayer] == 5) return "Science";
        if (boardSquareIndex[currentPlayer] == 9) return "Science";
        if (boardSquareIndex[currentPlayer] == 2) return "Sports";
        if (boardSquareIndex[currentPlayer] == 6) return "Sports";
        if (boardSquareIndex[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                return updatePlayerPurse();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            return updatePlayerPurse();
        }
    }

    private boolean updatePlayerPurse() {
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
