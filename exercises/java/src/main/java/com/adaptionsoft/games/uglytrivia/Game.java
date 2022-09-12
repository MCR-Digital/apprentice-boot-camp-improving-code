package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int MAX_TILES = 12;
	ArrayList players = new ArrayList();
    int[] currentTile = new int[6];
    int[] coins = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int count = 0; count < 50; count++) {
			popQuestions.addLast("Pop Question " + count);
			scienceQuestions.addLast(("Science Question " + count));
			sportsQuestions.addLast(("Sports Question " + count));
			rockQuestions.addLast(createRockQuestion(count));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean addPlayerToGame(String playerName) {
		
		
	    players.add(playerName);
	    currentTile[howManyPlayers()] = 0;
	    coins[howManyPlayers()] = 0;
	    isInPenaltyBox[howManyPlayers()] = false;
	    
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
		
		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				currentTile[currentPlayer] = currentTile[currentPlayer] + roll;
				if (currentTile[currentPlayer] > 11) currentTile[currentPlayer] = currentTile[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ currentTile[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			currentTile[currentPlayer] = currentTile[currentPlayer] + roll;
			if (currentTile[currentPlayer] > 11) currentTile[currentPlayer] = currentTile[currentPlayer] - MAX_TILES;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ currentTile[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
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
		if (currentTile[currentPlayer] == 0) return "Pop";
		if (currentTile[currentPlayer] == 4) return "Pop";
		if (currentTile[currentPlayer] == 8) return "Pop";
		if (currentTile[currentPlayer] == 1) return "Science";
		if (currentTile[currentPlayer] == 5) return "Science";
		if (currentTile[currentPlayer] == 9) return "Science";
		if (currentTile[currentPlayer] == 2) return "Sports";
		if (currentTile[currentPlayer] == 6) return "Sports";
		if (currentTile[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				coins[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ coins[currentPlayer]
						+ " Gold Coins.");
				
				boolean isPlayerWinner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return isPlayerWinner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			coins[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ coins[currentPlayer]
					+ " Gold Coins.");
			
			boolean isPlayerWinner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return isPlayerWinner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(coins[currentPlayer] == 6);
	}
}
