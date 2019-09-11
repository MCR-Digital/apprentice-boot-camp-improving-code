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

	private ArrayList playersInGame = new ArrayList();
	private int[] playerOrder = new int[MAX_PLAYERS];
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

	public boolean addPlayerToGame(Player player) {
	    playersInGame.add(player.getName());
	    playerOrder[getAmountOfPlayers()] = 0;
	    playerPurses[getAmountOfPlayers()] = 0;
	    playersInPenaltyBox[getAmountOfPlayers()] = false;
	    
	    System.out.println(player.getName() + " was added");
	    System.out.println("They are player number " + playersInGame.size());
		return true;
	}
	
	private int getAmountOfPlayers() {
    	return playersInGame.size();
	}

	public void playGame(int roll) {
		System.out.println(playersInGame.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (playersInPenaltyBox[currentPlayer]) {
			penaltyCheck(roll);
		} else {
			updatePlayerLocation(roll);
		}
	}

	private void penaltyCheck(final int roll) {
		if (roll % PENALTY_CHECK_VALUE != 0) {
			System.out.println(playersInGame.get(currentPlayer) + " is getting out of the penalty box");
			isOutOfPenaltyBox = true;

			updatePlayerLocation(roll);
		} else {
			System.out.println(playersInGame.get(currentPlayer) + " is not getting out of the penalty box");
			isOutOfPenaltyBox = false;
			}
	}

	private void updatePlayerLocation(final int roll) {
		playerOrder[currentPlayer] += roll;
		if (playerOrder[currentPlayer] > LAST_POSITION) {
			playerOrder[currentPlayer] -= RESET_POSITION;
		}

		System.out.println(playersInGame.get(currentPlayer)
				+ "'s new location is "
				+ playerOrder[currentPlayer]);
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
    	String[] boardSpaces = {CATEGORY_POP, CATEGORY_SCIENCE, CATEGORY_SPORTS, CATEGORY_ROCK, CATEGORY_POP,
				CATEGORY_SCIENCE, CATEGORY_SPORTS, CATEGORY_ROCK, CATEGORY_POP, CATEGORY_SCIENCE,
				CATEGORY_SPORTS, CATEGORY_ROCK};
    	return boardSpaces[playerOrder[currentPlayer]];
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
		System.out.println(playersInGame.get(currentPlayer)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		resetPlayerToZero();
		return true;
	}

	private boolean calculateScoreAndDisplay() {
		playerPurses[currentPlayer]++;
		System.out.println(playersInGame.get(currentPlayer)
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
		if (currentPlayer == playersInGame.size()) currentPlayer = 0;
	}

	private boolean didPlayerWin() {
		return (playerPurses[currentPlayer] != WINNING_SCORE);
	}
}
