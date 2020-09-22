
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int NUMBER_OF_COINS_TO_WIN = 6;
    List<Player> playerList = new ArrayList<>();
    Questions questionType;
    Questions popQuestionList;
    Questions scienceQuestionList;
    Questions sportsQuestionList;
    Questions rockQuestionList;
    int currentPlayerIndex = 0;
    Player currentPlayer;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        popQuestionList = new Questions("Pop");
        sportsQuestionList = new Questions("Sports");
        scienceQuestionList = new Questions("Science");
        rockQuestionList = new Questions("Rock");

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
        System.out.println(questionType.questions.removeFirst());
    }


    private String currentCategory() {
        if (currentPlayer.positionOnBoard % 4 == 0) {
            questionType = popQuestionList;
            return popQuestionList.questionType;
        }
        if (currentPlayer.positionOnBoard % 4 == 1) {
            questionType = scienceQuestionList;
            return scienceQuestionList.questionType;
        }
        if (currentPlayer.positionOnBoard % 4 == 2) {
            questionType = sportsQuestionList;

            return sportsQuestionList.questionType;
        }
        questionType = rockQuestionList;
        return rockQuestionList.questionType;
    }

    public boolean isCorrectAnswer() {
        if (!currentPlayer.isPlayerInPenaltyBox) {
            System.out.println("Answer was corrent!!!!");
            return isPlayerWinner();
        }
        if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            return isPlayerWinner();
        }
        chooseNextPlayer();
        return true;

    }


    private boolean isPlayerWinner() {
        currentPlayer.coinPurse++;
        currentPlayer.getCoinPurse();
        boolean winner = didPlayerNotWin();
        chooseNextPlayer();

        return winner;
    }

    public boolean wrongAnswer() {
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
