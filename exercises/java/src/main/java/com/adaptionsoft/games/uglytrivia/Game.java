package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] playerPositionList = new int[6]; // array
    int[] playerPurseList  = new int[6]; // array
    boolean[] playerInPenaltyBoxList  = new boolean[6];
    
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

	//REMOVE
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(playerName);
	    //VARIABLE
		playerPositionList[howManyPlayers()] = 0; //0 TO VARIABLE
		playerPositionList[howManyPlayers()] = 0; // ^
	    playerInPenaltyBoxList[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	//AMOUNTOFPLAYERS
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (playerInPenaltyBoxList[currentPlayer]) {
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
		playerPositionList[currentPlayer] = playerPositionList[currentPlayer] + roll;
		if (playerPositionList[currentPlayer] > 11) playerPositionList[currentPlayer] = playerPositionList[currentPlayer] - 12; // EXPLANATION?

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ playerPositionList[currentPlayer]);
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
		if (playerPositionList[currentPlayer] == 0) return QuestionTypes.Pop;
		if (playerPositionList[currentPlayer] == 4) return QuestionTypes.Pop;
		if (playerPositionList[currentPlayer] == 8) return QuestionTypes.Pop;
		if (playerPositionList[currentPlayer] == 1) return QuestionTypes.Science;
		if (playerPositionList[currentPlayer] == 5) return QuestionTypes.Science;
		if (playerPositionList[currentPlayer] == 9) return QuestionTypes.Science;
		if (playerPositionList[currentPlayer] == 2) return QuestionTypes.Sports;
		if (playerPositionList[currentPlayer] == 6) return QuestionTypes.Sports;
		if (playerPositionList[currentPlayer] == 10) return QuestionTypes.Sports;
		return QuestionTypes.Rock;
	}

	public boolean wasCorrectlyAnswered() {
		if (playerInPenaltyBoxList[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				playerPurseList[currentPlayer]++;
				System.out.println(players.get(currentPlayer)  // TO VARIABLE
						+ " now has "
						+ playerPurseList[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin(); // rename
				currentPlayer++; // change player variable
				if (currentPlayer == players.size()) currentPlayer = 0; // brackets
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			playerPurseList[currentPlayer]++;
			// duplication
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ playerPurseList[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		playerInPenaltyBoxList[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0; // brackets
		return true;
	}


	private boolean didPlayerWin() {
		return !(playerPurseList[currentPlayer] == 6);
	}
}
