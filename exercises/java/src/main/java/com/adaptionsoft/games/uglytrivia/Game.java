package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    ArrayList<Player> players2 = new ArrayList<>();
    int[] playerBoardPosition = new int[6];
    
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
			popQuestions.addLast(createQuestion("Pop", i));
			scienceQuestions.addLast((createQuestion("Science", i)));
			sportsQuestions.addLast((createQuestion("Sports", i)));
			rockQuestions.addLast(createQuestion("Rock", i));
    	}
    	topicQuestionMap.put("Pop", popQuestions);
    	topicQuestionMap.put("Science", scienceQuestions);
    	topicQuestionMap.put("Sports", sportsQuestions);
    	topicQuestionMap.put("Rock", rockQuestions);
    }

	private String createQuestion(String topic, int index) {
    	return topic + " Question " + index;
	}
	
	public boolean isPlayable() {
		int miniumumNumberOfPlayers = 2;
    	return (howManyPlayers() >= miniumumNumberOfPlayers);
	}

	public boolean addPlayer(String playerName) {
		
		int initialPlayerBoardPosition = 0;
	    players.add(playerName);
	    playerBoardPosition[howManyPlayers()] = initialPlayerBoardPosition;
	    players2.add(new Player(playerName));
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players2.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players2.size();
	}

	public void roll(int roll) {
		System.out.println(players2.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (players2.get(currentPlayer).isInPenaltyBox()) {
			if (isOdd(roll)) {
				isPlayerGettingOutOfPenaltyBox = true;
				
				System.out.println(players2.get(currentPlayer).getName() + " is getting out of the penalty box");
				updatePosition(roll);
			} else {
				System.out.println(players2.get(currentPlayer).getName() + " is not getting out of the penalty box");
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

		System.out.println(players2.get(currentPlayer).getName()
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
		if (players2.get(currentPlayer).isInPenaltyBox()){
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
		players2.get(currentPlayer).incrementCoinCount();
		System.out.println(players2.get(currentPlayer).getName()
				+ " now has "
				+ players2.get(currentPlayer).getCoinCount()
				+ " Gold Coins.");
	}

	public boolean wasIncorrectlyAnswered(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players2.get(currentPlayer).getName()+ " was sent to the penalty box");
		players2.get(currentPlayer).setInPenaltyBox(true);

		changeCurrentPlayer();
		return true;
	}


	private boolean didPlayerWin() {
    	return !(players2.get(currentPlayer).getCoinCount() ==6);
	}
}
