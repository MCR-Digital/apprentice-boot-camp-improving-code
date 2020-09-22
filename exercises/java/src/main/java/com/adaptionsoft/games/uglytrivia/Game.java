
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

	public int howManyPlayers() {
		return playerList.size();
	}

	public void roll(int roll) {
		System.out.println(playerList.get(currentPlayerIndex).playerName + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (playerList.get(currentPlayerIndex).isPlayerInPenaltyBox) {
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

			System.out.println(playerList.get(currentPlayerIndex).playerName + " is getting out of the penalty box");
			moveCurrentPlayerPosition(roll);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		} else {
			System.out.println(playerList.get(currentPlayerIndex).playerName + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private void moveCurrentPlayerPosition(int roll) {
		playerList.get(currentPlayerIndex).positionOnBoard = playerList.get(currentPlayerIndex).positionOnBoard + roll;
		if (playerList.get(currentPlayerIndex).positionOnBoard  > 11) playerList.get(currentPlayerIndex).positionOnBoard  = playerList.get(currentPlayerIndex).positionOnBoard  - 12;

		System.out.println(playerList.get(currentPlayerIndex).playerName
				+ "'s new location is "
				+ playerList.get(currentPlayerIndex).positionOnBoard );
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
		if (playerList.get(currentPlayerIndex).positionOnBoard %4 == 0) return "Pop";
		if (playerList.get(currentPlayerIndex).positionOnBoard %4 == 1) return "Science";
		if (playerList.get(currentPlayerIndex).positionOnBoard %4 == 2) return "Sports";

		return "Rock";
	}

	public boolean isCorrectAnswer() {
		if (playerList.get(currentPlayerIndex).isPlayerInPenaltyBox ){
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
		playerList.get(currentPlayerIndex).coinPurse++;
		System.out.println(playerList.get(currentPlayerIndex).playerName
				+ " now has "
				+ playerList.get(currentPlayerIndex).coinPurse
				+ " Gold Coins.");

		boolean winner = didPlayerNotWin();
		chooseNextPlayer();

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(playerList.get(currentPlayerIndex).playerName+ " was sent to the penalty box");
		playerList.get(currentPlayerIndex).isPlayerInPenaltyBox = true;

		chooseNextPlayer();
		return true;
	}

	private void chooseNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == playerList.size()) currentPlayerIndex = 0;
	}


	private boolean didPlayerNotWin() {
		return !(playerList.get(currentPlayerIndex).coinPurse == NUMBER_OF_COINS_TO_WIN);
	}
}
