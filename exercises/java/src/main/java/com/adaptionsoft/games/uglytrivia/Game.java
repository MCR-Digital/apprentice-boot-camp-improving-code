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
		System.out.println(getCurrentPlayer().getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (getCurrentPlayer().isInPenaltyBox()) {
			if (roll % 2 != 0) {
				getCurrentPlayer().leavePenaltyBox();
				movePlayer(roll);
				askQuestion();
			} else {
				getCurrentPlayer().remainInPenaltyBox();
			}
			
		} else {
			movePlayer(roll);
			askQuestion();
		}
		
	}

	private Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}


	private void movePlayer(int roll) {
		getCurrentPlayer().setPlayerPosition(
				getCurrentPlayer().getPlayerPosition() + roll
		);

		if (getCurrentPlayer().getPlayerPosition() > MAX_TILE_LIMIT) {
			getCurrentPlayer().setPlayerPosition(getCurrentPlayer().getPlayerPosition()
					- TOTAL_TILES);
		}

		System.out.println(getCurrentPlayer().getName()
				+ "'s new location is "
				+ getCurrentPlayer().getPlayerPosition());
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
		if (tiles[getCurrentPlayer().getPlayerPosition()].equals("Pop")) {
			return "Pop";
		} else if (tiles[getCurrentPlayer().getPlayerPosition()].equals("Science")) {
			return "Science";
		} else if (tiles[getCurrentPlayer().getPlayerPosition()].equals("Sports")) {
			return "Sports";
		} else if (tiles[getCurrentPlayer().getPlayerPosition()].equals("Rock")) {
			return "Rock";
		} else {
			return "Invalid player position";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (getCurrentPlayer().isInPenaltyBox()){
			if (getCurrentPlayer().isLeavingPenaltyBox()) {
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
		getCurrentPlayer().setCoinCount(
				getCurrentPlayer().getCoinCount() + 1
		);
		System.out.println(getCurrentPlayer().getName()
				+ " now has "
				+  getCurrentPlayer().getCoinCount()
				+ " Gold Coins.");
	}

	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
		getCurrentPlayer().setIsInPenaltyBox(true);

		return true;
	}
	
	private boolean didPlayerWin() {
		return !(getCurrentPlayer().getCoinCount() == 6);
	}
}
