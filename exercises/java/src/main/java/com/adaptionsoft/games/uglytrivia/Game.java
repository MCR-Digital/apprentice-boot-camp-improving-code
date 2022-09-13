package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int MAX_TILE_LIMIT = 11;
	public static final int TOTAL_TILES = 12;
	ArrayList<Player> players = new ArrayList<Player>();
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;

	String[] tiles =
			new String[] {
					"Pop", "Science", "Sports", "Rock",
					"Pop", "Science", "Sports", "Rock",
					"Pop", "Science", "Sports", "Rock",
			};
    
    public  Game(){
    	for (int index = 0; index < 50; index++) {
			popQuestions.addLast(createQuestion("Pop", index));
			scienceQuestions.addLast(createQuestion("Science", index));
			sportsQuestions.addLast(createQuestion("Sports", index));
			rockQuestions.addLast(createQuestion("Rock", index));
    	}
    }
	public String createQuestion(String subject, int index) {
		return subject + " Question " + index;
	}
	
	public boolean isPlayable() {
		return (amountOfPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(new Player(playerName));
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());

		return true;
	}
	
	public int amountOfPlayers() {
		return players.size();
	}

	public void turn(int roll) {
		System.out.println(players.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (players.get(currentPlayer).isInPenaltyBox()) {
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
		players.get(currentPlayer).setIsLeavingPenaltyBox(true);
		System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");
	}

	private void remainInPenaltyBox() {
		players.get(currentPlayer).setIsLeavingPenaltyBox(false);
		System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
	}

	private void movePlayer(int roll) {
		players.get(currentPlayer).setPlayerPosition(
				players.get(currentPlayer).getPlayerPosition() + roll
		);

		if (players.get(currentPlayer).getPlayerPosition() > MAX_TILE_LIMIT) {
			players.get(currentPlayer).setPlayerPosition(
					players.get(currentPlayer).getPlayerPosition() - TOTAL_TILES
			);
		}

		System.out.println(players.get(currentPlayer).getName()
				+ "'s new location is "
				+ players.get(currentPlayer).getPlayerPosition());
		System.out.println("The category is " + currentCategory());
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
		if (tiles[players.get(currentPlayer).getPlayerPosition()].equals("Pop")) {
			return "Pop";
		} else if (tiles[players.get(currentPlayer).getPlayerPosition()].equals("Science")) {
			return "Science";
		} else if (tiles[players.get(currentPlayer).getPlayerPosition()].equals("Sports")) {
			return "Sports";
		} else if (tiles[players.get(currentPlayer).getPlayerPosition()].equals("Rock")) {
			return "Rock";
		} else {
			return "Invalid player position";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (players.get(currentPlayer).isInPenaltyBox()){
			if (players.get(currentPlayer).isLeavingPenaltyBox()) {
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
		players.get(currentPlayer).setCoinCount(
				players.get(currentPlayer).getCoinCount() + 1
		);
		System.out.println(players.get(currentPlayer).getName()
				+ " now has "
				+  players.get(currentPlayer).getCoinCount()
				+ " Gold Coins.");
	}

	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");
		players.get(currentPlayer).setIsInPenaltyBox(true);

		return true;
	}
	
	private boolean didPlayerWin() {
		return !(players.get(currentPlayer).getCoinCount() == 6);
	}
}
