
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	public static final int NUMBER_OF_COINS_TO_WIN = 6;
	List<Player> playerList = new ArrayList<>();


	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	int currentPlayerIndex = 0;
	Player currentPlayer;
	boolean isGettingOutOfPenaltyBox;

	public  Game(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createQuestion("Rock Question", i));
		}
	}

	public String createQuestion(String questionType, int index){
		return questionType + " " + index;
	}


	public boolean addPlayer(String playerName){
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

			System.out.println(currentPlayer.playerName + " is getting out of the penalty box");
			moveCurrentPlayerPosition(roll);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		} else {
			System.out.println(currentPlayer.playerName + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private void moveCurrentPlayerPosition(int roll) {
		currentPlayer.positionOnBoard = currentPlayer.positionOnBoard + roll;
		if (currentPlayer.positionOnBoard  > 11) currentPlayer.positionOnBoard  = currentPlayer.positionOnBoard  - 12;

		System.out.println(currentPlayer.playerName
				+ "'s new location is "
				+ currentPlayer.positionOnBoard );
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
		if (currentPlayer.positionOnBoard %4 == 0) return "Pop";
		if (currentPlayer.positionOnBoard %4 == 1) return "Science";
		if (currentPlayer.positionOnBoard %4 == 2) return "Sports";

		return "Rock";
	}

	public boolean isCorrectAnswer() {
		if (currentPlayer.isPlayerInPenaltyBox ){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return isPlayerWinner();
			} else {
				chooseNextPlayer();
				return true;
			}

		} else {

			System.out.println("Answer was corrent!!!!");
			return isPlayerWinner();
		}

	}

	private boolean isPlayerWinner() {
		currentPlayer.coinPurse++;
currentPlayer.getCoinPurse();

		boolean winner = didPlayerNotWin();
		chooseNextPlayer();

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.playerName+ " was sent to the penalty box");
		currentPlayer.isPlayerInPenaltyBox = true;

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
