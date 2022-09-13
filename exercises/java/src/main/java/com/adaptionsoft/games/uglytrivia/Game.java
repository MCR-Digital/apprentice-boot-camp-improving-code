package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	public static final int NUMBER_OF_COINS_TO_WIN = 6;
	List<Player> players = new ArrayList<>();
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int count = 0; count < 50; count++) {
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

	public void addPlayerToGame(Player player) {
	    players.add(player);
	    
	    System.out.println(player.getPlayerName() + " was added");
	    System.out.println("They are player number " + howManyPlayers());
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int numberRolled) {
		System.out.println(players.get(currentPlayer).getPlayerName() + " is the current player");
		System.out.println("They have rolled a " + numberRolled);
		
		if (players.get(currentPlayer).isInPenaltyBox()) {
			if (numberRolled % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer).getPlayerName() + " is getting out of the penalty box");
				players.get(currentPlayer).playerMoves(numberRolled);

				System.out.println("The category is " + currentCategoryOnTile());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer).getPlayerName() + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
		} else {
			players.get(currentPlayer).playerMoves(numberRolled);

			System.out.println("The category is " + currentCategoryOnTile());
			askQuestion();
		}
	}

	private void askQuestion() {
		if (currentCategoryOnTile() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategoryOnTile() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategoryOnTile() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategoryOnTile() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	
	private String currentCategoryOnTile() {
		int playersCurrentTile = players.get(currentPlayer).getCurrentTile();
		if(playersCurrentTile % 4 == 0) return "Pop";
		if(playersCurrentTile % 4 == 1) return "Science";
		if(playersCurrentTile % 4 == 2) return "Sports";
		return "Rock";
	}

	public boolean checkAnswer() {
		if (players.get(currentPlayer).isInPenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				return correctAnswer("Answer was correct!!!!");
			} else {
				currentPlayer++;
				if (currentPlayer == howManyPlayers()) currentPlayer = 0;
				return true;
			}
		} else {
			return correctAnswer("Answer was corrent!!!!");
		}
	}

	private boolean correctAnswer(String message) {
		System.out.println(message);
		players.get(currentPlayer).addCoins(1);
		players.get(currentPlayer).displayCoinCount();
		boolean isPlayerWinner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == howManyPlayers()) currentPlayer = 0;

		return isPlayerWinner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer).getPlayerName() + " was sent to the penalty box");
		players.get(currentPlayer).putInPenaltyBox();
		
		currentPlayer++;
		if (currentPlayer == howManyPlayers()) currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(players.get(currentPlayer).getCoins() == NUMBER_OF_COINS_TO_WIN);
	}
}
