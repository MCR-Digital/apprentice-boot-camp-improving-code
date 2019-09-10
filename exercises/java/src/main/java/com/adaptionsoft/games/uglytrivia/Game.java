package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private static final int MAX_PLAYERS = 6;
	private static final int MIN_PLAYERS = 2;
	private static final int WINNING_SCORE = 6;

	private ArrayList players = new ArrayList();
	private int[] playerLocations = new int[MAX_PLAYERS];
	private int[] playerPurses = new int[MAX_PLAYERS];
	private boolean[] playersInPenaltyBox = new boolean[MAX_PLAYERS];

	private LinkedList popQuestionsList = new LinkedList();
	private LinkedList scienceQuestionsList = new LinkedList();
	private LinkedList sportsQuestionsList = new LinkedList();
	private LinkedList rockQuestionsList = new LinkedList();

	private int currentPlayer = 0;
	private boolean isOutOfPenaltyBox;
    
    public Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestionsList.addLast("Pop Question " + i);
			scienceQuestionsList.addLast(("Science Question " + i));
			sportsQuestionsList.addLast(("Sports Question " + i));
			rockQuestionsList.addLast("Rock Question " + i);
    	}
    }
	
	public boolean sufficientPlayersToPlay() {
		return (getAmountOfPlayers() >= MIN_PLAYERS);
	}

	public boolean addPlayerToGame(String playerName) {
	    players.add(playerName);
	    playerLocations[getAmountOfPlayers()] = 0;
	    playerPurses[getAmountOfPlayers()] = 0;
	    playersInPenaltyBox[getAmountOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int getAmountOfPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (playersInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

				updatePlayerLocation(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isOutOfPenaltyBox = false;
				}
			
		} else {
			updatePlayerLocation(roll);
		}
		
	}

	private void updatePlayerLocation(final int roll) {
		playerLocations[currentPlayer] += roll;
		if (playerLocations[currentPlayer] > 11) {
			playerLocations[currentPlayer] -= 12;
		}

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerLocations[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestionsList.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestionsList.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestionsList.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestionsList.removeFirst());
	}
	
	
	private String currentCategory() {
		if (playerLocations[currentPlayer] == 0) return "Pop";
		if (playerLocations[currentPlayer] == 4) return "Pop";
		if (playerLocations[currentPlayer] == 8) return "Pop";
		if (playerLocations[currentPlayer] == 1) return "Science";
		if (playerLocations[currentPlayer] == 5) return "Science";
		if (playerLocations[currentPlayer] == 9) return "Science";
		if (playerLocations[currentPlayer] == 2) return "Sports";
		if (playerLocations[currentPlayer] == 6) return "Sports";
		if (playerLocations[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasAnsweredCorrectly() {
		if (playersInPenaltyBox[currentPlayer]){
			if (isOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return calculateScoreAndDisplay();
			} else {
				currentPlayer++;
				resetPlayerToZero();
				return true;
			}
		} else {
			System.out.println("Answer was corrent!!!!");
			return calculateScoreAndDisplay();
		}
	}

	private boolean calculateScoreAndDisplay() {
		playerPurses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		return isPlayerTheWinner(winner);
	}

	private boolean isPlayerTheWinner(final boolean winner) {
		resetPlayerToZero();
		return winner;
	}

	public boolean wasAnsweredIncorrectly(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		resetPlayerToZero();
		return true;
	}

	private void resetPlayerToZero() {
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private boolean didPlayerWin() {
		return !(playerPurses[currentPlayer] == WINNING_SCORE);
	}
}
