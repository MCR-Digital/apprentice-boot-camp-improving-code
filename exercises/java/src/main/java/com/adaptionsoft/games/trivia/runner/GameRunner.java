
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isAWinner;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");

		// why is args[0] used here?
		// numberGenerator? randomNumberGenerator
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {

			// MAGIC NUMBERS!!
			game.turn(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				isAWinner = game.isWrongAnswer();
			} else {
				isAWinner = game.wasCorrectlyAnswered();
			}
			
			
			
		} while (isAWinner);
		
	}
}
