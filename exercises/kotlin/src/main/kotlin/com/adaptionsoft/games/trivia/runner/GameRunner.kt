package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import java.util.*

object GameRunner {
    fun main(args: Array<String>) {
        val aGame = Game()

        aGame.addPlayer("Chet")
        aGame.addPlayer("Pat")
        aGame.addPlayer("Sue")

        val rand = Random(args[0].toLong())

        do {

            aGame.roll(rand.nextInt(5) + 1)

            if (rand.nextInt(9) == 7) {
                GameRunner.notAWinner = aGame.wasIncorrectlyAnswered()
            } else {
                GameRunner.notAWinner = aGame.wasCorrectlyAnswered()
            }


        } while (GameRunner.notAWinner)

    }

    var notAWinner: Boolean = false
}

