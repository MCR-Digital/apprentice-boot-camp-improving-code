package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int TOTAL_NUMBER_OF_BOARD_SQUARES = 12;
	public static final int SCORE_TO_WIN = 6;

	private GameBoard gameBoard;
	private ArrayList<Player> players = new ArrayList();
	private int[] positionsOnBoard = new int[6];
	private int[] score = new int[6];
	private boolean[] inPenaltyBox = new boolean[6];

	private LinkedList popQuestions = new LinkedList();
	private LinkedList scienceQuestions = new LinkedList();
	private LinkedList sportsQuestions = new LinkedList();
	private LinkedList rockQuestions = new LinkedList();

	private int currentPlayer = 0;
	private boolean isGettingOutOfPenaltyBox;

	public Game() {
		gameBoard = new GameBoard();
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
	}

	public boolean addPlayer(String playerName) {
		players.add(new Player(playerName));
		positionsOnBoard[numberOfPlayers()] = 0;
		score[numberOfPlayers()] = 0;
		inPenaltyBox[numberOfPlayers()] = false;

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	public int numberOfPlayers() {
		return players.size();
	}

	public void playerTurn(int diceRoll) {
		System.out.println(players.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + diceRoll);

		if (inPenaltyBox[currentPlayer]) {
			if (isOdd(diceRoll)) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");

				movePlayer(diceRoll);
				displayPlayerPosition();
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {
			movePlayer(diceRoll);
			displayPlayerPosition();
			askQuestion();
		}
	}

	private boolean isOdd(int diceRoll) {
		return diceRoll % 2 != 0;
	}

	private void displayPlayerPosition() {
		System.out.println(players.get(currentPlayer).getName()
				+ "'s new location is "
				+ positionsOnBoard[currentPlayer]);
	}

	private void movePlayer(int diceRoll) {
		positionsOnBoard[currentPlayer] = positionsOnBoard[currentPlayer] + diceRoll;
		if (positionsOnBoard[currentPlayer] > 11) {
			returnToBeginningOfBoard();
		}
	}

	private void returnToBeginningOfBoard() {
		positionsOnBoard[currentPlayer] = positionsOnBoard[currentPlayer] - TOTAL_NUMBER_OF_BOARD_SQUARES;
	}

	private void askQuestion() {
		System.out.println("The category is " + currentCategory());
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.removeFirst());
		if (currentCategory().equals("Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory().equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory().equals("Rock"))
			System.out.println(rockQuestions.removeFirst());
	}

	private String currentCategory() {
		if (positionsOnBoard[currentPlayer] == 0) return "Pop";
		if (positionsOnBoard[currentPlayer] == 4) return "Pop";
		if (positionsOnBoard[currentPlayer] == 8) return "Pop";
		if (positionsOnBoard[currentPlayer] == 1) return "Science";
		if (positionsOnBoard[currentPlayer] == 5) return "Science";
		if (positionsOnBoard[currentPlayer] == 9) return "Science";
		if (positionsOnBoard[currentPlayer] == 2) return "Sports";
		if (positionsOnBoard[currentPlayer] == 6) return "Sports";
		if (positionsOnBoard[currentPlayer] == 10) return "Sports";
		return "Rock";
	}


	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return isWinnerOnEndOfTurn();
			} else {
				currentPlayer++;
				setNextPlayer();
				return true;
			}
		} else {
			System.out.println("Answer was corrent!!!!");
			return isWinnerOnEndOfTurn();
		}
	}

	private boolean isWinnerOnEndOfTurn() {
		updateScore();
		boolean winner = isWinner();
		currentPlayer++;

		setNextPlayer();
		return winner;
	}

	private void updateScore() {
		score[currentPlayer]++;
		System.out.println(players.get(currentPlayer).getName()
				+ " now has "
				+ score[currentPlayer]
				+ " Gold Coins.");
	}

	private void setNextPlayer() {
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	public boolean isWrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		setNextPlayer();
		return true;
	}

	private boolean isWinner() {
		return !(score[currentPlayer] == SCORE_TO_WIN);
	}
}