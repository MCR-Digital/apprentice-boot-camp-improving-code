package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList();
    int[] playerPosition = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
	private int maxNumberOfCoins;

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
		int minimumNumberOfPlayers = 2;
		return (numberOfPlayers() >= minimumNumberOfPlayers);
	}

	public boolean addPlayer(Player player) {
		
		
	    players.add(player);
	    playerPosition[numberOfPlayers()] = 0;
	    purses[numberOfPlayers()] = 0;
	    inPenaltyBox[numberOfPlayers()] = false;
	    
	    System.out.println(player.getName() + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int numberOfPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				updatePlayerPositionAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			updatePlayerPositionAndAskQuestion(roll);
		}
		
	}

	private void updatePlayerPositionAndAskQuestion(int roll) {
		playerPosition[currentPlayer] = playerPosition[currentPlayer] + roll;
		if (playerPosition[currentPlayer] > 11) playerPosition[currentPlayer] = playerPosition[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerPosition[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
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

		int position = playerPosition[currentPlayer];

		boolean positionEqualsPop = position == 0 || position == 4 || position == 8;
		boolean positionEqualsScience = position == 1 || position == 5 || position == 9;
		boolean positionEqualsSports = position == 2 || position == 6 || position == 10;

		if (positionEqualsPop) return "Pop";
		else if (positionEqualsScience) return "Science";
		else if (positionEqualsSports) return "Sports";
		else return "Rock";
	}

	public boolean correctAnswer() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return updatePlayerCoins();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			return updatePlayerCoins();
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

	// Split out and rename this method
	private boolean updatePlayerCoins() {
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer).getName()
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");

		boolean gameShouldContinue = gameShouldContinue();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return gameShouldContinue;
	}

	private boolean gameShouldContinue() {
		maxNumberOfCoins = 6;
		return !(purses[currentPlayer] == maxNumberOfCoins);
	}
}
