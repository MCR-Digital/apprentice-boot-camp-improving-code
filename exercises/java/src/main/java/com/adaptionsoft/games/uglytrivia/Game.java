package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.HashMap;
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

    HashMap<String, LinkedList> topicQuestionMap = new HashMap<>();
    String[] board= {"Pop", "Science", "Sports", "Rock", "Pop", "Science", "Sports", "Rock", "Pop", "Science", "Sports", "Rock"};
    
    int currentPlayer = 0;
    boolean isPlayerGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    	topicQuestionMap.put("Pop", popQuestions);
    	topicQuestionMap.put("Science", scienceQuestions);
    	topicQuestionMap.put("Sports", sportsQuestions);
    	topicQuestionMap.put("Rock", rockQuestions);
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		int miniumumNumberOfPlayers = 2;
    	return (howManyPlayers() >= miniumumNumberOfPlayers);
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
    	int numberOfBoardPositions = 12;
		playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] + roll;
		if (playerBoardPosition[currentPlayer] >= numberOfBoardPositions)
			playerBoardPosition[currentPlayer] = playerBoardPosition[currentPlayer] - numberOfBoardPositions;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerBoardPosition[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		System.out.println(topicQuestionMap.get(currentCategory()).removeFirst());
	}
	
	
	private String currentCategory() {
    	int currentPosition = playerBoardPosition[currentPlayer];
		return board[currentPosition];
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
