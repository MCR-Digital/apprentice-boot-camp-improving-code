package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final String POP_CATEGORY = "Pop";
	public static final String SCIENCE_CATEGORY = "Science";
	public static final String SPORTS_CATEGORY = "Sports";
	public static final String ROCK_CATEGORY = "Rock";
	ArrayList players = new ArrayList();
    int[] playerPositions = new int[6];
    int[] playerPurses = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(){
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
	    playerPositions[howManyPlayers()] = 0;
	    playerPurses[howManyPlayers()] = 0;
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
				moveCurrentPlayer(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {

			moveCurrentPlayer(roll);
		}

	}

	private void moveCurrentPlayer(int roll) {
		playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
		if (playerPositions[currentPlayer] > 11) playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerPositions[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == POP_CATEGORY)
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == SCIENCE_CATEGORY)
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		int playerPosition = playerPositions[currentPlayer];
		if (playerPosition == 0) return POP_CATEGORY;
		if (playerPosition == 4) return POP_CATEGORY;
		if (playerPosition == 8) return POP_CATEGORY;
		if (playerPosition == 1) return SCIENCE_CATEGORY;
		if (playerPosition == 5) return SCIENCE_CATEGORY;
		if (playerPosition == 9) return SCIENCE_CATEGORY;
		if (playerPosition == 2) return SPORTS_CATEGORY;
		if (playerPosition == 6) return SPORTS_CATEGORY;
		if (playerPosition == 10) return SPORTS_CATEGORY;
		return ROCK_CATEGORY;
	}

	public boolean isCorrectAnswer() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");

				addCoinToPlayersPurse();
				boolean noWinner = isGameContinuing();
				getNextPlayer();

				return noWinner;
			} else {
				getNextPlayer();
				return true;
			}



		} else {
			System.out.println("Answer was corrent!!!!");

			addCoinToPlayersPurse();
			boolean noWinner = isGameContinuing();
			getNextPlayer();

			return noWinner;
		}
	}

	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		getNextPlayer();
		return true;
	}


	private void getNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private boolean isGameContinuing() {
		return !(playerPurses[currentPlayer] == 6);
	}

	private void addCoinToPlayersPurse() {
		playerPurses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");
	}
}