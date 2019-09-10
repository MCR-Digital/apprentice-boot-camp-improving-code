package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] locationOnBoard = new int[6];
    int[] goldCoins = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isExitingPenaltyBox;
    
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
		return (numberOfPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {

	    players.add(playerName);
	    locationOnBoard[numberOfPlayers()] = 0;
	    goldCoins[numberOfPlayers()] = 0;
	    isInPenaltyBox[numberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int numberOfPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isExitingPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] + roll;
				if (locationOnBoard[currentPlayer] > 11) locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ locationOnBoard[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isExitingPenaltyBox = false;
				}
			
		} else {
		
			locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] + roll;
			if (locationOnBoard[currentPlayer] > 11) locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ locationOnBoard[currentPlayer]);
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
		if (locationOnBoard[currentPlayer] == 0) return "Pop";
		if (locationOnBoard[currentPlayer] == 4) return "Pop";
		if (locationOnBoard[currentPlayer] == 8) return "Pop";
		if (locationOnBoard[currentPlayer] == 1) return "Science";
		if (locationOnBoard[currentPlayer] == 5) return "Science";
		if (locationOnBoard[currentPlayer] == 9) return "Science";
		if (locationOnBoard[currentPlayer] == 2) return "Sports";
		if (locationOnBoard[currentPlayer] == 6) return "Sports";
		if (locationOnBoard[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean isCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isExitingPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				goldCoins[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ goldCoins[currentPlayer]
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
			goldCoins[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ goldCoins[currentPlayer]
					+ " Gold Coins.");
			
			boolean isWinner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return isWinner;
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
		return !(goldCoins[currentPlayer] == 6);
	}
}
