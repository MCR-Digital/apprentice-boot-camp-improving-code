package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Game {
    private static final int NUMBER_OF_CATEGORY_QUESTIONS = 50;
    private static final String POP_QUESTION = "Pop Question ";
    private static final String SCIENCE_QUESTION = "Science Question ";
    private static final String SPORTS_QUESTION = "Sports Question ";
    private static final String ROCK_QUESTION = "Rock Question ";

    private List<String> players = new ArrayList<>();
    private int[] boardSquares = new int[6];
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
        boardSquares[getNumberOfPlayers()] = 0;
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
            checkPenaltyBoxStatus(rollValue);
        }

        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            movePlayerForward(rollValue);
        }
    }

    private void checkPenaltyBoxStatus(int rollValue) {
        if (rollValue % 2 != 0) {
            isGettingOutOfPenaltyBox = true;
            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
        } else {
            isGettingOutOfPenaltyBox = false;
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
        }
    }

    private void movePlayerForward(int rollValue) {
        boardSquares[currentPlayer] = boardSquares[currentPlayer] + rollValue;
        if (boardSquares[currentPlayer] > 11) {
            boardSquares[currentPlayer] = boardSquares[currentPlayer] - 12;
        }

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + boardSquares[currentPlayer]);
        System.out.println("The category is " + currentCategory(boardSquares[currentPlayer]));
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory(boardSquares[currentPlayer]).equals("Pop"))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory(boardSquares[currentPlayer]).equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory(boardSquares[currentPlayer]).equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory(boardSquares[currentPlayer]).equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }

    private String currentCategory(int boardSquareIndex) {
        List<String> categories = new ArrayList<>(Arrays.asList("Pop", "Science", "Sports", "Rock"));
        String category = null;
        for (int i = 0, j = 0; i <= boardSquareIndex; i++, j++) {
            category = categories.get(j);
            if (j == 3) {
                j = -1;
            }
        }
        return category;
    }

    public boolean wasCorrectlyAnswered() {
        boolean isNotWinner;
        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            System.out.println("Answer was correct!!!!");
            updatePlayerPurse();
            isNotWinner = !hasPlayerWon();
            switchToNextPlayer();
        } else {
            isNotWinner = true;
            switchToNextPlayer();
        }
        return isNotWinner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        switchToNextPlayer();
        return true;
    }

    private void updatePlayerPurse() {
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    private void switchToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean hasPlayerWon() {
        return (purses[currentPlayer] == 6);
    }
}
