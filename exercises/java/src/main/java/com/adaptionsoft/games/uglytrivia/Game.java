package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    ArrayList players = new ArrayList();
    int[] gamesCurrentTile = new int[6];
    int[] playersPurses = new int[6];
    boolean[] isInPenatlyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isPlayerGettingOutOfPenaltyBox;
    
    public  Game(int numberOfQuestions){
    	for (int count = 0; count < numberOfQuestions; count++) {
			popQuestions.addLast("Pop Question " + count);
			scienceQuestions.addLast(("Science Question " + count));
			sportsQuestions.addLast(("Sports Question " + count));
			rockQuestions.addLast(createRockQuestion(count));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		
		
	    players.add(playerName);
	    gamesCurrentTile[howManyPlayers()] = 0;
	    playersPurses[howManyPlayers()] = 0;
	    isInPenatlyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void takeNextTurn(int numberRolled) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + numberRolled);
		
		if (isInPenatlyBox[currentPlayer]) {

			leavingPenaltyBox(numberRolled);

		} else {

			playersRolls(numberRolled);
			askQuestion();
		}
		
	}

	private void leavingPenaltyBox(int numberRolled) {
		if (numberRolled % 2 != 0) {
			isPlayerGettingOutOfPenaltyBox = true;

			System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			playersRolls(numberRolled);
			askQuestion();
		} else {
			System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
			isPlayerGettingOutOfPenaltyBox = false;
			}
	}

	private void playersRolls(int roll) {
		gamesCurrentTile[currentPlayer] = gamesCurrentTile[currentPlayer] + roll;
		if (gamesCurrentTile[currentPlayer] > 11)
			gamesCurrentTile[currentPlayer] = gamesCurrentTile[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ gamesCurrentTile[currentPlayer]);
		System.out.println("The category is " + currentCategory());
	}

	private void askQuestion() {
		if (currentCategory() == "Pop") {
			printAndRemoveQuestion(popQuestions);
		}
		if (currentCategory() == "Science")
			printAndRemoveQuestion(scienceQuestions);
		if (currentCategory() == "Sports")
			printAndRemoveQuestion(sportsQuestions);
		if (currentCategory() == "Rock")
			printAndRemoveQuestion(rockQuestions);
	}

	private void printAndRemoveQuestion(LinkedList questions) {
		System.out.println(questions.removeFirst());
	}


	private String currentCategory() {
		int playerCurrentTile = gamesCurrentTile[currentPlayer];

		List<String> gameBoard = new ArrayList<String>();
		for(int i = 0; i<4 ; i++){
			gameBoard.add("Pop");
			gameBoard.add("Science");
			gameBoard.add("Sports");
			gameBoard.add("Rock");
		}

		return gameBoard.get(playerCurrentTile);



//		if (playerCurrentTile == 0) return "Pop";
//		else if (playerCurrentTile == 1) return "Science";
//		else if (playerCurrentTile == 2) return "Sports";
//		else if (playerCurrentTile == 3) return "Rock";
//		else if (playerCurrentTile == 4) return "Pop";
//		else if (playerCurrentTile == 5) return "Science";
//		else if (playerCurrentTile == 6) return "Sports";
//		else if (playerCurrentTile == 7) return "Rock";
//		else if (playerCurrentTile == 8) return "Pop";
//		else if (playerCurrentTile == 9) return "Science";
//		else if (playerCurrentTile == 10) return "Sports";
//		else if (playerCurrentTile == 11) return "Rock";
//		else{
//		throw new IllegalStateException("No longer in the board"); }
	};

	public boolean wasCorrectlyAnswered() {
		if (isInPenatlyBox[currentPlayer]){
			if (isPlayerGettingOutOfPenaltyBox) {
				return correctAnswer("Answer was correct!!!!");
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
		} else {

			return correctAnswer("Answer was corrent!!!!");
		}
	}

	private boolean correctAnswer(String message) {
		System.out.println(message);
		playersPurses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playersPurses[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenatlyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(playersPurses[currentPlayer] == 6);
	}
}
