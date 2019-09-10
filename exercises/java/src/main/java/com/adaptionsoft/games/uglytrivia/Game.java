package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
	private static final String POP = "Pop";
	private static final String SCIENCE = "Science";
	private static final String SPORTS = "Sports";
	private static final String ROCK = "Rock";

	ArrayList players = new ArrayList();
    int[] locationOnBoard = new int[6];
    int[] goldCoins = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isExitingPenaltyBox;

    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast(POP + " Question " + i);
			scienceQuestions.addLast((SCIENCE + " Question " + i));
			sportsQuestions.addLast((SPORTS + " Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return ROCK + " Question " + index;
	}

	public boolean isPlayable() {
		return (numberOfPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {

	    players.add(playerName);
	    locationOnBoard[numberOfPlayers()] = 0;
	    goldCoins[numberOfPlayers()] = 0;
	    isInPenaltyBox[numberOfPlayers()] = false;

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	public int numberOfPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isExitingPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                takeTurn(roll);
            } else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isExitingPenaltyBox = false;
			}
		} else {
            takeTurn(roll);
        }

	}

    private void takeTurn(int roll) {
        locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] + roll;
        if (locationOnBoard[currentPlayer] > 11) locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] - 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + locationOnBoard[currentPlayer]);
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
		if (currentCategory() == POP)
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == SCIENCE)
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == SPORTS)
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == ROCK)
			System.out.println(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		if (locationOnBoard[currentPlayer] == 0) return POP;
		if (locationOnBoard[currentPlayer] == 4) return POP;
		if (locationOnBoard[currentPlayer] == 8) return POP;
		if (locationOnBoard[currentPlayer] == 1) return SCIENCE;
		if (locationOnBoard[currentPlayer] == 5) return SCIENCE;
		if (locationOnBoard[currentPlayer] == 9) return SCIENCE;
		if (locationOnBoard[currentPlayer] == 2) return SPORTS;
		if (locationOnBoard[currentPlayer] == 6) return SPORTS;
		if (locationOnBoard[currentPlayer] == 10) return SPORTS;
		return ROCK;
	}

	public boolean isCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isExitingPenaltyBox) {
				System.out.println("Answer was correct!!!!");
                return hasPlayerWon();
            } else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
            return hasPlayerWon();
        }
	}

    public boolean isIncorrectlyAnswered(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}

    private boolean hasPlayerWon() {
        goldCoins[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + goldCoins[currentPlayer]
                + " Gold Coins.");

        boolean isWinner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return isWinner;
    }


	private boolean didPlayerWin() {
		return !(goldCoins[currentPlayer] == 6);
	}
}
