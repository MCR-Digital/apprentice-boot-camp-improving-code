package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList<String> players = new ArrayList<>();
    int[] playerPosition = new int[6];
    int[] collectedCoins = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

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
		return (numberOfPlayers() >= 2);
	}

	public boolean add(String playerName) {


	    players.add(playerName);
	    playerPosition[numberOfPlayers()] = 0;
	    collectedCoins[numberOfPlayers()] = 0;
	    inPenaltyBox[numberOfPlayers()] = false;

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	public int numberOfPlayers() {
		return players.size();
	}

	public void rollDice(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				playerPosition[currentPlayer] = playerPosition[currentPlayer] + roll;
				if (playerPosition[currentPlayer] > 11) playerPosition[currentPlayer] = playerPosition[currentPlayer] - 12;

				System.out.println(players.get(currentPlayer)
						+ "'s new location is "
						+ playerPosition[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {

			playerPosition[currentPlayer] = playerPosition[currentPlayer] + roll;
			if (playerPosition[currentPlayer] > 11) playerPosition[currentPlayer] = playerPosition[currentPlayer] - 12;

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ playerPosition[currentPlayer]);
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
		if (playerPosition[currentPlayer] == 0) return "Pop";
		if (playerPosition[currentPlayer] == 4) return "Pop";
		if (playerPosition[currentPlayer] == 8) return "Pop";
		if (playerPosition[currentPlayer] == 1) return "Science";
		if (playerPosition[currentPlayer] == 5) return "Science";
		if (playerPosition[currentPlayer] == 9) return "Science";
		if (playerPosition[currentPlayer] == 2) return "Sports";
		if (playerPosition[currentPlayer] == 6) return "Sports";
		if (playerPosition[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean correctAnswer() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				collectedCoins[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ collectedCoins[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
			collectedCoins[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ collectedCoins[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(collectedCoins[currentPlayer] == 6);
	}
}
