package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.categories.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Game {
    private Map<Integer, Category> boardPositions;
    private ArrayList<String> playerNames = new ArrayList<>();
    private static final int MAX_AMOUNT_OF_PLAYERS = 6;
    private int[] locationOfPlayerOnBoard = new int[MAX_AMOUNT_OF_PLAYERS];
    private int[] playerPurses = new int[MAX_AMOUNT_OF_PLAYERS];
    private boolean[] isPlayerInPenaltyBox = new boolean[MAX_AMOUNT_OF_PLAYERS];

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    private String currentPlayerName;

    public Game(Map<Integer, Category> boardPositions) {
        this.boardPositions = boardPositions;
    }

    public void addPlayersToGame(String... players) {
        Arrays.stream(players).forEach(playerName -> {
            playerNames.add(playerName);
            locationOfPlayerOnBoard[numberOfPlayers()] = 0;
            playerPurses[numberOfPlayers()] = 0;
            isPlayerInPenaltyBox[numberOfPlayers()] = false;
            System.out.println(playerName + " was added");
            System.out.println("They are player number " + playerNames.size());
        });
    }

    public int numberOfPlayers() {
        return playerNames.size();
    }

    public void takeTurn(int roll) {
        currentPlayerName = playerNames.get(currentPlayer);
        System.out.println(currentPlayerName + " is the current player");
        System.out.println("They have rolled a " + roll);

        boolean isCurrentPlayerInPenaltyBox = this.isPlayerInPenaltyBox[currentPlayer];
        boolean isRollOddNumber = roll % 2 != 0;

        if (isCurrentPlayerInPenaltyBox) {

            if (isRollOddNumber) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(currentPlayerName + " is getting out of the penalty box");
                movePlayerAroundTheBoardAndAskQuestion(roll, currentPlayerName);

            } else {
                System.out.println(currentPlayerName + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            movePlayerAroundTheBoardAndAskQuestion(roll, currentPlayerName);
        }
    }

    private void movePlayerAroundTheBoardAndAskQuestion(int roll, Object currentPlayerName) {
        int newPlaceOnBoard = locationOfPlayerOnBoard[currentPlayer] + roll;
        locationOfPlayerOnBoard[currentPlayer] = newPlaceOnBoard;

        if (newPlaceOnBoard > 11) {
            newPlaceOnBoard -= 12;
            locationOfPlayerOnBoard[currentPlayer] = newPlaceOnBoard;
        }
        System.out.println(currentPlayerName + "'s new location is " + locationOfPlayerOnBoard[currentPlayer]);
        Category category = currentCategory();
        System.out.println("The category is " + category.getCategory());
        askQuestion(category);
    }

    private void askQuestion(Category category) {
        System.out.println(category.getQuestions().removeFirst());
    }


    private Category currentCategory() {
        int placeOnBoardOfCurrentPlayer = locationOfPlayerOnBoard[currentPlayer] + 1;
        return boardPositions.get(placeOnBoardOfCurrentPlayer);
    }

    public boolean isCorrectAnswer() {
        if (isPlayerInPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                return addGoldCoinToPlayerPurseAndCheckIfHasWon();
            } else {
                currentPlayer++;
                if (currentPlayer == playerNames.size()) currentPlayer = 0;
                return true;
            }

        } else {

            System.out.println("Answer was corrent!!!!");
            return addGoldCoinToPlayerPurseAndCheckIfHasWon();
        }
    }

    private boolean addGoldCoinToPlayerPurseAndCheckIfHasWon() {
        playerPurses[currentPlayer]++;
        System.out.println(currentPlayerName + " now has " + playerPurses[currentPlayer] + " Gold Coins.");

        boolean winner = hasPlayerWon();

        currentPlayer++;
        if (currentPlayer == playerNames.size()) {
            currentPlayer = 0;
        }
        return winner;
    }

    public boolean isWrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayerName + " was sent to the penalty box");
        isPlayerInPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == playerNames.size()) {
            currentPlayer = 0;
        }
        return true;
    }

    private boolean hasPlayerWon() {
        return !(playerPurses[currentPlayer] == 6);
    }
}
