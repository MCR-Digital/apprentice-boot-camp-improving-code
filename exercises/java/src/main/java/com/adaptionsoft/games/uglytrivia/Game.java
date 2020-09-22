
package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	public static final int NUMBER_OF_COINS_TO_WIN = 6;
	ArrayList players = new ArrayList();
	int[] positionOnBoard = new int[6];
	int[] purses  = new int[6];
	boolean[] inPenaltyBox  = new boolean[6];
	List<Player> playerList = new ArrayList<>();



	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	int currentPlayer = 0;
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

	public boolean addPlayer(String playerName) {


		players.add(playerName);
		positionOnBoard[howManyPlayers()] = 0;
		purses[howManyPlayers()] = 0;
		inPenaltyBox[howManyPlayers()] = false;

		System.out.println(playerName + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
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

			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			moveCurrentPlayerPosition(roll);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		} else {
			System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private void moveCurrentPlayerPosition(int roll) {
		positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] + roll;
		if (positionOnBoard[currentPlayer] > 11) positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ positionOnBoard[currentPlayer]);
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
		if (positionOnBoard[currentPlayer]%4 == 0) return "Pop";
		if (positionOnBoard[currentPlayer]%4 == 1) return "Science";
		if (positionOnBoard[currentPlayer]%4 == 2) return "Sports";

		return "Rock";
	}

	public boolean isCorrectAnswer() {
		if (inPenaltyBox[currentPlayer]){
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
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerNotWin();
		chooseNextPlayer();

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		chooseNextPlayer();
		return true;
	}

	private void chooseNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}


	private boolean didPlayerNotWin() {
		return !(purses[currentPlayer] == NUMBER_OF_COINS_TO_WIN);
	}
}
