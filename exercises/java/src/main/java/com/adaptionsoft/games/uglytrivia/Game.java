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

	private ArrayList<Player> playersInGame = new ArrayList();
	private int[] playerOrder = new int[MAX_PLAYERS];
	private int[] playerPurses = new int[MAX_PLAYERS];
	private boolean[] playersInPenaltyBox = new boolean[MAX_PLAYERS];

	private LinkedList popQuestionsList = new LinkedList();
	private LinkedList scienceQuestionsList = new LinkedList();
	private LinkedList sportsQuestionsList = new LinkedList();
	private LinkedList rockQuestionsList = new LinkedList();

	private int playerLocation = 0;
	private boolean isOutOfPenaltyBox;

	private Board gameBoard = new Board();

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
	    playersInGame.add(player);
	    playerOrder[getAmountOfPlayers()] = 0;
	    playerPurses[getAmountOfPlayers()] = 0;
	    playersInPenaltyBox[getAmountOfPlayers()] = false;
	    
	    System.out.println(player + " was added");
	    System.out.println("They are player number " + playersInGame.size());
		return true;
	}
	
	private int getAmountOfPlayers() {
    	return playersInGame.size();
	}

	public void playGame(int roll) {
    	Player player = playersInGame.get(playerLocation);
		System.out.println(playersInGame.get(playerLocation) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (player.isPlayerInPenaltyBox()) {
			penaltyCheck(roll);
		} else {
			updatePlayerLocation(roll);
		}
	}

	private void penaltyCheck(final int result) {
		Player player = playersInGame.get(playerLocation);

		if (result % PENALTY_CHECK_VALUE != 0) {
			System.out.println(playersInGame.get(playerLocation) + " is getting out of the penalty box");
			player.takePlayerOutOfPenaltyBox();
			isOutOfPenaltyBox = true;

			updatePlayerLocation(result);
		} else {
			System.out.println(playersInGame.get(playerLocation) + " is not getting out of the penalty box");
			isOutOfPenaltyBox = false;
			}
	}

	private void updatePlayerLocation(final int roll) {
		playerOrder[playerLocation] += roll;
		if (playerOrder[playerLocation] > LAST_POSITION) {
			playerOrder[playerLocation] -= RESET_POSITION;
		}

		System.out.println(playersInGame.get(playerLocation)
				+ "'s new location is "
				+ playerOrder[playerLocation]);
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
    	String[] boardSpaces = {
    			CATEGORY_POP, CATEGORY_SCIENCE, CATEGORY_SPORTS, CATEGORY_ROCK,
				CATEGORY_POP, CATEGORY_SCIENCE, CATEGORY_SPORTS, CATEGORY_ROCK,
				CATEGORY_POP, CATEGORY_SCIENCE, CATEGORY_SPORTS, CATEGORY_ROCK};
    	return boardSpaces[playerOrder[playerLocation]];
	}

	public boolean wasAnsweredCorrectly() {
		if (playersInPenaltyBox[playerLocation]){
			if (isOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return calculateScoreAndDisplay();
			} else {
				playerLocation++;
				resetPlayerLocationToZero();
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
		Player player = playersInGame.get(playerLocation);
		System.out.println("Question was incorrectly answered");
		System.out.println(playersInGame.get(playerLocation)+ " was sent to the penalty box");

		player.putPlayerInPenaltyBox();
		//playersInPenaltyBox[playerLocation] = true;

		playerLocation++;
		resetPlayerLocationToZero();
		return true;
	}

	private boolean calculateScoreAndDisplay() {
		playerPurses[playerLocation]++;
		System.out.println(playersInGame.get(playerLocation)
				+ " now has "
				+ playerPurses[playerLocation]
				+ " Gold Coins.");

		return determineWinner();
	}

	private boolean determineWinner() {
		boolean winner = didPlayerWin();
		playerLocation++;
		resetPlayerLocationToZero();
		return winner;
	}

	private void resetPlayerLocationToZero() {
		if (playerLocation == playersInGame.size()) playerLocation = 0;
	}

	private boolean didPlayerWin() {
		return (playerPurses[playerLocation] != WINNING_SCORE);
	}
}
