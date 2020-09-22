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
    
    int currentPlayerIndex = 0;
    boolean isGettingOutOfPenaltyBox;
	private String currentCategory;
	private Player currentPlayer;

	public  Game(){
		new Questions().addQuestionsToList();
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
		System.out.println(getCurrentPlayer().getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		if (playersInPenaltyBox[currentPlayerIndex]) {
			isGettingOutOfPenaltyBox(roll);
		} else {
			isNotInPenaltyBox(roll);
		}
		
	}

	private void isNotInPenaltyBox(int roll) {
		playersPositions[currentPlayerIndex] = playersPositions[currentPlayerIndex] + roll;

		if (playersPositions[currentPlayerIndex] > 11) playersPositions[currentPlayerIndex] = playersPositions[currentPlayerIndex] - 12;

		System.out.println(getCurrentPlayer().getName() + "'s new location is " + playersPositions[currentPlayerIndex]);
		System.out.println("The category is " + currentCategory());
		currentCategory = currentCategory();
		printQuestionByCategory(currentCategory);
	}

	private void isGettingOutOfPenaltyBox(int roll) {
		if (roll % 2 != 0) {
			isGettingOutOfPenaltyBox = true;
			System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
			isNotInPenaltyBox(roll);
		} else {
			System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
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
		switch ((playersPositions[currentPlayerIndex])){
			case 0: case 4: case 8: return "Pop";
			case 1: case 5: case 9: return "Science";
			case 2: case 6: case 10: return "Sports";
			default: return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (playersInPenaltyBox[currentPlayerIndex]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playersPurses[currentPlayerIndex]++;
				boolean winner = isWinner();
				changeToNextPlayer();
				return winner;
			} else {
				changeToNextPlayer();
				return true;
			}
		} else {
			System.out.println("Answer was corrent!!!!");
			playersPurses[currentPlayerIndex]++;
			boolean winner = isWinner();
			changeToNextPlayer();

			return winner;
		}
	}

	private void changeToNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		currentPlayer = new Player(players.get(currentPlayerIndex));
	}

	private boolean isWinner() {
		System.out.println(getCurrentPlayer().getName() + " now has " + playersPurses[currentPlayerIndex] + " Gold Coins.");
		return didPlayerWin();
	}

	private Player getCurrentPlayer() {
		return new Player(players.get(currentPlayerIndex));
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
		playersInPenaltyBox[currentPlayerIndex] = true;

		changeToNextPlayer();
		return true;
	}


	private boolean didPlayerWin() {
		return !(playersPurses[currentPlayerIndex] == 6);
	}

	private class Questions {

		public void addQuestionsToList() {
			for (int i = 0; i < 50; i++) {
				popQuestions.addLast("Pop Question " + i);
				scienceQuestions.addLast(("Science Question " + i));
				sportsQuestions.addLast(("Sports Question " + i));
				rockQuestions.addLast("Rock Question " + i);
			}
		}
	}
}



 class Player {

	private String name;


	Player(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}