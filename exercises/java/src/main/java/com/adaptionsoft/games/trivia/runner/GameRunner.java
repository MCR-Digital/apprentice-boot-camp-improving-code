
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isAWinner;

	public static void main(String[] args) {
		Game game = new Game();
		
		game.addNewPlayer("Chet");
		game.addNewPlayer("Pat");
		game.addNewPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			
			game.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				isAWinner = game.wrongAnswer();
			} else {
				isAWinner = game.wasCorrectlyAnswered();
			}
			
			
			
		} while (isAWinner);
		
	}
}
