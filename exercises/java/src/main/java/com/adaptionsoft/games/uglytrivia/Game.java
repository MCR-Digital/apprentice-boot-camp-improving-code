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

	public boolean addPlayer(String playerName) {
		
		int initialPlayerBoardPosition = 0;
		int initialPlayerCoinCount = 0;
	    players.add(playerName);
	    playerBoardPosition[howManyPlayers()] = initialPlayerBoardPosition;
	    playerCoinCount[howManyPlayers()] = initialPlayerCoinCount;
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
			if (isOdd(roll)) {
				isPlayerGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				updatePosition(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isPlayerGettingOutOfPenaltyBox = false;
				}
			
		} else {

			updatePosition(roll);
		}
		
	}

	private boolean isOdd(int roll) {
		return roll % 2 != 0;
	}

	private void updatePosition(int roll) {
		playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] + roll;
		if (playerBoardPosition[currentPlayer] > 11)
			playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerBoardPosition[currentPlayer]);
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
		if (playerBoardPosition[currentPlayer] == 0 || playerBoardPosition[currentPlayer] == 4 || playerBoardPosition[currentPlayer] == 8)
			return "Pop";
		if (playerBoardPosition[currentPlayer] == 1 || playerBoardPosition[currentPlayer] == 5 || playerBoardPosition[currentPlayer] == 9)
			return "Science";
		if (playerBoardPosition[currentPlayer] == 2 || playerBoardPosition[currentPlayer] == 6 || playerBoardPosition[currentPlayer] == 10)
			return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isPlayerInPenaltyBox[currentPlayer]){
			if (isPlayerGettingOutOfPenaltyBox) {
				String message = "Answer was correct!!!!";
				updatePlayerCoinCount(message);

				boolean didPlayerWin = didPlayerWin();
				changeCurrentPlayer();

				return didPlayerWin;
			} else {
				changeCurrentPlayer();
				return true;
			}
			
			
			
		} else {

			String message = "Answer was corrent!!!!";
			updatePlayerCoinCount(message);

			boolean didPlayerWin = didPlayerWin();
			changeCurrentPlayer();

			return didPlayerWin;
		}
	}

	private void changeCurrentPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private void updatePlayerCoinCount(String message) {
		System.out.println(message);
		playerCoinCount[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerCoinCount[currentPlayer]
				+ " Gold Coins.");
	}

	public boolean wasIncorrectlyAnswered(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isPlayerInPenaltyBox[currentPlayer] = true;

		changeCurrentPlayer();
		return true;
	}


	private boolean didPlayerWin() {
		return !(playerCoinCount[currentPlayer] == 6);
	}
}
