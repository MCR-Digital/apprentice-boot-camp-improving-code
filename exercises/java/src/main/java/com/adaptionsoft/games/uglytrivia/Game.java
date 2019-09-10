package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";
    private static final int WINNING_NUMBER = 6;
    private ArrayList players = new ArrayList();
    private int[] places = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private LinkedList popQuestions = new LinkedList();
    private LinkedList scienceQuestions = new LinkedList();
    private LinkedList sportsQuestions = new LinkedList();
    private LinkedList rockQuestions = new LinkedList();

    private int currentPlayerPlace = 0;
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
        System.out.println(players.get(currentPlayerPlace) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayerPlace]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayerPlace) + " is getting out of the penalty box");
                updatePlayerLocationAndCategory(roll);
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayerPlace) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            updatePlayerLocationAndCategory(roll);
            askQuestion();
        }

    }

    private void updatePlayerLocationAndCategory(int roll) {
        places[currentPlayerPlace] = places[currentPlayerPlace] + roll;

        if (places[currentPlayerPlace] > 11)
            places[currentPlayerPlace] = places[currentPlayerPlace] - 12;

        System.out.println(players.get(currentPlayerPlace)
                + "'s new location is "
                + places[currentPlayerPlace]);
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

        int playerPlace = places[currentPlayerPlace];

        if (playerPlace == 0 || playerPlace == 4 || playerPlace == 8) return POP;
        if (playerPlace == 1 || playerPlace == 5 || playerPlace == 9) return SCIENCE;
        if (playerPlace == 2 || playerPlace == 6 || playerPlace == 10) return SPORTS;

        return ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayerPlace]) {
            if (isGettingOutOfPenaltyBox) {
                updateCoins("Answer was correct!!!!");

                boolean winner = didPlayerWin();

                updatePlayerPlace();
                return winner;

            } else {
                updatePlayerPlace();
                return true;
            }


        } else {
            updateCoins("Answer was corrent!!!!");

            boolean winner = didPlayerWin();
            updatePlayerPlace();

            return winner;
        }
    }

    private void updatePlayerPlace() {
        currentPlayerPlace++;
        if (currentPlayerPlace == players.size()) {
            currentPlayerPlace = 0;
        }
    }

    private void updateCoins(String s) {
        System.out.println(s);
        purses[currentPlayerPlace]++;
        System.out.println(players.get(currentPlayerPlace)
                + " now has "
                + purses[currentPlayerPlace]
                + " Gold Coins.");
    }

    public boolean isAnswerWrong() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayerPlace) + " was sent to the penalty box");
        inPenaltyBox[currentPlayerPlace] = true;

        updatePlayerPlace();

        return true;
    }


    private boolean didPlayerWin() {
        return (purses[currentPlayerPlace] != WINNING_NUMBER);
    }
}
