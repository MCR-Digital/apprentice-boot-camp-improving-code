import generator from 'random-seed'

var Game = function () {
  const players = new Array()
  const places = new Array(6)
  const purses = new Array(6)
  const inPenaltyBox = new Array(6)

  const popQuestions = new Array()
  const scienceQuestions = new Array()
  const sportsQuestions = new Array()
  const rockQuestions = new Array()

  const numberOfCategories = 4
  let currentPlayer = 0
  let isGettingOutOfPenaltyBox = false

  var didPlayerWin = function () {
    return !(purses[currentPlayer] == 6)
  }

  var currentCategory = function () {
    const categoryNumber = places[currentPlayer] % numberOfCategories

    switch (categoryNumber) {
      case 0: return 'Pop'
      case 1: return 'Science'
      case 2: return 'Sports'
      case 3: return 'Rock'
    }
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

  this.isPlayable = function (howManyPlayers) {
    return howManyPlayers >= 2
  }

  this.add = function (playerName) {
    players.push(playerName)
    places[this.howManyPlayers() - 1] = 0
    purses[this.howManyPlayers() - 1] = 0
    inPenaltyBox[this.howManyPlayers() - 1] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)

    return true
  }

  this.howManyPlayers = function () {
    return players.length
  }

  var askQuestion = function () {
    if (currentCategory() == 'Pop') { console.log(popQuestions.shift()) }
    if (currentCategory() == 'Science') { console.log(scienceQuestions.shift()) }
    if (currentCategory() == 'Sports') { console.log(sportsQuestions.shift()) }
    if (currentCategory() == 'Rock') { console.log(rockQuestions.shift()) }
  }

  this.movePlayer = function(roll) {
    places[currentPlayer] = places[currentPlayer] + roll

    if (places[currentPlayer] > 11) {
      places[currentPlayer] = places[currentPlayer] - 12
    }

    console.log(players[currentPlayer] + "'s new location is " + places[currentPlayer])
    console.log('The category is ' + currentCategory())
  }

  this.isInPenaltyBox = function() {
    return inPenaltyBox[currentPlayer]
  }

  this.roll = function (roll) {
    console.log(players[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (this.isInPenaltyBox()) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true

        console.log(players[currentPlayer] + ' is getting out of the penalty box')
        
        this.movePlayer(roll)
        askQuestion()
      } else {
        console.log(players[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
      this.movePlayer(roll)
      askQuestion()
    }
  }

  this.addGoldCoin = function() {
    purses[currentPlayer] += 1
    console.log(players[currentPlayer] + ' now has ' +
        purses[currentPlayer] + ' Gold Coins.')
  }

  this.getNextPlayer = function() {
    currentPlayer += 1
    if (currentPlayer == players.length) { currentPlayer = 0 }
  }

  this.canAnswerAQuestion = function() {
    return !this.isInPenaltyBox() || isGettingOutOfPenaltyBox
  }

  this.wasCorrectlyAnswered = function () {
    if(!this.canAnswerAQuestion()) {
      this.getNextPlayer()
      return true
    }

    console.log('Answer was correct!!!!')
    this.addGoldCoin()

    var winner = didPlayerWin()
    this.getNextPlayer()

    return winner
  }

  this.wasIncorrectlyAnswered = function () {
    console.log('Question was incorrectly answered')
    console.log(players[currentPlayer] + ' was sent to the penalty box')
    inPenaltyBox[currentPlayer] = true

    currentPlayer += 1
    if (currentPlayer == players.length) { currentPlayer = 0 }
    return true
  }
}

const gameRunner = (i) => {
  var notAWinner = false

  var game = new Game()

  game.add('Chet')
  game.add('Pat')
  game.add('Sue')

  const random = generator.create(i)

  do {
    game.roll(random.range(5) + 1)

    if (random.range(9) == 7) {
      notAWinner = game.wasIncorrectlyAnswered()
    } else {
      notAWinner = game.wasCorrectlyAnswered()
    }
  } while (notAWinner)
}

export default gameRunner
