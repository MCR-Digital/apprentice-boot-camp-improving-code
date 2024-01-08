package com.adaptionsoft.games.uglytrivia

import java.util.*

class Game {
    internal var players = ArrayList<Any>()
    internal var places = IntArray(6)
    internal var purses = IntArray(6)
    internal var isInPenaltyBox = BooleanArray(6)

    internal var popQuestions = LinkedList<Any>()
    internal var scienceQuestions = LinkedList<Any>()
    internal var sportsQuestions = LinkedList<Any>()
    internal var rockQuestions = LinkedList<Any>()

    internal var currentPlayer = 0
    internal var isGettingOutOfPenaltyBox: Boolean = false

    init {
        for (i in 0..49) {
            popQuestions.addLast("Pop Question " + i)
            scienceQuestions.addLast("Science Question " + i)
            sportsQuestions.addLast("Sports Question " + i)
            rockQuestions.addLast(createRockQuestion(i))
        }
    }

    fun createRockQuestion(index: Int): String {
        return "Rock Question " + index
    }

    fun isPlayable(): Boolean {
        return getNumberOfPlayers() >= 2
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
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12

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
        if (places[currentPlayer] == 0) return "Pop"
        if (places[currentPlayer] == 4) return "Pop"
        if (places[currentPlayer] == 8) return "Pop"
        if (places[currentPlayer] == 1) return "Science"
        if (places[currentPlayer] == 5) return "Science"
        if (places[currentPlayer] == 9) return "Science"
        if (places[currentPlayer] == 2) return "Sports"
        if (places[currentPlayer] == 6) return "Sports"
        if (places[currentPlayer] == 10) return "Sports"
        return "Rock"
    }

    fun wasCorrectlyAnswered(): Boolean {
        if (isInPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                println("Answer was correct!!!!")
                addCoin()

                val winner = didPlayerWin()
                updateCurrentPlayer()

                return winner
            } else {
                updateCurrentPlayer()
                return true
            }


        } else {

            println("Answer was corrent!!!!")
            addCoin()

            val winner = didPlayerWin()
            updateCurrentPlayer()

            return winner
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

    private fun didPlayerWin(): Boolean {
        return purses[currentPlayer] != 6
    }
}