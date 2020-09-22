
package com.adaptionsoft.games.trivia.runner;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Player;

import java.util.Random;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Game game = new Game();

		game.addPlayer(new Player("Chet"));
		game.addPlayer(new Player("Pat"));
		game.addPlayer(new Player("Sue"));
		
		Random randomNumber = new Random(Integer.parseInt(args[0]));

	
		do {
			
			game.roll(randomNumber.nextInt(5) + 1);
			
			if (randomNumber.nextInt(9) == 7) {
				notAWinner = game.wrongAnswer();
			} else {
				notAWinner = game.correctAnswer();
			}
			
			
			
		} while (notAWinner);
		
	}
}
