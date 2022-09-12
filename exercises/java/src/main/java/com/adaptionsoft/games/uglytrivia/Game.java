package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] playerPositions = new int[6];
    int[] coinCounts = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isLeavingPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

//	public String createQuestion(int index, String subject) {
//		return subject + "Question " + index;
//	}

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    playerPositions[howManyPlayers()] = 0;
	    coinCounts[howManyPlayers()] = 0;
	    isInPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	// how is the parameter different from the return valie of the function?
	public void turn(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isLeavingPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

				// all this stuff on feels like it could be its own function
				playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
				if (playerPositions[currentPlayer] > 11) playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12;

				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ playerPositions[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isLeavingPenaltyBox = false;
				}
			
		} else {

			// yeah see? repeated code
			playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
			if (playerPositions[currentPlayer] > 11) playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ playerPositions[currentPlayer]);
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
		if (playerPositions[currentPlayer] == 0) return "Pop";
		if (playerPositions[currentPlayer] == 4) return "Pop";
		if (playerPositions[currentPlayer] == 8) return "Pop";
		if (playerPositions[currentPlayer] == 1) return "Science";
		if (playerPositions[currentPlayer] == 5) return "Science";
		if (playerPositions[currentPlayer] == 9) return "Science";
		if (playerPositions[currentPlayer] == 2) return "Sports";
		if (playerPositions[currentPlayer] == 6) return "Sports";
		if (playerPositions[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isLeavingPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				coinCounts[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ coinCounts[currentPlayer]
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
			coinCounts[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ coinCounts[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenaltyBox[currentPlayer] = true;

		// this is doing a slightly different thing. Extract to new method and include
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(coinCounts[currentPlayer] == 6);
	}
}
