package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private int currentPlayer = 0;
    private QuestionDeck questionDeck;
    private List<Player> playerList;

    public Game(QuestionDeck questionDeck, Player... players) {
        this.questionDeck = questionDeck;
        this.playerList = new ArrayList<>(Arrays.asList(players));
    }

    public void rollDice(int rollValue) {
        Player player = getPlayer();
        System.out.println(player.getName() + " is the current player");
        System.out.println("They have rolled a " + rollValue);
        player.adjustPenaltyBoxStatus(rollValue);

        if (player.playerIsMovable()) {
            player.movePlayerForward(rollValue);
            System.out.println("The category is " + questionDeck.getCurrentCategory(player.getBoardPosition()));
            askQuestion(player.getBoardPosition());
        }
    }

    private Player getPlayer() {
        return playerList.get(currentPlayer);
    }

    private void askQuestion(int boardSquareIndex) {
        String currentCategory = questionDeck.getCurrentCategory(boardSquareIndex);
        Category category = questionDeck.getQuestion(currentCategory);
        System.out.println(category.getCategoryName() + " Question " + category.getQuestionNumber());
        category.removeQuestionFromDeck();
    }

    public boolean wasCorrectlyAnswered() {
        Player player = playerList.get(currentPlayer);
        boolean isNotWinner = true;
        if (player.playerIsMovable()) {
            System.out.println("Answer was correct!!!!");
            player.updatePoints();
            isNotWinner = !player.checkIfPlayerHasWon();
            switchToNextPlayer();
        } else {
            switchToNextPlayer();
        }
        return isNotWinner;
    }

    private void switchToNextPlayer() {
        currentPlayer++;
        currentPlayer = currentPlayer % playerList.size();
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        Player player = playerList.get(currentPlayer);
        System.out.println(player.getName() + " was sent to the penalty box");
        player.putInPenaltyBox();

        switchToNextPlayer();
        return true;
    }
}
