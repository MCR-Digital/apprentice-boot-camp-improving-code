
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int NUMBER_OF_COINS_TO_WIN = 6;
    public static final int BOARD_SIZE = 12;

    List<Player> playerList = new ArrayList<>();
    private Questions currentQuestions;
    private Questions popQuestions;
    private Questions scienceQuestions;
    private Questions sportsQuestions;
    private Questions rockQuestions;
    private int currentPlayerIndex = 0;
   private Player currentPlayer;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        popQuestions = new Questions("Pop");
        sportsQuestions = new Questions("Sports");
        scienceQuestions = new Questions("Science");
        rockQuestions = new Questions("Rock");
    }


    public boolean addPlayer(String playerName) {
        Player newPlayer = new Player(playerName, playerList.size());
        playerList.add(newPlayer);
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + (newPlayer.getPlayerNumber() + 1));
        return true;
    }

    public void roll(int roll) {
        currentPlayer = playerList.get(currentPlayerIndex);
        System.out.println(currentPlayer.playerName + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isPlayerInPenaltyBox) {
            isCurrentPlayerGettingOutOfPenaltyBox(roll);
        } else {
            moveCurrentPlayerPosition(roll);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void isCurrentPlayerGettingOutOfPenaltyBox(int roll) {
        if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;
            System.out.println(currentPlayer.getPlayerName() + " is getting out of the penalty box");
            moveCurrentPlayerPosition(roll);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        } else {
            System.out.println(currentPlayer.getPlayerName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
        }
    }

    private void moveCurrentPlayerPosition(int roll) {
        currentPlayer.setPositionOnBoard(currentPlayer.getPositionOnBoard() + roll);
        if (currentPlayer.getPositionOnBoard() >= BOARD_SIZE)
            currentPlayer.setPositionOnBoard(currentPlayer.getPositionOnBoard() - BOARD_SIZE);

        System.out.println(currentPlayer.getPlayerName()
                + "'s new location is "
                + currentPlayer.getPositionOnBoard());
    }

    private void askQuestion() {
        System.out.println(currentQuestions.questions.removeFirst());
    }


    private String currentCategory() {
        if (currentPlayer.getPositionOnBoard() % 4 == 0) {
            currentQuestions = popQuestions;
            return popQuestions.getQuestionType();
        }
        if (currentPlayer.getPositionOnBoard() % 4 == 1) {
            currentQuestions = scienceQuestions;
            return scienceQuestions.getQuestionType();
        }
        if (currentPlayer.getPositionOnBoard() % 4 == 2) {
            currentQuestions = sportsQuestions;
            return sportsQuestions.getQuestionType();
        }
        currentQuestions = rockQuestions;
        return rockQuestions.getQuestionType();
    }

    public boolean isCorrectAnswer() {
        if (!currentPlayer.isPlayerInPenaltyBox) {
            System.out.println("Answer was corrent!!!!");
            return isGameContinuing();
        }
        if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            return isGameContinuing();
        }
        chooseNextPlayer();
        return true;

    }

    private boolean isGameContinuing() {
        currentPlayer.addCoinToPurse();
        currentPlayer.getCoinPurse();
        chooseNextPlayer();

        return didPlayerNotWin();
    }

    public boolean isIncorrectAnswer() {
        currentPlayer.setPlayerInPenaltyBox(true);
        chooseNextPlayer();
        return true;
    }

    private void chooseNextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == playerList.size()) currentPlayerIndex = 0;
    }


    private boolean didPlayerNotWin() {
        return !(currentPlayer.coinPurse == NUMBER_OF_COINS_TO_WIN);
    }
}
