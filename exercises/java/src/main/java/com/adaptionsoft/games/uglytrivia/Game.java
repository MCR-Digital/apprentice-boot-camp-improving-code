package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.player.Player;
import com.adaptionsoft.games.question.Questions;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private final Questions questions = new Questions();
    private final ArrayList<Player> players = new ArrayList<>();
    private final boolean[] inPenaltyBox = new boolean[6];

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public boolean isPlayable() {
        return (numberOfPlayers() >= 2);
    }

    public boolean addPlayer(String playerName) {

        Player player = new Player(playerName);
        players.add(player);
        player.setPosition(0);
        player.setCollectedCoins(0);
        player.setInPenaltyBox(false);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public void rollDice(int roll) {
        System.out.println(getPlayerName(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (getPlayer(currentPlayer).isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(getPlayerName(currentPlayer) + " is getting out of the penalty box");
                changePlayerPositions(roll);
                askQuestion(currentCategory(getPlayer(currentPlayer).getPosition()));
            } else {
                System.out.println(getPlayerName(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            changePlayerPositions(roll);
            askQuestion(currentCategory(getPlayer(currentPlayer).getPosition()));
        }

    }

    private void changePlayerPositions(int roll) {
        getPlayer(currentPlayer).setPosition(getPlayer(currentPlayer).getPosition() + roll);
        if (getPlayer(currentPlayer).getPosition() > 11) {
            getPlayer(currentPlayer).setPosition(getPlayer(currentPlayer).getPosition() - 12);
        }

        System.out.println(getPlayerName(currentPlayer)
                + "'s new location is "
                + getPlayer(currentPlayer).getPosition());

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
        if (getPlayer(currentPlayer).isInPenaltyBox()) {
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
        getPlayer(currentPlayer).setCollectedCoins(getPlayer(currentPlayer).getCollectedCoins() + 1);
        System.out.println(getPlayerName(currentPlayer)
                + " now has "
                + getPlayer(currentPlayer).getCollectedCoins()
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
        System.out.println(getPlayerName(currentPlayer) + " was sent to the penalty box");
        getPlayer(currentPlayer).setInPenaltyBox(true);

        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
        return true;
    }

    private boolean didPlayerWin() {
        return !(getPlayer(currentPlayer).getCollectedCoins() == 6);
    }

    private String getPlayerName(int currentPlayer) {
        return getPlayer(currentPlayer).getName();
    }

    private Player getPlayer(int currentPlayer) {
        return players.get(currentPlayer);
    }
}
