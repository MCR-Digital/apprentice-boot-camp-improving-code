package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    
    Board board = new Board();

	int[] places = new int[6];
	int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

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


	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void rollDice(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				moveCurrentPlayer(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}

		} else {

			moveCurrentPlayer(roll);
		}

	}

	private void moveCurrentPlayer(int roll) {
		places[currentPlayer] = getCurrentPlayerPosition() + roll;
		if (getCurrentPlayerPosition() > 11) places[currentPlayer] = getCurrentPlayerPosition() - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ getCurrentPlayerPosition());
		askQuestion();
	}


	public int getCurrentPlayerPosition() {
		return places[currentPlayer];
	}

	public boolean currentPlayerGetsAnswerRight() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return addCoinToCurrentPlayersPurse();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
			return addCoinToCurrentPlayersPurse();
		}
	}

	public boolean currentPlayerGetsAnswerWrong(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

	private boolean addCoinToCurrentPlayersPurse() {
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ purses[currentPlayer]
				+ " Gold Coins.");

		boolean isGameContinuing = isGameContinuing();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return isGameContinuing;
	}


	private boolean isGameContinuing() {
		return !(purses[currentPlayer] == 6);
	}

	public void askQuestion() {
		String category = board.getCategoryFromPosition(getCurrentPlayerPosition());
		System.out.println("The category is " + category);
		if (category.equals("Pop"))
			System.out.println(popQuestions.removeFirst());
		if (category.equals("Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (category.equals("Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (category.equals("Rock"))
			System.out.println(rockQuestions.removeFirst());
	}
}
