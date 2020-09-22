package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.question.Questions;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private final Questions questions = new Questions();

    private final ArrayList<String> players = new ArrayList<>();
    private final int[] playerPositions = new int[6];
    private final int[] playerCollectedCoins = new int[6];
    private final boolean[] inPenaltyBox = new boolean[6];

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public boolean isPlayable() {
        return (numberOfPlayers() >= 2);
    }

    public boolean addPlayer(String playerName) {

        players.add(playerName);
        playerPositions[numberOfPlayers()] = 0;
        playerCollectedCoins[numberOfPlayers()] = 0;
        inPenaltyBox[numberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public void rollDice(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                changePlayerPositions(roll, currentPlayer);
                askQuestion(currentCategory(playerPositions[currentPlayer]));
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            changePlayerPositions(roll, currentPlayer);
            askQuestion(currentCategory(playerPositions[currentPlayer]));
        }

    }

    private void changePlayerPositions(int roll, int currentPlayer) {
        playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
        if (playerPositions[currentPlayer] > 11) {
            playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12;
        }

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + playerPositions[currentPlayer]);

    }

    private void askQuestion(String category) {
        System.out.println("The category is " + category);
        if (category.equals("Pop")) {
            System.out.println(questions.getPopQuestions().removeFirst());
        }
        if (category.equals("Science")) {
            System.out.println(questions.getScienceQuestions().removeFirst());
        }
        if (category.equals("Sports")) {
            System.out.println(questions.getSportsQuestions().removeFirst());
        }
        if (category.equals("Rock")) {
            System.out.println(questions.getRockQuestions().removeFirst());
        }

    }

    private String currentCategory(int playerPosition) {

        String category = "Rock";

        if (playerPosition == 0 || playerPosition == 4
                || playerPosition == 8) {
            category = "Pop";
        } else if (playerPosition == 1 || playerPosition == 5
                || playerPosition == 9) {
            category = "Science";
        } else if (playerPosition == 2 || playerPosition == 6
                || playerPosition == 10) {
            category = "Sports";
        }

        return category;

    }

    public boolean correctAnswer() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                giveCoinToPlayer();
                return isAWinner();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) {
                    currentPlayer = 0;
                }
                return true;
            }

        } else {

            System.out.println("Answer was corrent!!!!");
            giveCoinToPlayer();
            return isAWinner();
        }
    }

    private void giveCoinToPlayer() {
        playerCollectedCoins[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + playerCollectedCoins[currentPlayer]
                + " Gold Coins.");
    }

    private boolean isAWinner() {

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
        return true;
    }

    private boolean didPlayerWin() {
        return !(playerCollectedCoins[currentPlayer] == 6);
    }
}
