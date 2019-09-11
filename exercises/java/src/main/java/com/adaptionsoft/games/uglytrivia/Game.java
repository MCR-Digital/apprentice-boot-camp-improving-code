package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class Game {
    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    private List<String> players = new ArrayList<>();
    private int[] boardSquares = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    private QuestionDeck questionDeck;


    public Game(QuestionDeck questionDeck) {
        this.questionDeck = questionDeck;
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
            askQuestion();
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
    }

    private void askQuestion() {
        String currentCategory = currentCategory(boardSquares[currentPlayer]);
        Question question = questionDeck.getQuestion(currentCategory);
        System.out.println(question.getCategory() + " Question " + question.getQuestionNumber());
        question.removeQuestionFromDeck();
    }

    private String currentCategory(int boardSquareIndex) {
        List<String> categoryName = new ArrayList<>(Arrays.asList(POP, SCIENCE, SPORTS, ROCK));
        return categoryName.get(boardSquareIndex % categoryName.size());
    }

    public boolean wasCorrectlyAnswered() {
        boolean isNotWinner = true;
        if (isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]) {
            System.out.println("Answer was correct!!!!");
            updatePlayerPurse();
            isNotWinner = !hasPlayerWon();
            switchToNextPlayer();
        } else {
            switchToNextPlayer();
        }
        return isNotWinner;
    }

    private void updatePlayerPurse() {
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    private boolean hasPlayerWon() {
        return purses[currentPlayer] == 6;
    }

    private void switchToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        switchToNextPlayer();
        return true;
    }
}
