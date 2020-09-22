
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int NUMBER_OF_COINS_TO_WIN = 6;
    List<Player> playerList = new ArrayList<>();
    Questions currentQuestions;
    Questions popQuestions;
    Questions scienceQuestions;
    Questions sportsQuestions;
    Questions rockQuestions;
    int currentPlayerIndex = 0;
    Player currentPlayer;
    boolean isGettingOutOfPenaltyBox;

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
        System.out.println("They are player number " + (newPlayer.playerNumber + 1));
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
        currentPlayer.positionOnBoard = currentPlayer.positionOnBoard + roll;
        if (currentPlayer.positionOnBoard > 11) currentPlayer.positionOnBoard = currentPlayer.positionOnBoard - 12;

        System.out.println(currentPlayer.playerName
                + "'s new location is "
                + currentPlayer.positionOnBoard);
    }

    private void askQuestion() {
        System.out.println(currentQuestions.questions.removeFirst());
    }


    private String currentCategory() {
        if (currentPlayer.positionOnBoard % 4 == 0) {
            currentQuestions = popQuestions;
            return popQuestions.questionType;
        }
        if (currentPlayer.positionOnBoard % 4 == 1) {
            currentQuestions = scienceQuestions;
            return scienceQuestions.questionType;
        }
        if (currentPlayer.positionOnBoard % 4 == 2) {
            currentQuestions = sportsQuestions;

            return sportsQuestions.questionType;
        }
        currentQuestions = rockQuestions;
        return rockQuestions.questionType;
    }

    public boolean isCorrectAnswer() {
        if (!currentPlayer.isPlayerInPenaltyBox) {
            System.out.println("Answer was corrent!!!!");
            return isPlayerNotWinner();
        }
        if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            return isPlayerNotWinner();
        }
        chooseNextPlayer();
        return true;

    }


    private boolean isPlayerNotWinner() {
        currentPlayer.coinPurse++;
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
