package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] positionOnBoard = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

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
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
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
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] + roll;
				if (positionOnBoard[currentPlayer] > 11) positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] - 12;

				System.out.println(players.get(currentPlayer)
						+ "'s new location is "
						+ positionOnBoard[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {

			positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] + roll;
			if (positionOnBoard[currentPlayer] > 11) positionOnBoard[currentPlayer] = positionOnBoard[currentPlayer] - 12;

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ positionOnBoard[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}

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
		if (positionOnBoard[currentPlayer] == 0) return "Pop";
		if (positionOnBoard[currentPlayer] == 4) return "Pop";
		if (positionOnBoard[currentPlayer] == 8) return "Pop";
		if (positionOnBoard[currentPlayer] == 1) return "Science";
		if (positionOnBoard[currentPlayer] == 5) return "Science";
		if (positionOnBoard[currentPlayer] == 9) return "Science";
		if (positionOnBoard[currentPlayer] == 2) return "Sports";
		if (positionOnBoard[currentPlayer] == 6) return "Sports";
		if (positionOnBoard[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean isCorrectAnswer() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean noWinner = isGameContinuing();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;

				return noWinner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean noWinner = isGameContinuing();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return noWinner;
		}
	}

	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean isGameContinuing() {
		return !(purses[currentPlayer] == 6);
	}
}