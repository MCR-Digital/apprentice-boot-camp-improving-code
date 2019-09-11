package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";
    private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    private static final int MAX_NUMBER_OF_PLAYERS = 6;
    private static final int NUMBER_OF_QUESTIONS = 50;
    private static final int PLAYER_ONE_ID = 0;
    private static final int MAXIMUM_COINS = 6;

    private final String[] board = {POP, SCIENCE, SPORTS, ROCK, POP, SCIENCE, SPORTS, ROCK, POP, SCIENCE, SPORTS, ROCK};

    private ArrayList players = new ArrayList();
    private int[] playerPositions = new int[MAX_NUMBER_OF_PLAYERS];
    private int[] playerPurses = new int[MAX_NUMBER_OF_PLAYERS];
    private boolean[] playersInPenaltyBox = new boolean[MAX_NUMBER_OF_PLAYERS];

    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();

    private int currentPlayerID = PLAYER_ONE_ID;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int index = 0; index < NUMBER_OF_QUESTIONS; index++) {
            popQuestions.addLast("Pop Question " + index);
            scienceQuestions.addLast(("Science Question " + index));
            sportsQuestions.addLast(("Sports Question " + index));
            rockQuestions.addLast(createRockQuestion(index));
        }
    }

    private String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (getNumberOfPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
    }

    public boolean addPlayer(String playerName) {
        players.add(playerName);
        playerPositions[getNumberOfPlayers()] = 0;
        playerPurses[getNumberOfPlayers()] = 0;
        playersInPenaltyBox[getNumberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    private int getNumberOfPlayers() {
        return players.size();
    }

    public void processRoll(int rolledNumber) {
        System.out.println(players.get(currentPlayerID) + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);

        if (isPlayerInPenaltyBox(currentPlayerID)) {
            if (isOddNumber(rolledNumber)) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayerID) + " is getting out of the penalty box");
                movePlayer(rolledNumber);

                System.out.println("The category is " + getCategoryForPosition());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayerID) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            movePlayer(rolledNumber);
            System.out.println("The category is " + getCategoryForPosition());
            askQuestion();
        }

    }

    private void printPlayerPosition(int playerID) {
        System.out.println(players.get(playerID)
                + "'s new location is "
                + getPlayerPosition(playerID));
    }

    private boolean isOddNumber(int number) {
        return number % 2 != 0;
    }

    private void movePlayer(int rolledNumber) {
        playerPositions[currentPlayerID] = getPlayerPosition(currentPlayerID) + rolledNumber;
        if (hasPlayerPassedFinalTile(currentPlayerID)) {
            continueMoveFromStartOfBoard();
        }

        printPlayerPosition(currentPlayerID);
    }

    private void continueMoveFromStartOfBoard() {
        playerPositions[currentPlayerID] = getPlayerPosition(currentPlayerID) - 12;
    }

    private boolean hasPlayerPassedFinalTile(int playerID) {
        return getPlayerPosition(playerID) > 11;
    }

    private void askQuestion() {
        if (getCategoryForPosition().equals(POP))
            System.out.println(popQuestions.removeFirst());
        if (getCategoryForPosition().equals(SCIENCE))
            System.out.println(scienceQuestions.removeFirst());
        if (getCategoryForPosition().equals(SPORTS))
            System.out.println(sportsQuestions.removeFirst());
        if (getCategoryForPosition().equals(ROCK))
            System.out.println(rockQuestions.removeFirst());
    }

    private String getCategoryForPosition() {
        return board[getPlayerPosition(currentPlayerID)];
    }

    private int getPlayerPosition(int playerID) {
        return playerPositions[playerID];
    }

    public boolean handleCorrectAnswer() {
        if (isPlayerInPenaltyBox(currentPlayerID)) {

            if (isGettingOutOfPenaltyBox) {
                boolean winner = addCoin(currentPlayerID);
                switchToNextPlayer();

                return winner;
            } else {
                switchToNextPlayer();
                return true;
            }

        } else {
            boolean winner = addCoin(currentPlayerID);
            switchToNextPlayer();

            return winner;
        }
    }

    private void switchToNextPlayer() {
        currentPlayerID++;
        if (isLastPlayerInList()) resetBackToPlayerOne();
    }

    private boolean isPlayerInPenaltyBox(int playerID) {
        return playersInPenaltyBox[playerID];
    }

    private void resetBackToPlayerOne() {
        currentPlayerID = PLAYER_ONE_ID;
    }

    private boolean addCoin(int playerID) {
        System.out.println("Answer was correct!!!!");
        playerPurses[currentPlayerID]++;
        System.out.println(players.get(playerID)
                + " now has "
                + getPlayerPurse(playerID)
                + " Gold Coins.");

        return playerDoesNotHaveSixCoins(currentPlayerID);
    }

    private int getPlayerPurse(int playerID) {
        return playerPurses[playerID];
    }

    public boolean handleIncorrectAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayerID) + " was sent to the penalty box");
        playersInPenaltyBox[currentPlayerID] = true;

        switchToNextPlayer();
        return true;
    }

    private boolean isLastPlayerInList() {
        return currentPlayerID == players.size();
    }

    private boolean playerDoesNotHaveSixCoins(int playerID) {
        return (getPlayerPurse(playerID) != MAXIMUM_COINS);
    }
}
