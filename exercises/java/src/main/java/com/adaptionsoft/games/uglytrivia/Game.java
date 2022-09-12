package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] gameTiles = new int[6];
    int[] playersPurses = new int[6];
    boolean[] isInPenatlyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    gameTiles[howManyPlayers()] = 0;
	    playersPurses[howManyPlayers()] = 0;
	    isInPenatlyBox[howManyPlayers()] = false;
	    
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
		
		if (isInPenatlyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				gameTiles[currentPlayer] = gameTiles[currentPlayer] + roll;
				if (gameTiles[currentPlayer] > 11) gameTiles[currentPlayer] = gameTiles[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ gameTiles[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			gameTiles[currentPlayer] = gameTiles[currentPlayer] + roll;
			if (gameTiles[currentPlayer] > 11) gameTiles[currentPlayer] = gameTiles[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ gameTiles[currentPlayer]);
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
		if (gameTiles[currentPlayer] == 0) return "Pop";
		if (gameTiles[currentPlayer] == 4) return "Pop";
		if (gameTiles[currentPlayer] == 8) return "Pop";
		if (gameTiles[currentPlayer] == 1) return "Science";
		if (gameTiles[currentPlayer] == 5) return "Science";
		if (gameTiles[currentPlayer] == 9) return "Science";
		if (gameTiles[currentPlayer] == 2) return "Sports";
		if (gameTiles[currentPlayer] == 6) return "Sports";
		if (gameTiles[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenatlyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playersPurses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ playersPurses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			playersPurses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ playersPurses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenatlyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playersPurses[currentPlayer] == 6);
	}
}
