package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] positionOnBoard = new int[6];
    int[] score = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int player = 0;
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
		return (numberOfPlayers() >= 2);
	}

	public boolean addPlayer(String playerName) {

		
	    players.add(playerName);
	    positionOnBoard[numberOfPlayers()] = 0;
	    score[numberOfPlayers()] = 0;
	    inPenaltyBox[numberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int numberOfPlayers() {
		return players.size();
	}

	public void playerTurn(int diceRoll) {
		System.out.println(players.get(player) + " is the current player");
		System.out.println("They have rolled a " + diceRoll);
		
		if (inPenaltyBox[player]) {
			if (isOdd(diceRoll)) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(player) + " is getting out of the penalty box");

				movePlayer(diceRoll);
                displayPlayerPosition();
				askQuestion();

			} else {
				System.out.println(players.get(player) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
            movePlayer(diceRoll);
            displayPlayerPosition();
			askQuestion();
		}
		
	}

    private boolean isOdd(int diceRoll) {
        return diceRoll % 2 != 0;
    }

    private void displayPlayerPosition() {
        System.out.println(players.get(player)
                + "'s new location is "
                + positionOnBoard[player]);
    }

    private void movePlayer(int diceRoll) {
        positionOnBoard[player] = positionOnBoard[player] + diceRoll;
        if (positionOnBoard[player] > 11) {
            returnToBeginningOfBoard();
        }
    }

    private void returnToBeginningOfBoard() {
        positionOnBoard[player] = positionOnBoard[player] - 12;
    }

    private void askQuestion() {
		System.out.println("The category is " + currentCategory());
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
		if (positionOnBoard[player] == 0) return "Pop";
		if (positionOnBoard[player] == 4) return "Pop";
		if (positionOnBoard[player] == 8) return "Pop";
		if (positionOnBoard[player] == 1) return "Science";
		if (positionOnBoard[player] == 5) return "Science";
		if (positionOnBoard[player] == 9) return "Science";
		if (positionOnBoard[player] == 2) return "Sports";
		if (positionOnBoard[player] == 6) return "Sports";
		if (positionOnBoard[player] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[player]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				score[player]++;
				System.out.println(players.get(player)
						+ " now has "
						+ score[player]
						+ " Gold Coins.");
				
				boolean winner = isWinner();
				player++;
				if (player == players.size()) player = 0;
				
				return winner;
			} else {
				player++;
				if (player == players.size()) player = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			score[player]++;
			System.out.println(players.get(player)
					+ " now has "
					+ score[player]
					+ " Gold Coins.");
			
			boolean winner = isWinner();
			player++;
			if (player == players.size()) player = 0;
			
			return winner;
		}
	}
	
	public boolean isWrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(player)+ " was sent to the penalty box");
		inPenaltyBox[player] = true;
		
		player++;
		if (player == players.size()) player = 0;
		return true;
	}


	private boolean isWinner() {
		return !(score[player] == 6);
	}
}
