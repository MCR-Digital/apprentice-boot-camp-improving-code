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

    fun getPlace(position: Int): Place {
        return places[position]
    }
}

class Player (val name: String){
    var purse: Int = 0
    var isInPenaltyBox: Boolean = false
    var position: Int = 0

    fun addCoin() {
        purse++
        println(
            "$name now has $purse Gold Coins."
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
    fun category(): String {
        return questionCategory.toString()
    }
}

class QuestionBank {
    private val MAX_NUMBER_OF_QUESTIONS = 50

    internal var pop = LinkedList<Any>()
    internal var science = LinkedList<Any>()
    internal var sports = LinkedList<Any>()
    internal var rock = LinkedList<Any>()

    init {
        for (questionIndex in 0..<MAX_NUMBER_OF_QUESTIONS) {
            pop.addLast(createQuestion("Pop", questionIndex))
            science.addLast(createQuestion("Science", questionIndex))
            sports.addLast(createQuestion("Sports", questionIndex))
            rock.addLast(createQuestion("Rock", questionIndex))
        }
    }

    private fun createQuestion(category: String, index: Int): String {
        return "$category Question $index"
    }

    fun askQuestion(place: Place) {
        if (place.category() === "Pop")
            println(pop.removeFirst())
        if (place.category() === "Science")
            println(science.removeFirst())
        if (place.category() === "Sports")
            println(sports.removeFirst())
        if (place.category() === "Rock")
            println(rock.removeFirst())
    }
}

enum class Category {
    Pop, Science, Sports, Rock
}

class Game {
    private val NUMBER_OF_PLACES = 12
    private val COINS_TO_WIN = 6

    private val board = Board()
    private val questionBank = QuestionBank()
    private var players = ArrayList<Player>()

    private var currentPlayerIndex = 0
    private lateinit var currentPlayer: Player
    private var isGettingOutOfPenaltyBox: Boolean = false

    fun addPlayer(playerName: String): Boolean {
        players.add(Player(playerName))
        println("$playerName was added")
        println("They are player number " + players.size)
        currentPlayer = players[currentPlayerIndex]
        return true
    }

    fun roll(roll: Int) {
        println(currentPlayer.name + " is the current player")
        println("They have rolled a $roll")

        if (currentPlayer.isInPenaltyBox) {
            if (isRollOdd(roll)) {
                isGettingOutOfPenaltyBox = true
                println(currentPlayer.name + " is getting out of the penalty box")
                movePlayer(roll)
                questionBank.askQuestion(getCurrentPlace())
            } else {
                println(currentPlayer.name + " is not getting out of the penalty box")
                isGettingOutOfPenaltyBox = false
            }
        } else {
            movePlayer(roll)
            questionBank.askQuestion(getCurrentPlace())
        }
    }

    private fun isRollOdd(roll: Int) = roll % 2 != 0

    private fun movePlayer(roll: Int) {
        currentPlayer.position += roll
        if (currentPlayer.position >= NUMBER_OF_PLACES) currentPlayer.position -= NUMBER_OF_PLACES

        println(
            currentPlayer.name
                    + "'s new location is "
                    + currentPlayer.position
        )
        println("The category is " + getCurrentPlace().category())
    }

    private fun getCurrentPlace() = board.getPlace(currentPlayer.position)

    fun wasCorrectlyAnswered(): Boolean {
        if (currentPlayer.isInPenaltyBox) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                currentPlayer.addCoin()
                val shouldContinueGame = shouldContinueGame()
                updateCurrentPlayer()
                return shouldContinueGame
            } else {
                updateCurrentPlayer()
                return true
            }
        } else {
            println("Answer was corrent!!!!")
            currentPlayer.addCoin()
            val shouldContinueGame = shouldContinueGame()
            updateCurrentPlayer()
            return shouldContinueGame
        }
    }

    fun wasIncorrectlyAnswered(): Boolean {
        println("Question was incorrectly answered")
        println(currentPlayer.name + " was sent to the penalty box")
        currentPlayer.isInPenaltyBox = true
        updateCurrentPlayer()
        return true
    }

    private fun updateCurrentPlayer() {
        currentPlayerIndex++
        if (currentPlayerIndex == players.size) currentPlayerIndex = 0
        currentPlayer = players[currentPlayerIndex]
    }

    private fun shouldContinueGame(): Boolean {
        return currentPlayer.purse != COINS_TO_WIN
    }
}