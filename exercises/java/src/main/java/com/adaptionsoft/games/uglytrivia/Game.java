package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] playerPositions = new int[6]; // array
    int[] playerPurses = new int[6]; // array
    boolean[] playerInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public Game(){
		setUpQuestions();
    }

	private void setUpQuestions() {
		for (int questionNumber = 0; questionNumber < 50; questionNumber++) {
			popQuestions.addLast("Pop Question " + questionNumber);
			scienceQuestions.addLast(("Science Question " + questionNumber));
			sportsQuestions.addLast(("Sports Question " + questionNumber));
			rockQuestions.addLast("Rock Question " + questionNumber);
		}
	}

	public boolean addPlayer(String playerName) {
	    players.add(playerName);
		resetPlayer(howManyPlayers());
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	private void resetPlayer(int number) {
		resetPlayerPosition(number);
		resetPlayerPurse(number);
		resetPlayerInPenaltyBox(number);
	}

	private void resetPlayerInPenaltyBox(int number) {
		playerInPenaltyBox[number] = false;
	}

	private void resetPlayerPurse(int number) {
		playerPurses[number] = 0; //0 TO VARIABLE
	}

	private void resetPlayerPosition(int number) {
		playerPositions[number] = 0; //0 TO VARIABLE
	}


	//AMOUNTOFPLAYERS
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (playerInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) { //EXTRACT TO VARIABLE "isRoleOdd"
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				return;
			}
		}
		playerRolls(roll);
	}

	private void playerRolls(int roll) {
		playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll;
		if (playerPositions[currentPlayer] > 11) {
			playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12; // EXPLANATION?
		}

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerPositions[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
    	//CASE?
		if (currentCategory() == QuestionTypes.Pop)
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == QuestionTypes.Science)
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == QuestionTypes.Sports)
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == QuestionTypes.Rock)
			System.out.println(rockQuestions.removeFirst());
	}
	
	
	private QuestionTypes currentCategory() {
    	//FOR LOOP UP TO 11
		if (playerPositions[currentPlayer] == 0) return QuestionTypes.Pop;
		if (playerPositions[currentPlayer] == 4) return QuestionTypes.Pop;
		if (playerPositions[currentPlayer] == 8) return QuestionTypes.Pop;
		if (playerPositions[currentPlayer] == 1) return QuestionTypes.Science;
		if (playerPositions[currentPlayer] == 5) return QuestionTypes.Science;
		if (playerPositions[currentPlayer] == 9) return QuestionTypes.Science;
		if (playerPositions[currentPlayer] == 2) return QuestionTypes.Sports;
		if (playerPositions[currentPlayer] == 6) return QuestionTypes.Sports;
		if (playerPositions[currentPlayer] == 10) return QuestionTypes.Sports;
		return QuestionTypes.Rock;
	}

	public boolean wasCorrectlyAnswered() {
		if (playerInPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				return isWinnerFromCorrectAnswer();
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
		} else {
			System.out.println("Answer was corrent!!!!");
			return isWinnerFromCorrectAnswer();
		}
	}

	private boolean isWinnerFromCorrectAnswer() {
		playerPurses[currentPlayer]++;
		// duplication
		System.out.println(players.get(currentPlayer)
				+ " now has "
				+ playerPurses[currentPlayer]
				+ " Gold Coins.");

		boolean winner = didPlayerWin();
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;

		return winner;
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		playerInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0; // brackets
		return true;
	}


	private boolean didPlayerWin() {
		return playerPurses[currentPlayer] != 6;
	}
}
