import generator from 'random-seed'
import { Player } from './player'

var Game = function () {
  const MAX_PLAYERS = 6
  const winningGoldCoins = 6
  var players = []
  var board = new Array(MAX_PLAYERS)
  var playersInPenaltyBox = new Array(MAX_PLAYERS)

  var popQuestions = []

  var scienceQuestions = []
  var sportsQuestions = []
  var rockQuestions = []

  var currentPlayer = 0
  var isPlayerInPenaltyBox = false

  var isWinner = function () {
    return players[currentPlayer].purse !== winningGoldCoins
  }

  var getCurrentCategory = function () {
    const categoryIndex = board[currentPlayer] % 4
    if (categoryIndex == 0) { return 'Pop' }
    if (categoryIndex  == 1) { return 'Science' }
    if (categoryIndex == 2) { return 'Sports' }
    if (categoryIndex == 3) { return 'Rock' }
  }

  this.createRockQuestion = function (index) {
    return 'Rock Question ' + index
  }

  for (var index = 0; index < 50; index++) {
    popQuestions.push('Pop Question ' + index)
    scienceQuestions.push('Science Question ' + index)
    sportsQuestions.push('Sports Question ' + index)
    rockQuestions.push(this.createRockQuestion(index))
  }

  this.isPlayable = function (howManyPlayers) {
    return howManyPlayers >= 2
  }

  this.add = function (playerName) {
    players.push(new Player(playerName))
    const player = this.playerCount() -1
    this.initializePlayer(player)
    this.logNewPlayer(playerName)
    return true
  }
  this.initializePlayer = function (player) {
    board[player] = 0
    playersInPenaltyBox[player] = false
  }

  this.logNewPlayer = function (playerName) {
    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)
  }

  this.playerCount = function () {
    return players.length
  }
  const displayQuestion = (questions) => {
    console.log(questions.shift())
  }

  this.askQuestion = function () {
  const currentCategory = getCurrentCategory()
    if (currentCategory == 'Pop') { displayQuestion(popQuestions) }
    if (currentCategory == 'Science') { displayQuestion(scienceQuestions) }
    if (currentCategory == 'Sports') { displayQuestion(sportsQuestions) }
    if (currentCategory == 'Rock') { displayQuestion(rockQuestions) }
  }

  this.logPlayerRoll = function (roll) {
    console.log(players[currentPlayer].name + ' is the current player')
    console.log('They have rolled a ' + roll)
  }

  this.movePlayer = function (roll) {
    board[currentPlayer] += roll
    if (board[currentPlayer] > 11) {
      board[currentPlayer] -= 12
    }
    console.log(players[currentPlayer].name + "'s new location is " + board[currentPlayer])
    console.log('The category is ' + getCurrentCategory())
  }

  this.roll = function (roll) {
    const isRollOdd = roll % 2 != 0
    this.logPlayerRoll(roll)

    if (playersInPenaltyBox[currentPlayer]) {
      if (isRollOdd) {
        isPlayerInPenaltyBox = true
        console.log(players[currentPlayer].name + ' is getting out of the penalty box')
        this.movePlayer(roll)
        this.askQuestion()
      } else {
        console.log(players[currentPlayer].name + ' is not getting out of the penalty box')
        isPlayerInPenaltyBox = false
      }
    } else {
      this.movePlayer(roll)
      this.askQuestion()
    }
  }

  this.changePlayer = function () {
    currentPlayer += 1
    if (currentPlayer == players.length) { currentPlayer = 0 }
  }

  this.addToPurse = function () {
    players[currentPlayer].purse += 1
    console.log(players[currentPlayer].name + ' now has ' +
        players[currentPlayer].purse + ' Gold Coins.')
  }

  this.correctAnswer = function () {
    if (playersInPenaltyBox[currentPlayer]) {
      if (isPlayerInPenaltyBox) {
        console.log('Answer was correct!!!!')
        this.addToPurse()
        var winner = isWinner()
        this.changePlayer()
        return winner
      } else {
        this.changePlayer()
        return true
      }
    } else {
      console.log('Answer was corrent!!!!')
      this.addToPurse()
      var winner = isWinner()
      this.changePlayer()
      return winner
    }
  }

  this.wrongAnswer = function () {
    console.log('Question was incorrectly answered')
    console.log(players[currentPlayer].name + ' was sent to the penalty box')
    playersInPenaltyBox[currentPlayer] = true

    this.changePlayer()
    return true
  }
}

const runGame = (i) => {
  var isWinner = false

  var game = new Game()

  game.add('Chet')
  game.add('Pat')
  game.add('Sue')

  const random = generator.create(i)

  do {
    game.roll(random.range(5) + 1)

    if (random.range(9) == 7) {
      isWinner = game.wrongAnswer()
    } else {
      isWinner = game.correctAnswer()
    }
  } while (isWinner)
}

export default runGame
