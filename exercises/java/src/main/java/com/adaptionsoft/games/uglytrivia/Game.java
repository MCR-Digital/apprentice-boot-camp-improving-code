package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";
    private static final int WINNING_NUMBER = 6;
    public static final int MAX_PLAYERS_NUMBER = 6;
    private ArrayList players = new ArrayList();
    private int[] places = new int[MAX_PLAYERS_NUMBER];
    private int[] purses = new int[MAX_PLAYERS_NUMBER];
    private boolean[] inPenaltyBox = new boolean[MAX_PLAYERS_NUMBER];

    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
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

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        boolean rollIsEven = roll % 2 == 0;
        boolean inPenaltyBox = this.inPenaltyBox[currentPlayer];

        if (inPenaltyBox && !rollIsEven) {
            isGettingOutOfPenaltyBox = true;
            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            updatePlayerLocationAndCategory(roll);
            askQuestion();
        } else if (inPenaltyBox) {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
        } else {
            updatePlayerLocationAndCategory(roll);
            askQuestion();
        }
    }

    private void updatePlayerLocationAndCategory(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;

        if (places[currentPlayer] > 11)
            places[currentPlayer] = places[currentPlayer] - 12;

        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        System.out.println("The category is " + currentCategory());
    }

    private void askQuestion() {
        switch (currentCategory()) {
            case POP:
                System.out.println(popQuestions.removeFirst());
                break;
            case SCIENCE:
                System.out.println(scienceQuestions.removeFirst());
                break;
            case SPORTS:
                System.out.println(sportsQuestions.removeFirst());
                break;
            default:
                System.out.println(rockQuestions.removeFirst());
                break;
        }
    }


    private String currentCategory() {
        int playerPlace = places[currentPlayer];

        if (playerPlace > 7) {
            playerPlace = playerPlace - 8;
        } else if (playerPlace > 3) {
            playerPlace = playerPlace - 4;
        }

        switch (playerPlace) {
            case 0:
                return POP;
            case 1:
                return SCIENCE;
            case 2:
                return SPORTS;
            default:
                return ROCK;
        }
    }

    public boolean wasCorrectlyAnswered() {
        boolean inPenaltyBoxAfterQuestion = this.inPenaltyBox[currentPlayer];

        if (inPenaltyBoxAfterQuestion && isGettingOutOfPenaltyBox) {
            updateCoins("Answer was correct!!!!");

            boolean winner = didPlayerWin();

            updatePlayersPlace();
            return winner;

        } else if (inPenaltyBoxAfterQuestion) {
            updatePlayersPlace();
            return true;
        } else {
            updateCoins("Answer was corrent!!!!");

            boolean winner = didPlayerWin();
            updatePlayersPlace();

            return winner;
        }
    }

    private void updatePlayersPlace() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private void updateCoins(String s) {
        System.out.println(s);
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    public boolean isAnswerWrong() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        updatePlayersPlace();

        return true;
    }

    private boolean didPlayerWin() {
        return (purses[currentPlayer] != WINNING_NUMBER);
    }
}
