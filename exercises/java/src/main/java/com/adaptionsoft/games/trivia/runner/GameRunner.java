
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean hasReachedWinCondition;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.add("Chet");
		game.add("Pat");
		game.add("Sue");

		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			game.turn(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				hasReachedWinCondition = game.isWrongAnswer();
				game.changePlayer();
			} else {
				hasReachedWinCondition = game.wasCorrectlyAnswered();
				game.changePlayer();
			}
			
			
			
		} while (hasReachedWinCondition);
		
	}
}
