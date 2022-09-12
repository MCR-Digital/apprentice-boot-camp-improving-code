import generator from 'random-seed'

var Game = function () {
  var playerNames = new Array()
  var playerPositions = new Array(6)
  var playerPurses = new Array(6)
  var inPenaltyBox = new Array(6)

  var popQuestions = new Array()
  var scienceQuestions = new Array()
  var sportsQuestions = new Array()
  var rockQuestions = new Array()

  var currentPlayer = 0
  var isGettingOutOfPenaltyBox = false

  var didPlayerWin = function () {
    return !(playerPurses[currentPlayer] == 6)
  }

  var currentCategory = function () {
    if (playerPositions[currentPlayer] == 0) { return 'Pop' }
    if (playerPositions[currentPlayer] == 4) { return 'Pop' }
    if (playerPositions[currentPlayer] == 8) { return 'Pop' }
    if (playerPositions[currentPlayer] == 1) { return 'Science' }
    if (playerPositions[currentPlayer] == 5) { return 'Science' }
    if (playerPositions[currentPlayer] == 9) { return 'Science' }
    if (playerPositions[currentPlayer] == 2) { return 'Sports' }
    if (playerPositions[currentPlayer] == 6) { return 'Sports' }
    if (playerPositions[currentPlayer] == 10) { return 'Sports' }
    return 'Rock'
  }

  this.createRockQuestion = function (index) {
    return 'Rock Question ' + index
  }

  for (var i = 0; i < 50; i++) {
    popQuestions.push('Pop Question ' + i)
    scienceQuestions.push('Science Question ' + i)
    sportsQuestions.push('Sports Question ' + i)
    rockQuestions.push(this.createRockQuestion(i))
  }

  this.canPlayGame = function (howManyPlayers) {
    return howManyPlayers >= 2
  }

  this.addPlayer = function (playerName) {
    playerNames.push(playerName)
    playerPositions[this.numberOfPlayers() - 1] = 0
    playerPurses[this.numberOfPlayers() - 1] = 0
    inPenaltyBox[this.numberOfPlayers() - 1] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + playerNames.length)

    return true
  }

  this.numberOfPlayers = function () {
    return playerNames.length
  }

  var askQuestion = function () {
    if (currentCategory() == 'Pop') { console.log(popQuestions.shift()) }
    if (currentCategory() == 'Science') { console.log(scienceQuestions.shift()) }
    if (currentCategory() == 'Sports') { console.log(sportsQuestions.shift()) }
    if (currentCategory() == 'Rock') { console.log(rockQuestions.shift()) }
  }

  this.roll = function (roll) {
    console.log(playerNames[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true

        console.log(playerNames[currentPlayer] + ' is getting out of the penalty box')
        movePlayerPosition(playerPositions, currentPlayer, roll)

        askQuestion()
      } else {
        console.log(playerNames[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
      movePlayerPosition(playerPositions, currentPlayer, roll)

      askQuestion()
    }
  }

  this.questionAnsweredCorrectly = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        console.log('Answer was correct!!!!')
        increasePurse()

        var winner = didPlayerWin()
        nextPlayer()

        return winner
      } else {
        nextPlayer()
        return true
      }
    } else {

      console.log('Answer was correct!!!!')
      increasePurse()

      var winner = didPlayerWin()

      nextPlayer()
      return winner
    }


    function increasePurse() {
      playerPurses[currentPlayer] += 1
      console.log(playerNames[currentPlayer] + ' now has ' +
        playerPurses[currentPlayer] + ' Gold Coins.')
    }
  }
  
  this.questionAnsweredIncorrectly = function () {
    console.log('Question was incorrectly answered')
    console.log(playerNames[currentPlayer] + ' was sent to the penalty box')
    inPenaltyBox[currentPlayer] = true

    nextPlayer()
    return true
  }

  function nextPlayer() {
    currentPlayer += 1
    if (currentPlayer == playerNames.length) { currentPlayer = 0} 
  }

  function movePlayerPosition(playerPositions, currentPlayer, roll) {
    playerPositions[currentPlayer] = playerPositions[currentPlayer] + roll
    if (playerPositions[currentPlayer] > 11) {
      playerPositions[currentPlayer] = playerPositions[currentPlayer] - 12
    }

    console.log(playerNames[currentPlayer] + "'s new location is " + playerPositions[currentPlayer])
    console.log('The category is ' + currentCategory())
  }
}

const gameRunner = (i) => {
  var notAWinner = false

  var game = new Game()

  game.addPlayer('Chet')
  game.addPlayer('Pat')
  game.addPlayer('Sue')

  const random = generator.create(i)

  do {
    game.roll(random.range(5) + 1)

    if (random.range(9) == 7) {
      notAWinner = game.questionAnsweredIncorrectly()
    } else {
      notAWinner = game.questionAnsweredCorrectly()
    }
  } while (notAWinner)
}

export default gameRunner



