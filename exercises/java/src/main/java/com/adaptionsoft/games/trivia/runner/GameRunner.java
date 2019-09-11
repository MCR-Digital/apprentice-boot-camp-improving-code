
package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;
import com.adaptionsoft.games.uglytrivia.QuestionDeck;

import java.util.Random;


public class GameRunner {


    private static final String POP = "Pop";
    private static final String SCIENCE = "Science";
    private static final String SPORTS = "Sports";
    private static final String ROCK = "Rock";

    private static boolean notAWinner;

    public static void main(String[] args) {
        QuestionDeck questionDeck = new QuestionDeck(POP, SCIENCE, SPORTS, ROCK);

        Player chet = new Player("Chet", 1);
        Player pat = new Player("Pat", 2);
        Player sue = new Player("Sue", 3);

        Game aGame = new Game(questionDeck, chet, pat, sue);

        Random rand = new Random(Integer.parseInt(args[0]));

        do {

            aGame.rollDice(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }

        } while (notAWinner);

    }
}
