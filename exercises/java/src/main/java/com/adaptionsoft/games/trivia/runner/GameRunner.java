
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	public static void main(String[] args) {
		Game newGame = new Game();
		
		newGame.addingPlayer("Chet");
		newGame.addingPlayer("Pat");
		newGame.addingPlayer("Sue");
		
		Random rand = new Random(Integer.parseInt(args[0]));

		boolean notAWinner;
		do {
			newGame.rollTheDice(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = newGame.questionWasAnsweredWrong();
			} else {
				notAWinner = newGame.questionWasAnsweredCorrectly();
			}

		} while (notAWinner);
		
	}
}
