package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList playerNames = new ArrayList();
    int[] placeOnBoard = new int[6];
    int[] playerPurses = new int[6];
    boolean[] isPlayerInPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int index = 0; index < 50; index++) {
            popQuestions.addLast("Pop Question " + index);
            scienceQuestions.addLast(("Science Question " + index));
            sportsQuestions.addLast(("Sports Question " + index));
            rockQuestions.addLast(createRockQuestion(index));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (numberOfPlayers() >= 2);
    }

    public boolean addPlayerToGame(String playerName) {


        playerNames.add(playerName);
        placeOnBoard[numberOfPlayers()] = 0;
        playerPurses[numberOfPlayers()] = 0;
        isPlayerInPenaltyBox[numberOfPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + playerNames.size());
        return true;
    }

    public int numberOfPlayers() {
        return playerNames.size();
    }

    public void takeTurn(int roll) {
		Object currentPlayerName = playerNames.get(currentPlayer);
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
		int newPlaceOnBoard = placeOnBoard[currentPlayer] + roll;
		placeOnBoard[currentPlayer] = newPlaceOnBoard;

		if (newPlaceOnBoard > 11) {
			newPlaceOnBoard -= 12;
			placeOnBoard[currentPlayer] = newPlaceOnBoard;
		}
        System.out.println(currentPlayerName + "'s new location is " + placeOnBoard[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
        if (currentCategory() == "Pop")
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
		int placeOnBoardOfCurrentPlayer = placeOnBoard[currentPlayer];
		if (placeOnBoardOfCurrentPlayer == 0) return "Pop";
        if (placeOnBoardOfCurrentPlayer == 4) return "Pop";
        if (placeOnBoardOfCurrentPlayer == 8) return "Pop";
        if (placeOnBoardOfCurrentPlayer == 1) return "Science";
        if (placeOnBoardOfCurrentPlayer == 5) return "Science";
        if (placeOnBoardOfCurrentPlayer == 9) return "Science";
        if (placeOnBoardOfCurrentPlayer == 2) return "Sports";
        if (placeOnBoardOfCurrentPlayer == 6) return "Sports";
        if (placeOnBoardOfCurrentPlayer == 10) return "Sports";
        return "Rock";
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
        System.out.println(playerNames.get(currentPlayer)
                + " now has "
                + playerPurses[currentPlayer]
                + " Gold Coins.");

        boolean winner = hasPlayerWon();
        currentPlayer++;
        if (currentPlayer == playerNames.size()) currentPlayer = 0;

        return winner;
    }

    public boolean isWrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerNames.get(currentPlayer) + " was sent to the penalty box");
        isPlayerInPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == playerNames.size()) currentPlayer = 0;
        return true;
    }


    private boolean hasPlayerWon() {
        return !(playerPurses[currentPlayer] == 6);
    }
}
