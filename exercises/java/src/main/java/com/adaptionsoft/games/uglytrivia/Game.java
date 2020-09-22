package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	public static final int SIZE = 6;
	ArrayList<String> players = new ArrayList<>();
    int[] playersPositions = new int[SIZE];
    int[] playersPurses = new int[SIZE];
    boolean[] playersInPenaltyBox = new boolean[SIZE];
    
    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
	private String currentCategory;

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


	public void addPlayerNames(String playerName) {
	    players.add(playerName);
	    playersPositions[howManyPlayers()] = 0;
	    playersPurses[howManyPlayers()] = 0;
	    playersInPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void playTrivia(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		if (playersInPenaltyBox[currentPlayer]) {
			isGettingOutOfPenaltyBox(roll);
		} else {
			isNotInPenaltyBox(roll);
		}
		
	}

	private void isNotInPenaltyBox(int roll) {
		playersPositions[currentPlayer] = playersPositions[currentPlayer] + roll;

		if (playersPositions[currentPlayer] > 11) playersPositions[currentPlayer] = playersPositions[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer) + "'s new location is " + playersPositions[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		currentCategory = currentCategory();
		printQuestionByCategory(currentCategory);
	}

	private void isGettingOutOfPenaltyBox(int roll) {
		if (roll % 2 != 0) {
			isGettingOutOfPenaltyBox = true;
			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			isNotInPenaltyBox(roll);
		} else {
			System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
			}
	}

	private void printQuestionByCategory(String currentCategory) {
    	switch (currentCategory){
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
		}
	}


	private String currentCategory() {
		switch ((playersPositions[currentPlayer])){
			case 0: case 4: case 8: return "Pop";
			case 1: case 5: case 9: return "Science";
			case 2: case 6: case 10: return "Sports";
			default: return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (playersInPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playersPurses[currentPlayer]++;
				boolean winner = isWinner();
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
			boolean winner = isWinner();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}

	private boolean isWinner() {
		System.out.println(players.get(currentPlayer) + " now has " + playersPurses[currentPlayer] + " Gold Coins.");
		return didPlayerWin();
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		playersInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playersPurses[currentPlayer] == 6);
	}
}
