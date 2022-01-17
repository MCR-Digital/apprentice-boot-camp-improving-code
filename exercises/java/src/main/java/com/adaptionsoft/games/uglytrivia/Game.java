package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] penaltyShoes = new boolean[6];

	// Create game categories - lists of questions
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isTakingOffPenaltyShoes;
    
    public Game(){
		// Generate 50 questions for each game category
    	for (int questionIndex = 0; questionIndex < 50; questionIndex++) {
			popQuestions.addLast("Pop Question " + questionIndex);
			scienceQuestions.addLast(("Science Question " + questionIndex));
			sportsQuestions.addLast(("Sports Question " + questionIndex));
			rockQuestions.addLast("Rock Question " + questionIndex);
    	}
    }

	// If there are 2 or more players, the game can be played
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		// Add player to the game and set default values
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    penaltyShoes[howManyPlayers()] = false;
	    
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

		// If the player is in the penalty box
		if (penaltyShoes[currentPlayer]) {
			// If their roll is odd they can get out of the penalty box
			if (roll % 2 != 0) {
				isTakingOffPenaltyShoes = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

				// Increase their number of places by their roll
				places[currentPlayer] = places[currentPlayer] + roll;

				// If their new number of places is 12 or more, reduce it by 12
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
				
				System.out.println(players.get(currentPlayer) 
						+ "'s new location is " 
						+ places[currentPlayer]);
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
			// Increase the player's places by their roll
			places[currentPlayer] = places[currentPlayer] + roll;

			// If the new number of places is 12 or more, reduce it by 12
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			System.out.println(players.get(currentPlayer) 
					+ "'s new location is " 
					+ places[currentPlayer]);
			System.out.println("The category s " + currentCategory());

			// Ask the player a question
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

	// 0,4,8: Pop question
	// 1,5,9: Science question
	// 2,6,10: Sports question
	// 3,7,11: Rock question
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
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
