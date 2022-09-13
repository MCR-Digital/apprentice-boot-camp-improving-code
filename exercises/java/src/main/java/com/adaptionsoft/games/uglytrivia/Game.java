package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int MAX_TILE_LIMIT = 11;
	public static final int TOTAL_TILES = 12;
	ArrayList players = new ArrayList();
    int[] playerPosition = new int[6];
    int[] coinCount = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isLeavingPenaltyBox;

	String[] tiles =
			new String[] {
					"Pop", "Science", "Sports", "Rock",
					"Pop", "Science", "Sports", "Rock",
					"Pop", "Science", "Sports", "Rock",
			};
    
    public  Game(){
		// this could be much better
    	for (int index = 0; index < 50; index++) {
			popQuestions.addLast(createQuestion("Pop", index));
			scienceQuestions.addLast(createQuestion("Science", index));
			sportsQuestions.addLast(createQuestion("Sports", index));
			rockQuestions.addLast(createQuestion("Rock", index));
    	}
    }

	//this should be in a Category class
	public String createQuestion(String subject, int index) {
		return subject + " Question " + index;
	}
	
	public boolean isPlayable() {
		return (amountOfPlayers() >= 2);
	}

	public boolean add(String playerName) {

	    players.add(playerName);
	    playerPosition[amountOfPlayers()] = 0;
	    coinCount[amountOfPlayers()] = 0;
	    isInPenaltyBox[amountOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());

		return true;
	}
	
	public int amountOfPlayers() {
		return players.size();
	}

	public void turn(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				leavePenaltyBox();
				movePlayer(roll);
				askQuestion();
			} else {
				remainInPenaltyBox();
			}
			
		} else {
			movePlayer(roll);
			askQuestion();
		}
		
	}

	private void leavePenaltyBox() {
		isLeavingPenaltyBox = true;
		System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
	}

	private void remainInPenaltyBox() {
		System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
		isLeavingPenaltyBox = false;
	}

	private void movePlayer(int roll) {
		//TODO: change TOTAL_TILES name

		setPlayerPosition(getPlayerPosition() + roll);
		if (getPlayerPosition() > MAX_TILE_LIMIT) setPlayerPosition(getPlayerPosition() - TOTAL_TILES);

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ getPlayerPosition());
		System.out.println("The category is " + currentCategory());
	}

	private void askQuestion() {
		// replace with switch?
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
		// add a method in the enum that translates between int and string of pop
//		if (playerPosition[currentPlayer] % AMOUNT_OF_CATEGORIES == Categories.POP) return "Pop";

		if (tiles[getPlayerPosition()].equals("Pop")) {
			return "Pop";
		} else if (tiles[getPlayerPosition()].equals("Science")) {
			return "Science";
		} else if (tiles[getPlayerPosition()].equals("Sports")) {
			return "Sports";
		} else if (tiles[getPlayerPosition()].equals("Rock")) {
			return "Rock";
		} else {
			return "Invalid player position";
		}
	}

	private int getPlayerPosition() {
		return playerPosition[currentPlayer];
	}

	private void setPlayerPosition(int newPosition) {
		playerPosition[currentPlayer] = newPosition;
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isLeavingPenaltyBox) {
				incrementCoinCount();
				return didPlayerWin();
			} else {
				return true;
			}
		} else {
			incrementCoinCount();
			return didPlayerWin();
		}
	}

	public void changePlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private void incrementCoinCount() {
		System.out.println("Answer was correct!!!!");
		coinCount[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ coinCount[currentPlayer]
				+ " Gold Coins.");
	}

	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenaltyBox[currentPlayer] = true;

		return true;
	}
	
	private boolean didPlayerWin() {
		return !(coinCount[currentPlayer] == 6);
	}
}
