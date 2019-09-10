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

	ArrayList players = new ArrayList();
    int[] places = new int[MAX_NUMBER_OF_PLAYERS];
    int[] purses  = new int[MAX_NUMBER_OF_PLAYERS];
    boolean[] playersInPenaltyBox = new boolean[MAX_NUMBER_OF_PLAYERS];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayerID = 0;
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

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    places[getNumberOfPlayers()] = 0;
	    purses[getNumberOfPlayers()] = 0;
	    playersInPenaltyBox[getNumberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int getNumberOfPlayers() {
		return players.size();
	}

	public void processRoll(int rolledNumber) {
		System.out.println(players.get(currentPlayerID) + " is the current player");
		System.out.println("They have rolled a " + rolledNumber);
		
		if (isPlayerInPenaltyBox()) {
			if (rolledNumber % 2 != 0) {
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

	private void movePlayer(int rolledNumber) {
		places[currentPlayerID] = getCurrentPlayerPosition() + rolledNumber;
		if (currentPlayerPassedFinalTile()) {
			continueMoveFromStartOfBoard();
		}
	}

	private void continueMoveFromStartOfBoard() {
		places[currentPlayerID] = getCurrentPlayerPosition() - 12;
	}

	private boolean currentPlayerPassedFinalTile() {
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
		if (isPlayerInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				boolean winner = addCoin();
				currentPlayerID++;
				if (isLastPlayerInList()) resetBackToPlayerOne();

				return winner;
			} else {
				currentPlayerID++;
				if (isLastPlayerInList()) resetBackToPlayerOne();
				return true;
			}

		} else {
			boolean winner = addCoin();
			currentPlayerID++;
			if (isLastPlayerInList()) {
				resetBackToPlayerOne();
			}

			return winner;
		}
	}

	private boolean isPlayerInPenaltyBox() {
		return playersInPenaltyBox[currentPlayerID];
	}

	private void resetBackToPlayerOne() {
		currentPlayerID = 0;
	}

	private boolean addCoin() {
		System.out.println("Answer was correct!!!!");
		purses[currentPlayerID]++;
		System.out.println(players.get(currentPlayerID)
				+ " now has "
				+ getPlayerPurse(currentPlayerID)
				+ " Gold Coins.");

		return hasPlayerWon();
	}

	private int getPlayerPurse(int playerID) {
		return purses[playerID];
	}

	public boolean handleIncorrectAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayerID)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayerID] = true;
		
		currentPlayerID++;
		if (isLastPlayerInList()) resetBackToPlayerOne();
		return true;
	}

	private boolean isLastPlayerInList() {
		return currentPlayerID == players.size();
	}


	private boolean hasPlayerWon() {
		return !(getPlayerPurse(currentPlayerID) == 6);
	}
}
