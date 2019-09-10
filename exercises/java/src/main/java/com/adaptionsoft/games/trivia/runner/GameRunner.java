
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean isNoWinner;

	public static void main(String[] args) {
		Game aGame = new Game();
		
		aGame.add("Sandra");
		aGame.add("Pat");
		aGame.add("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));
	
		do {
			
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				isNoWinner = aGame.wrongAnswer();
			} else {
				isNoWinner = aGame.wasCorrectlyAnswered();
			}
			
			
			
		} while (isNoWinner);
		
	}
}
