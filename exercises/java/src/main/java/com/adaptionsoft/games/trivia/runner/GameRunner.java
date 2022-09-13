
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean hasntReachedWinningCondition;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.addNewPlayer("Chet");
		game.addNewPlayer("Pat");
		game.addNewPlayer("Sue");
		
		Random diceRoll = new Random(Integer.parseInt(args[0]));
	
		do {
			game.turn(diceRoll.nextInt(5) + 1);

				hasntReachedWinningCondition = game.answer(diceRoll.nextInt(9));


		} while (hasntReachedWinningCondition);
		
	}
}
