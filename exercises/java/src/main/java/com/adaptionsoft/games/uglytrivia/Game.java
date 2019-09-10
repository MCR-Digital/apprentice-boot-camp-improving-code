package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] placeOnTheBoard = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
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
		return (amountOfPlayers() >= 2);
	}

	public boolean addingPlayer(String playerName) {
		
		
	    players.add(playerName);
	    placeOnTheBoard[amountOfPlayers()] = 0;
	    purses[amountOfPlayers()] = 0;
	    inPenaltyBox[amountOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int amountOfPlayers() {
		return players.size();
	}

	public void rollTheDice(int numberOnDiceAfterRoll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + numberOnDiceAfterRoll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (numberOnDiceAfterRoll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] + numberOnDiceAfterRoll;
				if (placeOnTheBoard[currentPlayer] > 11) placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ placeOnTheBoard[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] + numberOnDiceAfterRoll;
			if (placeOnTheBoard[currentPlayer] > 11) placeOnTheBoard[currentPlayer] = placeOnTheBoard[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ placeOnTheBoard[currentPlayer]);
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
		if (placeOnTheBoard[currentPlayer] == 0) return "Pop";
		if (placeOnTheBoard[currentPlayer] == 4) return "Pop";
		if (placeOnTheBoard[currentPlayer] == 8) return "Pop";
		if (placeOnTheBoard[currentPlayer] == 1) return "Science";
		if (placeOnTheBoard[currentPlayer] == 5) return "Science";
		if (placeOnTheBoard[currentPlayer] == 9) return "Science";
		if (placeOnTheBoard[currentPlayer] == 2) return "Sports";
		if (placeOnTheBoard[currentPlayer] == 6) return "Sports";
		if (placeOnTheBoard[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ purses[currentPlayer]
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
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
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
		return !(purses[currentPlayer] == 6);
	}
}
