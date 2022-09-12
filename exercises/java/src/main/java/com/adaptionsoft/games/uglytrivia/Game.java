package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] playerLocation = new int[6];
    int[] playerPurse = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
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
			rockQuestions.addLast("Rock Question " + i);
    	}
    }

	public boolean addNewPlayerToGame(String playerName) {
		
		
	    players.add(playerName);
	    playerLocation[howManyPlayers()] = 0;
	    playerPurse[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void rollTheDice(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				playerLocation[currentPlayer] = currentPlayerLocation() + roll;
				if (currentPlayerLocation() > 11) playerLocation[currentPlayer] = currentPlayerLocation() - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ currentPlayerLocation());
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			playerLocation[currentPlayer] = currentPlayerLocation() + roll;
			if (currentPlayerLocation() > 11) playerLocation[currentPlayer] = currentPlayerLocation() - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ currentPlayerLocation());
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
		if (currentPlayerLocation() == 0) return "Pop";
		if (currentPlayerLocation() == 1) return "Science";
		if (currentPlayerLocation() == 2) return "Sports";
		if (currentPlayerLocation() == 4) return "Pop";
		if (currentPlayerLocation() == 5) return "Science";
		if (currentPlayerLocation() == 6) return "Sports";
		if (currentPlayerLocation() == 8) return "Pop";
		if (currentPlayerLocation() == 9) return "Science";
		if (currentPlayerLocation() == 10) return "Sports";
		return "Rock";
	}

	private int currentPlayerLocation() {
		return playerLocation[currentPlayer];
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playerPurse[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ playerPurse[currentPlayer]
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
			playerPurse[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ playerPurse[currentPlayer]
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
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playerPurse[currentPlayer] == 6);
	}
}
