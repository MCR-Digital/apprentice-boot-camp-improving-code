package com.adaptionsoft.games.uglytrivia

import java.util.*

class Board {
    private val places = ArrayList<Place>()
    private val boardSize = 12

    init {
        createBoard(boardSize)
    }

    private fun createBoard(boardSize: Int) {
        for (i in 0..boardSize) {
            places.add(Place(i))
        }
    }
}

class Player (val name: String){
    var purse: Int = 0
    var isInPenaltyBox: Boolean = false

    fun addCoin() {
        purse++
        println(
            name
                    + " now has "
                    + purse
                    + " Gold Coins."
        )
    }
}

class Place (position: Int) {
    private val questionCategory: Category

    init {
        questionCategory = when (position % 4) {
            0 -> Category.Pop
            1 -> Category.Science
            2 -> Category.Sports
            else -> Category.Rock
        }
    }
}

enum class Category {
    Pop, Science, Sports, Rock
}

class Game {
    private val board = Board()

    private val MAX_NUMBER_OF_PLAYERS = 6
    private val MAX_NUMBER_OF_QUESTIONS = 50
    private val NUMBER_OF_PLACES = 12
    private val COINS_TO_WIN = 6

    internal var players = ArrayList<Player>()
    internal var places = IntArray(MAX_NUMBER_OF_PLAYERS)

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
        players.add(Player(playerName))
        places[getNumberOfPlayers()] = 0

        println(playerName + " was added")
        println("They are player number " + players.size)
        return true
    }

    fun getNumberOfPlayers(): Int {
        return players.size
    }

    fun roll(roll: Int) {
        println(players[currentPlayer].name + " is the current player")
        println("They have rolled a " + roll)

        if (players[currentPlayer].isInPenaltyBox) {
            if (isRollOdd(roll)) {
                isGettingOutOfPenaltyBox = true
                println(players[currentPlayer].name + " is getting out of the penalty box")
                movePlayer(roll)
                askQuestion()
            } else {
                println(players[currentPlayer].name + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }

        } else {
            movePlayer(roll)
            askQuestion()
        }

    }

    private fun isRollOdd(roll: Int) = roll % 2 != 0

    private fun movePlayer(roll: Int) {
        places[currentPlayer] = places[currentPlayer] + roll
        if (places[currentPlayer] >= NUMBER_OF_PLACES) places[currentPlayer] = places[currentPlayer] - NUMBER_OF_PLACES

        println(
            players[currentPlayer].name
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
        if (isPopRound()) return "Pop"
        else if (isScienceRound()) return "Science"
        else if (isSportsRound()) return "Sports"
        else return "Rock"
    }

    private fun isSportsRound() = places[currentPlayer] % 4 == 2

    private fun isScienceRound() = places[currentPlayer] % 4 == 1

    private fun isPopRound() = places[currentPlayer] % 4 == 0

    fun wasCorrectlyAnswered(): Boolean {
        if (players[currentPlayer].isInPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                return updateRoundAndCheckIfShouldContinue()
            } else {
                updateCurrentPlayer()
                return true
            }
        } else {
            println("Answer was corrent!!!!")
            return updateRoundAndCheckIfShouldContinue()
        }
    }

    private fun updateRoundAndCheckIfShouldContinue(): Boolean {
        players[currentPlayer].addCoin()
        val shouldContinueGame = shouldContinueGame()
        updateCurrentPlayer()
        return shouldContinueGame
    }



    fun wasIncorrectlyAnswered(): Boolean {
        println("Question was incorrectly answered")
        println(players[currentPlayer].name + " was sent to the penalty box")
        players[currentPlayer].isInPenaltyBox = true

        updateCurrentPlayer()
        return true
    }

    private fun updateCurrentPlayer() {
        currentPlayer++
        if (currentPlayer == players.size) currentPlayer = 0
    }

    private fun shouldContinueGame(): Boolean {
        return players[currentPlayer].purse != COINS_TO_WIN
    }
}