package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList playerNames = new ArrayList();
    int[] boardPlace = new int[6];
    int[] playerPurses = new int[6];
    boolean[] playerInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int index = 0; index < 50; index++) {
			popQuestions.addLast("Pop Question " + index);
			scienceQuestions.addLast(("Science Question " + index));
			sportsQuestions.addLast(("Sports Question " + index));
			rockQuestions.addLast(createRockQuestion(index));
    	}
    }

	public String createRockQuestion(int index){
    	return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
    	return (numberOfPlayers() >= 2);
	}

	public boolean addPlayerToGame(String playerName) {
		
		
	    playerNames.add(playerName);
	    boardPlace[numberOfPlayers()] = 0;
	    playerPurses[numberOfPlayers()] = 0;
	    playerInPenaltyBox[numberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + playerNames.size());
		return true;
	}
	
	public int numberOfPlayers() {
    	return playerNames.size();
	}

	public void roll(int roll) {
		System.out.println(playerNames.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (playerInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(playerNames.get(currentPlayer) + " is getting out of the penalty box");
				boardPlace[currentPlayer] = boardPlace[currentPlayer] + roll;
				if (boardPlace[currentPlayer] > 11) boardPlace[currentPlayer] = boardPlace[currentPlayer] - 12;
				
				System.out.println(playerNames.get(currentPlayer)
						+ "'s new location is " 
						+ boardPlace[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(playerNames.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			boardPlace[currentPlayer] = boardPlace[currentPlayer] + roll;
			if (boardPlace[currentPlayer] > 11) boardPlace[currentPlayer] = boardPlace[currentPlayer] - 12;
			
			System.out.println(playerNames.get(currentPlayer)
					+ "'s new location is " 
					+ boardPlace[currentPlayer]);
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
		if (boardPlace[currentPlayer] == 0) return "Pop";
		if (boardPlace[currentPlayer] == 4) return "Pop";
		if (boardPlace[currentPlayer] == 8) return "Pop";
		if (boardPlace[currentPlayer] == 1) return "Science";
		if (boardPlace[currentPlayer] == 5) return "Science";
		if (boardPlace[currentPlayer] == 9) return "Science";
		if (boardPlace[currentPlayer] == 2) return "Sports";
		if (boardPlace[currentPlayer] == 6) return "Sports";
		if (boardPlace[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (playerInPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playerPurses[currentPlayer]++;
				System.out.println(playerNames.get(currentPlayer)
						+ " now has "
						+ playerPurses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == playerNames.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == playerNames.size()) currentPlayer = 0;
				return true;
			}

		} else {
		
			System.out.println("Answer was corrent!!!!");
			playerPurses[currentPlayer]++;
			System.out.println(playerNames.get(currentPlayer)
					+ " now has "
					+ playerPurses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == playerNames.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(playerNames.get(currentPlayer)+ " was sent to the penalty box");
		playerInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == playerNames.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playerPurses[currentPlayer] == 6);
	}
}
