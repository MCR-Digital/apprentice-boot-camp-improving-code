package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] playerBoardPosition = new int[6];
    int[] playerCoinCount = new int[6];
    boolean[] isPlayerInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isPlayerGettingOutOfPenaltyBox;
    
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
	    playerBoardPosition[howManyPlayers()] = 0;
	    playerCoinCount[howManyPlayers()] = 0;
	    isPlayerInPenaltyBox[howManyPlayers()] = false;
	    
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
		
		if (isPlayerInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isPlayerGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] + roll;
				if (playerBoardPosition[currentPlayer] > 11) playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ playerBoardPosition[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isPlayerGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] + roll;
			if (playerBoardPosition[currentPlayer] > 11) playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ playerBoardPosition[currentPlayer]);
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
		if (playerBoardPosition[currentPlayer] == 0) return "Pop";
		if (playerBoardPosition[currentPlayer] == 4) return "Pop";
		if (playerBoardPosition[currentPlayer] == 8) return "Pop";
		if (playerBoardPosition[currentPlayer] == 1) return "Science";
		if (playerBoardPosition[currentPlayer] == 5) return "Science";
		if (playerBoardPosition[currentPlayer] == 9) return "Science";
		if (playerBoardPosition[currentPlayer] == 2) return "Sports";
		if (playerBoardPosition[currentPlayer] == 6) return "Sports";
		if (playerBoardPosition[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isPlayerInPenaltyBox[currentPlayer]){
			if (isPlayerGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playerCoinCount[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ playerCoinCount[currentPlayer]
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
			playerCoinCount[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ playerCoinCount[currentPlayer]
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
		isPlayerInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playerCoinCount[currentPlayer] == 6);
	}
}
