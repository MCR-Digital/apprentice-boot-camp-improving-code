package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] placeOnTheBoard = new int[6];
    int[] coinPurses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int index = 0; index < 50; index++) {
            popQuestions.addLast("Pop Question " + index);
            scienceQuestions.addLast(("Science Question " + index));
            sportsQuestions.addLast(("Sports Question " + index));
            rockQuestions.addLast(("Rock Question " + index));
        }
    }

    public boolean addingPlayer(String playerName) {

        players.add(playerName);
        placeOnTheBoard[amountOfPlayers()] = 0;
        coinPurses[amountOfPlayers()] = 0;
        inPenaltyBox[amountOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int amountOfPlayers() {
        return players.size();
    }

    public void rollTheDice(int numberOnDiceAfterRoll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + numberOnDiceAfterRoll);

        if (inPenaltyBox[currentPlayer]) {
            if (numberOnDiceAfterRoll % 2 != 0) {
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
        if (placeOnTheBoard[currentPlayer] > 11) placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] - 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + placeOnTheBoard[currentPlayer]);
        System.out.println("The category is " + currentQuestionCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentQuestionCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentQuestionCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentQuestionCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentQuestionCategory() {
        if (placeOnTheBoard[currentPlayer] % 4 == 0) return "Pop";
        if (placeOnTheBoard[currentPlayer] == 1) return "Science";
        if (placeOnTheBoard[currentPlayer] == 5) return "Science";
        if (placeOnTheBoard[currentPlayer] == 9) return "Science";
        if (placeOnTheBoard[currentPlayer] % 2 == 0) return "Sports";
        return "Rock";
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
        if (currentPlayer == players.size()) currentPlayer = 0;

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
        return !(coinPurses[currentPlayer] == 6);
    }
}
