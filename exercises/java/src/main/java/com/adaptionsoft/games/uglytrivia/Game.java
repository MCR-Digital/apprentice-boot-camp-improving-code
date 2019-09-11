package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private ArrayList<Player> players = new ArrayList<>();
	private boolean[] inPenaltyBox = new boolean[6];
	private int maxNumOfQuestions = 50;
    
    private LinkedList<String> popQuestions = new LinkedList<>();
	private LinkedList<String>  scienceQuestions = new LinkedList<>();
	private LinkedList<String>  sportsQuestions = new LinkedList<>();
	private LinkedList<String>  rockQuestions = new LinkedList<>();

	private int numOfPlaces = 12;
	private String[] board = {"Pop", "Science", "Sports", "Rock"};

	private int currentPlayer = 0;
	private boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	createQuestions();
    }

	private void createQuestions(){
		for (int question = 0; question < maxNumOfQuestions; question++) {
			popQuestions.addLast("Pop Question " + question);
			scienceQuestions.addLast("Science Question " + question);
			sportsQuestions.addLast("Sports Question " + question);
			rockQuestions.addLast("Rock Question " + question);
		}
	}

	public void addPlayer(Player player) {
	    players.add(player);
		int numOfPlayers = howManyPlayers();

	    inPenaltyBox[numOfPlayers] = false;
	    
	    System.out.println(player.getName() + " was added");
	    System.out.println("They are player number " + players.size());
	}
	
	private int howManyPlayers() {
		return players.size();
	}

	public void moveOnRoll(int rollValue) {
		System.out.println(players.get(currentPlayer).getName() + " is the current player");
		System.out.println("They have rolled a " + rollValue);
		
		if (inPenaltyBox[currentPlayer]) {
			if (rollValue % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");

				movePlayer(rollValue);
				askQuestion();

				return;
			}
			System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
			
		} else {
			movePlayer(rollValue);
			askQuestion();
		}
		
	}

	private void movePlayer(int rollValue) {
    	Player theCurrentPlayer = players.get(currentPlayer);
		theCurrentPlayer.setPlaceOnBoard(rollValue);
		if (theCurrentPlayer.getPlaceOnBoard() > numOfPlaces - 1) {
			theCurrentPlayer.setPlaceOnBoard(-numOfPlaces);
		}

		System.out.println(theCurrentPlayer.getName()
				+ "'s new location is "
				+ theCurrentPlayer.getPlaceOnBoard());
		System.out.println("The category is " + currentCategory());
	}

	private void askQuestion() {
		if (currentCategory().equals("Pop"))
			System.out.println(popQuestions.removeFirst());

		if (currentCategory().equals("Science"))
			System.out.println(scienceQuestions.removeFirst());

		if (currentCategory().equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());

		if (currentCategory().equals("Rock"))
			System.out.println(rockQuestions.removeFirst());		
	}


	private String currentCategory() {
		Player theCurrentPlayer = players.get(currentPlayer);

    	int categoryIndex = theCurrentPlayer.getPlaceOnBoard() % 4;

    	return board[categoryIndex];
	}

	private void addCoin() {
		Player theCurrentPlayer = players.get(currentPlayer);

		theCurrentPlayer.addCoin();
		System.out.println(theCurrentPlayer.getName()
				+ " now has "
				+ theCurrentPlayer.getPurse()
				+ " Gold Coins.");
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				addCoin();

				boolean winner = didPlayerWin();
				goToNextPlayer();

				return winner;
			}

			goToNextPlayer();
			return true;

		} else {
			System.out.println("Answer was corrent!!!!");
			addCoin();

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}

	private void goToNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		Player theCurrentPlayer = players.get(currentPlayer);

		return theCurrentPlayer.getPurse() != 6;
	}
}
