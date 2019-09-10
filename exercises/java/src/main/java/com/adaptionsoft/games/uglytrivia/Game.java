package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private static final String POP = "Pop";
	private static final String SCIENCE = "Science";
	private static final String SPORTS = "Sports";
	private static final String ROCK = "Rock";
	private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
	private static final int MAX_NUMBER_OF_PLAYERS = 6;
	private static final int NUMBER_OF_QUESTIONS = 50;
	private static final int PLAYER_ONE_ID = 0;

	private ArrayList players = new ArrayList();
    private int[] places = new int[MAX_NUMBER_OF_PLAYERS];
    private int[] purses  = new int[MAX_NUMBER_OF_PLAYERS];
    private boolean[] playersInPenaltyBox = new boolean[MAX_NUMBER_OF_PLAYERS];
    
    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();
    
    int currentPlayerID = PLAYER_ONE_ID;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int index = 0; index < NUMBER_OF_QUESTIONS; index++) {
			popQuestions.addLast("Pop Question " + index);
			scienceQuestions.addLast(("Science Question " + index));
			sportsQuestions.addLast(("Sports Question " + index));
			rockQuestions.addLast(createRockQuestion(index));
    	}
    }

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (getNumberOfPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
	}

	public boolean addPlayer(String playerName) {
		
		
	    players.add(playerName);
	    places[getNumberOfPlayers()] = 0;
	    purses[getNumberOfPlayers()] = 0;
	    playersInPenaltyBox[getNumberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	private int getNumberOfPlayers() {
		return players.size();
	}

	public void processRoll(int rolledNumber) {
		System.out.println(players.get(currentPlayerID) + " is the current player");
		System.out.println("They have rolled a " + rolledNumber);
		
		if (isPlayerInPenaltyBox(currentPlayerID)) {
			if (isOddNumber(rolledNumber)) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayerID) + " is getting out of the penalty box");
				movePlayer(rolledNumber);

				System.out.println(players.get(currentPlayerID)
						+ "'s new location is " 
						+ getCurrentPlayerPosition());
				System.out.println("The category is " + getCurrentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayerID) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayer(rolledNumber);

			System.out.println(players.get(currentPlayerID)
					+ "'s new location is " 
					+ getCurrentPlayerPosition());
			System.out.println("The category is " + getCurrentCategory());
			askQuestion();
		}
		
	}

	private boolean isOddNumber(int number) {
		return number % 2 != 0;
	}

	private void movePlayer(int rolledNumber) {
		places[currentPlayerID] = getCurrentPlayerPosition() + rolledNumber;
		if (hasCurrentPlayerPassedFinalTile()) {
			continueMoveFromStartOfBoard();
		}
	}

	private void continueMoveFromStartOfBoard() {
		places[currentPlayerID] = getCurrentPlayerPosition() - 12;
	}

	private boolean hasCurrentPlayerPassedFinalTile() {
		return getCurrentPlayerPosition() > 11;
	}

	private void askQuestion() {
		if (getCurrentCategory() == POP)
			System.out.println(popQuestions.removeFirst());
		if (getCurrentCategory() == SCIENCE)
			System.out.println(scienceQuestions.removeFirst());
		if (getCurrentCategory() == SPORTS)
			System.out.println(sportsQuestions.removeFirst());
		if (getCurrentCategory() == ROCK)
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String getCurrentCategory() {
		if (getCurrentPlayerPosition() == 0) return POP;
		if (getCurrentPlayerPosition() == 4) return POP;
		if (getCurrentPlayerPosition() == 8) return POP;
		if (getCurrentPlayerPosition() == 1) return SCIENCE;
		if (getCurrentPlayerPosition() == 5) return SCIENCE;
		if (getCurrentPlayerPosition() == 9) return SCIENCE;
		if (getCurrentPlayerPosition() == 2) return SPORTS;
		if (getCurrentPlayerPosition() == 6) return SPORTS;
		if (getCurrentPlayerPosition() == 10) return SPORTS;
		return ROCK;
	}

	private int getCurrentPlayerPosition() {
		return places[currentPlayerID];
	}

	public boolean handleCorrectAnswer() {
		if (isPlayerInPenaltyBox(currentPlayerID)){
			if (isGettingOutOfPenaltyBox) {
				boolean winner = addCoin(currentPlayerID);
				switchToNextPlayer();

				return winner;
			} else {
				switchToNextPlayer();
				return true;
			}

		} else {
			boolean winner = addCoin(currentPlayerID);
			switchToNextPlayer();

			return winner;
		}
	}

	private void switchToNextPlayer() {
		currentPlayerID++;
		if (isLastPlayerInList()) resetBackToPlayerOne();
	}

	private boolean isPlayerInPenaltyBox(int playerID) {
		return playersInPenaltyBox[playerID];
	}

	private void resetBackToPlayerOne() {
		currentPlayerID = PLAYER_ONE_ID;
	}

	private boolean addCoin(int playerID) {
		System.out.println("Answer was correct!!!!");
		purses[currentPlayerID]++;
		System.out.println(players.get(playerID)
				+ " now has "
				+ getPlayerPurse(playerID)
				+ " Gold Coins.");

		return hasPlayerWon(currentPlayerID);
	}

	private int getPlayerPurse(int playerID) {
		return purses[playerID];
	}

	public boolean handleIncorrectAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayerID)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayerID] = true;

		switchToNextPlayer();
		return true;
	}

	private boolean isLastPlayerInList() {
		return currentPlayerID == players.size();
	}


	private boolean hasPlayerWon(int playerID) {
		return !(getPlayerPurse(playerID) == 6);
	}
}
