package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6]; //NAME, array
    int[] purses  = new int[6]; //NAME, array
    boolean[] inPenaltyBox  = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public Game(){
    	//i NAME
    	for (int i = 0; i < 50; i++) {
    		//METHOD
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	//REMOVE
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
	    players.add(playerName);
	    //VARIABLE
	    places[howManyPlayers()] = 0; //0 TO VARIABLE
	    purses[howManyPlayers()] = 0; // ^
	    inPenaltyBox[howManyPlayers()] = false;
	    
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

		if (inPenaltyBox[currentPlayer]) {
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
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12; // EXPLANATION?

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
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
		if (places[currentPlayer] == 0) return QuestionTypes.Pop;
		if (places[currentPlayer] == 4) return QuestionTypes.Pop;
		if (places[currentPlayer] == 8) return QuestionTypes.Pop;
		if (places[currentPlayer] == 1) return QuestionTypes.Science;
		if (places[currentPlayer] == 5) return QuestionTypes.Science;
		if (places[currentPlayer] == 9) return QuestionTypes.Science;
		if (places[currentPlayer] == 2) return QuestionTypes.Sports;
		if (places[currentPlayer] == 6) return QuestionTypes.Sports;
		if (places[currentPlayer] == 10) return QuestionTypes.Sports;
		return QuestionTypes.Rock;
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)  // TO VARIABLE
						+ " now has "
						+ purses[currentPlayer]
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
			purses[currentPlayer]++;
			// duplication
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
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
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0; // brackets
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
