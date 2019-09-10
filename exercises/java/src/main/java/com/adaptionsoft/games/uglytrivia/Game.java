package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

	private static final int MAX_PLAYERS = 6;
	private static final int MIN_PLAYERS = 2;
	private static final int WINNING_SCORE = 6;
	private static final int MAX_QUESTIONS = 50;
	private static final int LAST_POSITION = 11;
	private static final int RESET_POSITION = 12;
	private static final int PENALTY_CHECK_VALUE = 2;

	private static final String CATEGORY_POP = "Pop";
	private static final String CATEGORY_SCIENCE = "Science";
	private static final String CATEGORY_SPORTS = "Sports";
	private static final String CATEGORY_ROCK = "Rock";

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
    	for (int i = 0; i < MAX_QUESTIONS; i++) {
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
	
	private int getAmountOfPlayers() {
    	return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (playersInPenaltyBox[currentPlayer]) {
			penaltyCheck(roll);
		} else {
			updatePlayerLocation(roll);
		}
	}

	private void penaltyCheck(final int roll) {
		if (roll % PENALTY_CHECK_VALUE != 0) {
			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			isOutOfPenaltyBox = true;

			updatePlayerLocation(roll);
		} else {
			System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			isOutOfPenaltyBox = false;
			}
	}

	private void updatePlayerLocation(final int roll) {
		playerLocations[currentPlayer] += roll;
		if (playerLocations[currentPlayer] > LAST_POSITION) {
			playerLocations[currentPlayer] -= RESET_POSITION;
		}

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerLocations[currentPlayer]);
		System.out.println("The category is " + getCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (getCategory().equals(CATEGORY_POP))
			System.out.println(popQuestionsList.removeFirst());
		if (getCategory().equals(CATEGORY_SCIENCE))
			System.out.println(scienceQuestionsList.removeFirst());
		if (getCategory().equals(CATEGORY_SPORTS))
			System.out.println(sportsQuestionsList.removeFirst());
		if (getCategory().equals(CATEGORY_ROCK))
			System.out.println(rockQuestionsList.removeFirst());
	}
	
	
	private String getCategory() {
		if (playerLocations[currentPlayer] == 0) return CATEGORY_POP;
		if (playerLocations[currentPlayer] == 4) return CATEGORY_POP;
		if (playerLocations[currentPlayer] == 8) return CATEGORY_POP;
		if (playerLocations[currentPlayer] == 1) return CATEGORY_SCIENCE;
		if (playerLocations[currentPlayer] == 5) return CATEGORY_SCIENCE;
		if (playerLocations[currentPlayer] == 9) return CATEGORY_SCIENCE;
		if (playerLocations[currentPlayer] == 2) return CATEGORY_SPORTS;
		if (playerLocations[currentPlayer] == 6) return CATEGORY_SPORTS;
		if (playerLocations[currentPlayer] == 10) return CATEGORY_SPORTS;
		return CATEGORY_ROCK;
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
			// Don't touch this spelling mistake upon pain of death
			// It seems to hold the whole universe together
			System.out.println("Answer was corrent!!!!");
			return calculateScoreAndDisplay();
		}
	}

	public boolean wasAnsweredIncorrectly(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		resetPlayerToZero();
		return true;
	}

	private boolean calculateScoreAndDisplay() {
		playerPurses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");

		return determineWinner();
	}

	private boolean determineWinner() {
		boolean winner = didPlayerWin();
		currentPlayer++;
		resetPlayerToZero();
		return winner;
	}

	private void resetPlayerToZero() {
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private boolean didPlayerWin() {
		return !(playerPurses[currentPlayer] == WINNING_SCORE);
	}
}
