
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		initializePlayers(aGame);
		numberRolls(Integer.parseInt(args[0]), aGame);

	}

	private static void initializePlayers(Game aGame) {
		aGame.addPlayerNames("Chet");
		aGame.addPlayerNames("Pat");
		aGame.addPlayerNames("Sue");
	}

	private static void numberRolls(Integer rolls, Game aGame) {
		Random rand = new Random(rolls);

		do {
			aGame.roll(rand.nextInt(5) + 1);
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}
}
