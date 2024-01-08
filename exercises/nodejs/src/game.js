import generator from 'random-seed'

var Game = function () {
  var players = new Array()
  var board = new Array(6)
  var playerPurses = new Array(6)
  var playersInPenaltyBox = new Array(6)

  var popQuestions = new Array()
  var scienceQuestions = new Array()
  var sportsQuestions = new Array()
  var rockQuestions = new Array()

  var currentPlayer = 0
  var isPlayerInPenaltyBox = false

  var isWinner = function () {
    return !(playerPurses[currentPlayer] == 6)
  }

  var currentCategory = function () {
    if (board[currentPlayer] == 0) { return 'Pop' }
    if (board[currentPlayer] == 4) { return 'Pop' }
    if (board[currentPlayer] == 8) { return 'Pop' }
    if (board[currentPlayer] == 1) { return 'Science' }
    if (board[currentPlayer] == 5) { return 'Science' }
    if (board[currentPlayer] == 9) { return 'Science' }
    if (board[currentPlayer] == 2) { return 'Sports' }
    if (board[currentPlayer] == 6) { return 'Sports' }
    if (board[currentPlayer] == 10) { return 'Sports' }
    return 'Rock'
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
    players.push(playerName)
    board[this.playerCount() - 1] = 0
    playerPurses[this.playerCount() - 1] = 0
    playersInPenaltyBox[this.playerCount() - 1] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)

    return true
  }

  this.playerCount = function () {
    return players.length
  }

  var askQuestion = function () {
    if (currentCategory() == 'Pop') { console.log(popQuestions.shift()) }
    if (currentCategory() == 'Science') { console.log(scienceQuestions.shift()) }
    if (currentCategory() == 'Sports') { console.log(sportsQuestions.shift()) }
    if (currentCategory() == 'Rock') { console.log(rockQuestions.shift()) }
  }

  this.roll = function (roll) {
    console.log(players[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (playersInPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isPlayerInPenaltyBox = true

        console.log(players[currentPlayer] + ' is getting out of the penalty box')
        board[currentPlayer] = board[currentPlayer] + roll
        if (board[currentPlayer] > 11) {
          board[currentPlayer] = board[currentPlayer] - 12
        }

        console.log(players[currentPlayer] + "'s new location is " + board[currentPlayer])
        console.log('The category is ' + currentCategory())
        askQuestion()
      } else {
        console.log(players[currentPlayer] + ' is not getting out of the penalty box')
        isPlayerInPenaltyBox = false
      }
    } else {
      board[currentPlayer] = board[currentPlayer] + roll
      if (board[currentPlayer] > 11) {
        board[currentPlayer] = board[currentPlayer] - 12
      }

      console.log(players[currentPlayer] + "'s new location is " + board[currentPlayer])
      console.log('The category is ' + currentCategory())
      askQuestion()
    }
  }

  this.correctAnswer = function () {
    if (playersInPenaltyBox[currentPlayer]) {
      if (isPlayerInPenaltyBox) {
        console.log('Answer was correct!!!!')
        playerPurses[currentPlayer] += 1
        console.log(players[currentPlayer] + ' now has ' +
            playerPurses[currentPlayer] + ' Gold Coins.')

        var winner = isWinner()
        currentPlayer += 1
        if (currentPlayer == players.length) { currentPlayer = 0 }

        return winner
      } else {
        currentPlayer += 1
        if (currentPlayer == players.length) { currentPlayer = 0 }
        return true
      }
    } else {
      console.log('Answer was corrent!!!!')

      playerPurses[currentPlayer] += 1
      console.log(players[currentPlayer] + ' now has ' +
          playerPurses[currentPlayer] + ' Gold Coins.')

      var winner = isWinner()

      currentPlayer += 1
      if (currentPlayer == players.length) { currentPlayer = 0 }

      return winner
    }
  }

  this.wrongAnswer = function () {
    console.log('Question was incorrectly answered')
    console.log(players[currentPlayer] + ' was sent to the penalty box')
    playersInPenaltyBox[currentPlayer] = true

    currentPlayer += 1
    if (currentPlayer == players.length) { currentPlayer = 0 }
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
