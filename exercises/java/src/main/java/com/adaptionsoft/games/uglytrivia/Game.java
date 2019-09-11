package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private ArrayList<String> players = new ArrayList<>();
    Category pop = new Category("Pop");
    private int[] placeOnTheBoard = new int[6];
    private int[] coinPurses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    private int amountOfPlayers = players.size();
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    private static final int maxAmountOfQuestions = 50;
    private static final int maximumPlaceOnTheBoard = 11;
    private static final int startingPlaceOnTheBoard = 0;
    private static final int startingAmountOfCoins = 0;

    public Game() {
        for (int index = 0; index < maxAmountOfQuestions; index++) {
            scienceQuestions.addLast(("Science Question " + index));
            sportsQuestions.addLast(("Sports Question " + index));
            rockQuestions.addLast(("Rock Question " + index));
        }
    }

    public void addingPlayer(String playerName) {

        players.add(playerName);
        setUpForEachPlayer();
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private void setUpForEachPlayer() {
        placeOnTheBoard[amountOfPlayers] = startingPlaceOnTheBoard;
        coinPurses[amountOfPlayers] = startingAmountOfCoins;
        inPenaltyBox[amountOfPlayers] = false;
    }


    public void rollTheDice(int numberOnDiceAfterRoll) {
        boolean oddNumberRolled = numberOnDiceAfterRoll % 2 != 0;
        boolean currentPlayerIsInPenaltyBox = this.inPenaltyBox[currentPlayer];

        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + numberOnDiceAfterRoll);

        if (currentPlayerIsInPenaltyBox) {
            if (oddNumberRolled) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                outcomeOfCorrectAnswer(numberOnDiceAfterRoll);
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            outcomeOfCorrectAnswer(numberOnDiceAfterRoll);
        }
    }

    private void outcomeOfCorrectAnswer(int numberOnDiceAfterRoll) {
        int oneMoreThanTheMaximumPlaceOnBoard = 12;

        placeOnTheBoard[currentPlayer] = playersPlaceOnBoard() + numberOnDiceAfterRoll;

        if (playersPlaceOnBoard() > maximumPlaceOnTheBoard) {
            placeOnTheBoard[currentPlayer] = playersPlaceOnBoard() - oneMoreThanTheMaximumPlaceOnBoard;
        }
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + playersPlaceOnBoard());
        System.out.println("The category is " + currentQuestionCategory());
        askQuestion();
    }

    private int playersPlaceOnBoard() {
        return placeOnTheBoard[currentPlayer];
    }

    private void askQuestion() {
        if (currentQuestionCategory().equals("Pop"))
            System.out.println(pop.getNextQuestion());
        if (currentQuestionCategory().equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentQuestionCategory().equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentQuestionCategory().equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentQuestionCategory() {
        boolean placeOnBoardIsMultipleOfFour = playersPlaceOnBoard() % 4 == 0;
        boolean placeOnBoardIsMultipleOfTwo = playersPlaceOnBoard() % 2 == 0;

        if (placeOnBoardIsMultipleOfFour) return "Pop";
        if (playersPlaceOnBoard() == 1 || playersPlaceOnBoard() == 5 || playersPlaceOnBoard() == 9) return "Science";
        if (placeOnBoardIsMultipleOfTwo) return "Sports";
        return "Rock";
    }

    public boolean questionWasAnsweredCorrectly() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                return correctQuestionAnswer();
            } else {
                loopToSeeWhosNext();
                return true;
            }

        } else {
            System.out.println("Answer was corrent!!!!");
            return correctQuestionAnswer();
        }
    }

    private void loopToSeeWhosNext() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private boolean correctQuestionAnswer() {
        coinPurses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + coinPurses[currentPlayer]
                + " Gold Coins.");

        boolean winner = winningPlayer();
        loopToSeeWhosNext();

        return winner;
    }

    public boolean questionWasAnsweredWrong() {

        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        loopToSeeWhosNext();
        return true;
    }

    private boolean winningPlayer() {
        int winningAmountOfCoins = 6;
        return (coinPurses[currentPlayer] != winningAmountOfCoins);
    }
}