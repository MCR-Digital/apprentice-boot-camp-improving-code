package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Game {
	static final int NUMBER_OF_BOARD_PLACES = 12;
	ArrayList<Player> players = new ArrayList<>();
	int[] purses  = new int[6];
    boolean[] penaltyShoes = new boolean[6];
	Board gameBoard = new Board();

	// Create game categories - lists of questions
    Map<String, LinkedList<Question>> questionDecks = new HashMap<>();
    
    int currentPlayer = 0;
    boolean isTakingOffPenaltyShoes;
    
    public Game(){
		LinkedList<Question> popDeck = new LinkedList<>();
		LinkedList<Question> scienceDeck = new LinkedList<>();
		LinkedList<Question> sportsDeck = new LinkedList<>();
		LinkedList<Question> rockDeck = new LinkedList<>();
		// Generate 50 questions for each game category
    	for (int questionIndex = 0; questionIndex < 50; questionIndex++) {
			popDeck.addLast(new Question("Pop", questionIndex));
			scienceDeck.addLast(new Question("Science", questionIndex));
			sportsDeck.addLast(new Question("Sports", questionIndex));
			rockDeck.addLast(new Question("Rock", questionIndex));
    	}
		questionDecks.put("Pop",popDeck);
		questionDecks.put("Science",scienceDeck);
		questionDecks.put("Sports",sportsDeck);
		questionDecks.put("Rock",rockDeck);
    }

	public void addPlayer(Player newPlayer) {
		
		// Add player to the game and set default values
	    players.add(newPlayer);
		int numberOfCurrentPlayers = players.size();

	    purses[numberOfCurrentPlayers] = 0;
	    penaltyShoes[numberOfCurrentPlayers] = false;
	    
	    System.out.println(newPlayer + " was added");
	    System.out.println("They are player number " + numberOfCurrentPlayers);
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		// If the player is in the penalty box
		if (penaltyShoes[currentPlayer]) {
			// If their roll is odd they can get out of the penalty box
			if (isRollOdd(roll)) {
				isTakingOffPenaltyShoes = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

				int newPlayerPlaces = players.get(currentPlayer).movePlaces(roll);
				System.out.println(players.get(currentPlayer)
						+ "'s new location is " 
						+ newPlayerPlaces);
				System.out.println("The category is " + currentCategory());

				// Ask the player a question
				askQuestion();

			// If they roll an even number, the player stays in the penalty box
			} else {

				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isTakingOffPenaltyShoes = false;
				}

		// If the player is not in the penalty box
		} else {
			int newPlayerPlaces = players.get(currentPlayer).movePlaces(roll);
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ newPlayerPlaces);
			System.out.println("The category s " + currentCategory());

			// Ask the player a question
			askQuestion();
		}
		
	}

	private boolean isRollOdd(int roll) {
		return roll % 2 != 0;
	}

	private void askQuestion() {
		System.out.println(questionDecks.get(currentCategory()).removeFirst());
	}

	// 0,4,8: Pop question
	// 1,5,9: Science question
	// 2,6,10: Sports question
	// 3,7,11: Rock question
	private String currentCategory() {
		return gameBoard.getCurrentCategory(players.get(currentPlayer).getPlaces());
	}

	public boolean wasCorrectlyAnswered() {
		// If player is in the penalty box
		if (penaltyShoes[currentPlayer]){
			// If they are getting out of the box
			if (isTakingOffPenaltyShoes) {
				System.out.println("Answer was correct!!!!");
				// Increase gold by 1
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				// If they've got 6 coins, winner=False, else winner=True
				boolean winner = playerDoesntHaveSixCoins();

				// Change to next player
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;

				// If they've got 6 coins, winner=False, else winner=True
				return winner;

			// If the player is not in the penalty box move to next player and return true
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			

		// If player is not in penalty box
		} else {
		
			System.out.println("Answer was corrent!!!!");

			// Increase gold by 1
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			// If they've got 6 coins, winner=False, else winner=True
			boolean doesntHaveSixCoins = playerDoesntHaveSixCoins();

			// Next player
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			// If they've got 6 coins, winner=False, else winner=True
			return doesntHaveSixCoins;
		}
	}

	// If answered wrong, put the player in the penalty box and move to next player
	public boolean penalisePlayer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		penaltyShoes[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean playerDoesntHaveSixCoins() {
		return !(purses[currentPlayer] == 6);
	}
}
