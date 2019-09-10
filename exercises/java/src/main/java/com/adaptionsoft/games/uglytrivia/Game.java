package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class Game {
    private static final String POP = "Pop";
	private static final String SCIENCE = "Science";
	private static final String SPORTS = "Sports";
	private static final String ROCK = "Rock";

    private static final int MINIMUM_PLAYERS = 2;
    private static final int END_SPACE = 11;
    private static final int NUMBER_OF_SPACES = 12;
    private static final int BOARD_STARTING_LOCATION = 0;
    private static final int ZERO_COINS = 0;
    private static final int TOTAL_NUMBER_OF_QUESTIONS = 50;

    private Map<Integer, String> categoriesToSpaces = new HashMap<>();

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

	ArrayList allPlayers = new ArrayList();
    int[] locationOnBoard = new int[6];
    int[] goldCoins = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];

    int currentPlayer = 0;
    boolean isExitingPenaltyBox;

    public  Game(){
    	for (int index = 0; index < TOTAL_NUMBER_OF_QUESTIONS; index++) {
			popQuestions.addLast(POP + " Question " + index);
			scienceQuestions.addLast((SCIENCE + " Question " + index));
			sportsQuestions.addLast((SPORTS + " Question " + index));
			rockQuestions.addLast(createRockQuestion(index));
			assignCategory(POP, 0, 4, 8);
			assignCategory(SCIENCE, 1, 5, 9);
			assignCategory(SPORTS, 2, 6, 10);
			assignCategory(ROCK, 3, 7, 11);
    	}
    }

	public String createRockQuestion(int index){
		return ROCK + " Question " + index;
	}

	public boolean isPlayable() {
		return (numberOfPlayers() >= MINIMUM_PLAYERS);
	}

	public boolean addPlayer(String playerName) {

	    allPlayers.add(playerName);
	    locationOnBoard[numberOfPlayers()] = BOARD_STARTING_LOCATION;
	    goldCoins[numberOfPlayers()] = ZERO_COINS;
	    isInPenaltyBox[numberOfPlayers()] = false;

	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + numberOfPlayers());
		return true;
	}

	public int numberOfPlayers() {
		return allPlayers.size();
	}

	public void rollDice(int currentRoll) {
		System.out.println(allPlayers.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + currentRoll);

		if (isInPenaltyBox[currentPlayer]) {
            boolean oddRoll = currentRoll % 2 != 0;
            if (oddRoll) {
				isExitingPenaltyBox = true;
				System.out.println(allPlayers.get(currentPlayer) + " is getting out of the penalty box");
                takeTurn(currentRoll);
            } else {
				System.out.println(allPlayers.get(currentPlayer) + " is not getting out of the penalty box");
				isExitingPenaltyBox = false;
			}
		} else {
            takeTurn(currentRoll);
        }
	}

    private void takeTurn(int currentRoll) {
        locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] + currentRoll;
        if (locationOnBoard[currentPlayer] > END_SPACE) locationOnBoard[currentPlayer] = locationOnBoard[currentPlayer] - NUMBER_OF_SPACES;

        System.out.println(allPlayers.get(currentPlayer)
                + "'s new location is "
                + locationOnBoard[currentPlayer]);
        System.out.println("The category is " + getCurrentCategory());

        askQuestion();
    }

    public boolean isCorrectlyAnswered() {
        if (isInPenaltyBox[currentPlayer]){
            if (isExitingPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                collectCoins();
                return hasCurrentPlayerWon();
            } else {
                getNextPlayer();
                return true;
            }
        }
        else {
            System.out.println("Answer was corrent!!!!");
            collectCoins();
            return hasCurrentPlayerWon();
        }
    }

    public boolean isIncorrectlyAnswered(){
        System.out.println("Question was incorrectly answered");
        System.out.println(allPlayers.get(currentPlayer)+ " was sent to the penalty box");
        isInPenaltyBox[currentPlayer] = true;

        getNextPlayer();
        return true;
    }

    private void askQuestion() {
		if (getCurrentCategory().equals(POP))
			System.out.println(popQuestions.removeFirst());
		if (getCurrentCategory().equals(SCIENCE))
			System.out.println(scienceQuestions.removeFirst());
		if (getCurrentCategory().equals(SPORTS))
			System.out.println(sportsQuestions.removeFirst());
		if (getCurrentCategory().equals(ROCK))
			System.out.println(rockQuestions.removeFirst());
	}

	private String getCurrentCategory() {
        int currentSpace = locationOnBoard[currentPlayer];

        return categoriesToSpaces.get(currentSpace);
	}

	private void assignCategory(String category, int... spaces) {
        Arrays.stream(spaces).forEach(space -> categoriesToSpaces.put(space, category));
    }

    private boolean hasCurrentPlayerWon() {
        boolean isWinner = didPlayerWin();
        getNextPlayer();

        return isWinner;
    }

    private void getNextPlayer() {
        currentPlayer++;
        resetToFirstPlayer();
    }

    private void collectCoins() {
        goldCoins[currentPlayer]++;
        System.out.println(allPlayers.get(currentPlayer)
                + " now has "
                + goldCoins[currentPlayer]
                + " Gold Coins.");
    }

    private void resetToFirstPlayer() {
        int firstPlayer = 0;
        if (currentPlayer == numberOfPlayers()) {
            this.currentPlayer = firstPlayer;
		}
	}

	private boolean didPlayerWin() {
        int maxGoldCoins = 6;
        return !(goldCoins[currentPlayer] == maxGoldCoins);
	}
}
