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
    return playerPurses[currentPlayer] !== 6
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
    players.push(playerName)

    const player = this.playerCount() -1

    board[player] = 0
    playerPurses[player] = 0
    playersInPenaltyBox[player] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)

    return true
  }

  this.playerCount = function () {
    return players.length
  }
  const displayQuestion = (questions) => {
    console.log(questions.shift())
  }

  var askQuestion = function () {
  const currentCategory = getCurrentCategory()
    if (currentCategory == 'Pop') { displayQuestion(popQuestions) }
    if (currentCategory == 'Science') { displayQuestion(scienceQuestions) }
    if (currentCategory == 'Sports') { displayQuestion(sportsQuestions) }
    if (currentCategory == 'Rock') { displayQuestion(rockQuestions) }
  }

  this.roll = function (roll) {
    const isRollOdd = roll % 2 != 0
    console.log(players[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (playersInPenaltyBox[currentPlayer]) {
      if (isRollOdd) {
        isPlayerInPenaltyBox = true

        console.log(players[currentPlayer] + ' is getting out of the penalty box')
        board[currentPlayer] += roll
        if (board[currentPlayer] > 11) {
          board[currentPlayer] -= 12
        }

        console.log(players[currentPlayer] + "'s new location is " + board[currentPlayer])
        console.log('The category is ' + getCurrentCategory())
        askQuestion()
      } else {
        console.log(players[currentPlayer] + ' is not getting out of the penalty box')
        isPlayerInPenaltyBox = false
      }
    } else {
      board[currentPlayer] += roll
      if (board[currentPlayer] > 11) {
        board[currentPlayer] -= 12
      }

      console.log(players[currentPlayer] + "'s new location is " + board[currentPlayer])
      console.log('The category is ' + getCurrentCategory())
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
