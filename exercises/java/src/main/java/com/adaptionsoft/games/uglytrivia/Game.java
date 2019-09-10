package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int playerPosition = 0;
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
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void playerTurn(int roll) {
		System.out.println(players.get(playerPosition) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[playerPosition]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(playerPosition) + " is getting out of the penalty box");
				places[playerPosition] = places[playerPosition] + roll;
				if (places[playerPosition] > 11) places[playerPosition] = places[playerPosition] - 12;
				
				System.out.println(players.get(playerPosition)
						+ "'s new location is " 
						+ places[playerPosition]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(playerPosition) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[playerPosition] = places[playerPosition] + roll;
			if (places[playerPosition] > 11) places[playerPosition] = places[playerPosition] - 12;
			
			System.out.println(players.get(playerPosition)
					+ "'s new location is " 
					+ places[playerPosition]);
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
		if (places[playerPosition] == 0) return "Pop";
		if (places[playerPosition] == 4) return "Pop";
		if (places[playerPosition] == 8) return "Pop";
		if (places[playerPosition] == 1) return "Science";
		if (places[playerPosition] == 5) return "Science";
		if (places[playerPosition] == 9) return "Science";
		if (places[playerPosition] == 2) return "Sports";
		if (places[playerPosition] == 6) return "Sports";
		if (places[playerPosition] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[playerPosition]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[playerPosition]++;
				System.out.println(players.get(playerPosition)
						+ " now has "
						+ purses[playerPosition]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				playerPosition++;
				if (playerPosition == players.size()) playerPosition = 0;
				
				return winner;
			} else {
				playerPosition++;
				if (playerPosition == players.size()) playerPosition = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[playerPosition]++;
			System.out.println(players.get(playerPosition)
					+ " now has "
					+ purses[playerPosition]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			playerPosition++;
			if (playerPosition == players.size()) playerPosition = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(playerPosition)+ " was sent to the penalty box");
		inPenaltyBox[playerPosition] = true;
		
		playerPosition++;
		if (playerPosition == players.size()) playerPosition = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[playerPosition] == 6);
	}
}
