package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int MAX_PLAYERS = 6;
	public static final int MIN_PLAYERS = 2;
	public static final int STARTING_VALUE = 0;
	public static final int AVAILABLE_SPACES = 11;

	ArrayList players = new ArrayList();
    int[] playerPlaces = new int[MAX_PLAYERS];
    int[] playerCoins = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox  = new boolean[MAX_PLAYERS];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean leavingPenaltyBox;
    
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
		return (howManyPlayers() >= MIN_PLAYERS);
	}

	public void addPlayer(String playerName) {
		
		
	    players.add(playerName);
	    playerPlaces[howManyPlayers()] = STARTING_VALUE;
	    playerCoins[howManyPlayers()] = STARTING_VALUE;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void takeNextTurn(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (isLeavingPenaltyBox(roll)) {
				leavingPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				leavingPenaltyBox = false;
			}
		}
			
		if (!inPenaltyBox[currentPlayer] || leavingPenaltyBox) {

			int currentPlayerPlace = playerPlaces[currentPlayer] + roll;
			if (currentPlayerPlace > AVAILABLE_SPACES) currentPlayerPlace = currentPlayerPlace - 12;
			
			System.out.println(players.get(currentPlayer)
					+ "'s new location is " 
					+ currentPlayerPlace);
			playerPlaces[currentPlayer] = currentPlayerPlace;
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}

	}

	private boolean isLeavingPenaltyBox(int roll) {
		return roll % 2 != 0;
	}


	private void askQuestion() {
		switch (currentCategory()) {
			case "Pop":
				System.out.println(popQuestions.removeFirst());
				break;
			case "Science":
				System.out.println(scienceQuestions.removeFirst());
				break;
			case "Sports":
				System.out.println(sportsQuestions.removeFirst());
				break;
			case "Rock":
				System.out.println(rockQuestions.removeFirst());
				break;
			default:
				throw new IllegalArgumentException(String.format("Category invalid: %s", currentCategory()));
		}
	}
	
	
	private String currentCategory() {
		switch(playerPlaces[currentPlayer]) {
			case 0:
			case 4:
			case 8:
				return "Pop";
			case 1:
			case 5:
			case 9:
				return "Science";
			case 2:
			case 6:
			case 10:
				return "Sports";
			default:
				return "Rock";
		}
	}

	public boolean correctAnswer() {
		if (inPenaltyBox[currentPlayer]){
			if (leavingPenaltyBox) {
				return answerWasCorrect("Answer was correct!!!!");
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
			return answerWasCorrect("Answer was corrent!!!!");
		}
	}

	private boolean answerWasCorrect(String x) {
		System.out.println(x);
		playerCoins[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerCoins[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return winner;
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
		return playerCoins[currentPlayer] != 6;
	}
}
