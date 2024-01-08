package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    private val MAX_NUMBER_OF_PLAYERS = 6
    private val MAX_NUMBER_OF_QUESTIONS = 50
    private val NUMBER_OF_PLACES = 12
    private val COINS_TO_WIN = 6

    internal var players = ArrayList<Any>()
    internal var places = IntArray(MAX_NUMBER_OF_PLAYERS)
    internal var purses = IntArray(MAX_NUMBER_OF_PLAYERS)
    internal var isInPenaltyBox = BooleanArray(MAX_NUMBER_OF_PLAYERS)

    internal var popQuestions = LinkedList<Any>()
    internal var scienceQuestions = LinkedList<Any>()
    internal var sportsQuestions = LinkedList<Any>()
    internal var rockQuestions = LinkedList<Any>()

    internal var currentPlayer = 0
    internal var isGettingOutOfPenaltyBox: Boolean = false

    init {
        for (questionIndex in 0..<MAX_NUMBER_OF_QUESTIONS) {
            popQuestions.addLast(createQuestion("Pop", questionIndex))
            scienceQuestions.addLast(createQuestion("Science", questionIndex))
            sportsQuestions.addLast(createQuestion("Sports", questionIndex))
            rockQuestions.addLast(createQuestion("Rock", questionIndex))
        }
    }

    fun createQuestion(category: String, index: Int): String {
        return "$category Question " + index
    }

    fun addPlayer(playerName: String): Boolean {


        players.add(playerName)
        places[getNumberOfPlayers()] = 0
        purses[getNumberOfPlayers()] = 0
        isInPenaltyBox[getNumberOfPlayers()] = false

        println(playerName + " was added")
        println("They are player number " + players.size)
        return true
    }

    fun getNumberOfPlayers(): Int {
        return players.size
    }

    fun roll(roll: Int) {
        println(players[currentPlayer].toString() + " is the current player")
        println("They have rolled a " + roll)

        if (isInPenaltyBox[currentPlayer]) {
            val isRollOdd = roll % 2 != 0
            if (isRollOdd) {
                isGettingOutOfPenaltyBox = true

                println(players[currentPlayer].toString() + " is getting out of the penalty box")
                movePlayer(roll)
                askQuestion()
            } else {
                println(players[currentPlayer].toString() + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }

        } else {
            movePlayer(roll)
            askQuestion()
        }

    }

    private fun movePlayer(roll: Int) {
        places[currentPlayer] = places[currentPlayer] + roll
        if (places[currentPlayer] >= NUMBER_OF_PLACES) places[currentPlayer] = places[currentPlayer] - NUMBER_OF_PLACES

        println(
            players[currentPlayer].toString()
                    + "'s new location is "
                    + places[currentPlayer]
        )
        println("The category is " + currentCategory())
    }

    private fun askQuestion() {
        if (currentCategory() === "Pop")
            println(popQuestions.removeFirst())
        if (currentCategory() === "Science")
            println(scienceQuestions.removeFirst())
        if (currentCategory() === "Sports")
            println(sportsQuestions.removeFirst())
        if (currentCategory() === "Rock")
            println(rockQuestions.removeFirst())
    }

    private fun currentCategory(): String {
        if (places[currentPlayer] % 4 == 0) return "Pop"
        if (places[currentPlayer] % 4 == 1) return "Science"
        if (places[currentPlayer] % 4 == 2) return "Sports"
        return "Rock"
    }

    fun wasCorrectlyAnswered(): Boolean {
        if (isInPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                addCoin()

                val shouldContinueGame = shouldContinueGame()
                updateCurrentPlayer()

                return shouldContinueGame
            } else {
                updateCurrentPlayer()
                return true
            }


        } else {

            println("Answer was corrent!!!!")
            addCoin()

            val shouldContinueGame = shouldContinueGame()
            updateCurrentPlayer()

            return shouldContinueGame
        }
    }

    private fun addCoin() {
        purses[currentPlayer]++
        println(
            players[currentPlayer].toString()
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins."
        )
    }

    fun wasIncorrectlyAnswered(): Boolean {
        println("Question was incorrectly answered")
        println(players[currentPlayer].toString() + " was sent to the penalty box")
        isInPenaltyBox[currentPlayer] = true

        updateCurrentPlayer()
        return true
    }

    private fun updateCurrentPlayer() {
        currentPlayer++
        if (currentPlayer == players.size) currentPlayer = 0
    }

    private fun shouldContinueGame(): Boolean {
        return purses[currentPlayer] != COINS_TO_WIN
    }
}