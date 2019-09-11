package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.Board;
import com.adaptionsoft.games.QuestionCategories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Game {
    private ArrayList<String> playerNames = new ArrayList<>();
    private static final int MAX_AMOUNT_OF_PLAYERS = 6;
    private static final int MAX_NUMBER_OF_QUESTIONS = 50;
    private int[] locationOfPlayerOnBoard = new int[MAX_AMOUNT_OF_PLAYERS];
    private int[] playerPurses = new int[MAX_AMOUNT_OF_PLAYERS];
    private boolean[] isPlayerInPenaltyBox = new boolean[MAX_AMOUNT_OF_PLAYERS];
    public Board board;

    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;
    private String currentPlayerName;

    public Game(Board board) {
        for (int index = 0; index < MAX_NUMBER_OF_QUESTIONS; index++) {
            popQuestions.addLast("Pop Question " + index);
            scienceQuestions.addLast("Science Question " + index);
            sportsQuestions.addLast("Sports Question " + index);
            rockQuestions.addLast("Rock Question " + index);
        }
        this.board = board;
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
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory().equals(QuestionCategories.POP.getCategory()))
            System.out.println(popQuestions.removeFirst());
        if (currentCategory().equals(QuestionCategories.SCIENCE.getCategory()))
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory().equals(QuestionCategories.SPORTS.getCategory()))
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory().equals(QuestionCategories.ROCK.getCategory()))
            System.out.println(rockQuestions.removeFirst());
    }

    private String currentCategory() {
        return board.getCurrentCategory(locationOfPlayerOnBoard[currentPlayer]);
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
