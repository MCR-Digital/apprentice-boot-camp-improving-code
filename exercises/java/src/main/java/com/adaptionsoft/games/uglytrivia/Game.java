package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private ArrayList<String> players = new ArrayList<>();
    private int[] placeOnTheBoard = new int[6];
    private int[] coinPurses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    private int amountOfPlayers = players.size();
    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    private final int maxAmountOfQuestions = 50;

    private final int maximumPlaceOnTheBoard = 11;
    private final int startingPlaceOnTheBoard = 0;
    private final int startingAmountOfCoins = 0;

    public Game() {
        for (int index = 0; index < maxAmountOfQuestions; index++) {
            popQuestions.addLast(("Pop Question " + index));
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
        placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] + numberOnDiceAfterRoll;

        if (placeOnTheBoard[currentPlayer] > maximumPlaceOnTheBoard) {
            placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] - 12;
        }
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + placeOnTheBoard[currentPlayer]);
        System.out.println("The category is " + currentQuestionCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionCategory().equals("Pop"))
            System.out.println(popQuestions.removeFirst());
        if (currentQuestionCategory().equals("Science"))
            System.out.println(scienceQuestions.removeFirst());
        if (currentQuestionCategory().equals("Sports"))
            System.out.println(sportsQuestions.removeFirst());
        if (currentQuestionCategory().equals("Rock"))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentQuestionCategory() {
        boolean placeOnBoardIsMultipleOfFour = placeOnTheBoard[currentPlayer] % 4 == 0;
        boolean placeOnBoardIsMultipleOfTwo = placeOnTheBoard[currentPlayer] % 2 == 0;


        if (placeOnBoardIsMultipleOfFour) return "Pop";
        if (placeOnBoardIsScience()) return "Science";
        if (placeOnBoardIsMultipleOfTwo) return "Sports";
        return "Rock";
    }

    private boolean placeOnBoardIsScience() {
        if (placeOnTheBoard[currentPlayer] == 1) return true;
        if (placeOnTheBoard[currentPlayer] == 5) return true;
        if (placeOnTheBoard[currentPlayer] == 9) return true;
        return false;
    }

    public boolean questionWasAnsweredCorrectly() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                return correctQuestionAnswer();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }

        } else {

            System.out.println("Answer was corrent!!!!");
            return correctQuestionAnswer();
        }
    }

    private boolean correctQuestionAnswer() {
        coinPurses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + coinPurses[currentPlayer]
                + " Gold Coins.");

        boolean winner = winningPlayer();
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }

        return winner;
    }

    public boolean questionWasAnsweredWrong() {

        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }

    private boolean winningPlayer() {
        int winningAmountOfCoins = 6;
        return !(coinPurses[currentPlayer] == winningAmountOfCoins);
    }
}
