package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();

    
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

	public boolean addPlayers(Player playerName) {
	    players.add(playerName);
	    System.out.println(playerName.getPlayerName() + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void takeNextTurn(int numberRolled) {
		System.out.println(players.get(currentPlayer).getPlayerName() + " is the current player");
		System.out.println("They have rolled a " + numberRolled);
		
		if (players.get(currentPlayer).getPenaltyBox()){

			leavingPenaltyBox(numberRolled);

		} else {

			players.get(currentPlayer).playersRolls(numberRolled);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void leavingPenaltyBox(int numberRolled) {
		if (numberRolled % 2 != 0) {
			isPlayerGettingOutOfPenaltyBox = true;

			System.out.println(players.get(currentPlayer).getPlayerName() + " is getting out of the penalty box");
			players.get(currentPlayer).playersRolls(numberRolled);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		} else {
			System.out.println(players.get(currentPlayer).getPlayerName() + " is not getting out of the penalty box");
			isPlayerGettingOutOfPenaltyBox = false;
			}
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
		int currentTile = players.get(currentPlayer).getPosition();
		List<String> gameBoard = Arrays.asList(
				"Pop", "Science", "Sports", "Rock",
				"Pop", "Science", "Sports", "Rock",
				"Pop", "Science", "Sports", "Rock");

		return gameBoard.get(currentTile);
	}

	public boolean wasCorrectlyAnswered() {
		if (players.get(currentPlayer).getPenaltyBox()){
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
		players.get(currentPlayer).addCoins(1);
		System.out.println(players.get(currentPlayer).getPlayerName()
				+ " now has "
				+ players.get(currentPlayer).getCoins()
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getPlayerName() + " was sent to the penalty box");
		players.get(currentPlayer).setPenaltyBox(true);;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(players.get(currentPlayer).getCoins() == 6);
	}
}


