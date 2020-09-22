package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.question.Questions;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    ArrayList<String> players = new ArrayList<>();
    int[] playerPositions = new int[6];
    int[] playerCollectedCoins = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    Questions questions = new Questions();


    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            questions.getPopQuestions().addLast("Pop Question " + i);
            questions.getScienceQuestions().addLast(("Science Question " + i));
            questions.getSportsQuestions().addLast(("Sports Question " + i));
            questions.getRockQuestions().addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

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
                changePlayerPositions(roll);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            changePlayerPositions(roll);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void changePlayerPositions(int roll) {
        playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
        if (playerPositions[currentPlayer] > 11) {
            playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12;
        }

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + playerPositions[currentPlayer]);

    }

    private void askQuestion() {
        if (currentCategory().equals("Pop")) {
            System.out.println(questions.getPopQuestions().removeFirst());
        }
        if (currentCategory().equals("Science")) {
            System.out.println(questions.getScienceQuestions().removeFirst());
        }
        if (currentCategory().equals("Sports")) {
            System.out.println(questions.getSportsQuestions().removeFirst());
        }
        if (currentCategory().equals("Rock")) {
            System.out.println(questions.getRockQuestions().removeFirst());
        }
    }

    private String currentCategory() {

        String category = "Rock";

        int currentPlayerPosition = playerPositions[currentPlayer];
        if (currentPlayerPosition == 0 || currentPlayerPosition == 4
                || currentPlayerPosition == 8) {
            category = "Pop";
        } else if (currentPlayerPosition == 1 || currentPlayerPosition == 5
                || currentPlayerPosition == 9) {
            category = "Science";
        } else if (currentPlayerPosition == 2 || currentPlayerPosition == 6
                || currentPlayerPosition == 10) {
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
