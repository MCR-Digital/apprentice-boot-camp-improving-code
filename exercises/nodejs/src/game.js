import generator from 'random-seed'

class Player {
  constructor(name) {
    this._name = name
    this._purse = 0;
    this._position = 0;
    this._inPenaltyBox = false;
  }

  get name() {
    return this._name;
  }

  get purse() {
    return this._purse;
  }

  get position() {
    return this._position;
  }

  get inPenaltyBox() {
    return this._inPenaltyBox;
  }

  updatePenaltyBox() {
    this._inPenaltyBox = !this._inPenaltyBox;
  }

  increasePurse() {
    this.purse++;
    console.log(this.name + ' now has ' + this.purse + ' Gold Coins.');
  }
}

var Game = function () {
  var playerNames = new Array()
  var playerPositions = new Array(6)
  var playerPurses = new Array(6)
  var inPenaltyBox = new Array(6)

  const questionCategories = {
    'Pop': new Array(),
    'Science': new Array(),
    'Sports': new Array(),
    'Rock': new Array()
  }

  var currentPlayer = 0
  var isGettingOutOfPenaltyBox = false

  const NumberOfQuestions = 50;

  var didPlayerWin = function () {
    return !(playerPurses[currentPlayer] == 6)
  }

  var currentCategory = function () {
    if (playerPositions[currentPlayer] % 4 == 0) { return 'Pop' }
    if (playerPositions[currentPlayer] % 4 == 1) { return 'Science' }
    if (playerPositions[currentPlayer] % 4 == 2) { return 'Sports' }
    return 'Rock'
  }

  this.createQuestion = function (category, index) {
    return category + ' Question ' + index
  }

  this.createQuestionSet = function () {
    for (var questionNumber = 0; questionNumber < NumberOfQuestions; questionNumber++) {
      for(let category of Object.keys(questionCategories)) {
        questionCategories[category].push(this.createQuestion(category, questionNumber))
      }
    }
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

  var askQuestion = function (category) {
    console.log(questionCategories[category].shift())
  }

  this.roll = function (roll) {
    console.log(playerNames[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true

        console.log(playerNames[currentPlayer] + ' is getting out of the penalty box')
        movePlayerPosition(roll)

        askQuestion(currentCategory())
      } else {
        console.log(playerNames[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
      movePlayerPosition(roll)

      askQuestion(currentCategory())
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
  }

  function increasePurse() {
    playerPurses[currentPlayer] += 1
    console.log(playerNames[currentPlayer] + ' now has ' + playerPurses[currentPlayer] + ' Gold Coins.')
  }
  
  this.questionAnsweredIncorrectly = function () {
    console.log('Question was incorrectly answered')
    console.log(playerNames[currentPlayer] + ' was sent to the penalty box')
    inPenaltyBox[currentPlayer] = true

    nextPlayer()
    return true
  }

  function nextPlayer() {
    currentPlayer = (currentPlayer + 1) % playerNames.length;
  }

  function movePlayerPosition(roll) {
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

  game.createQuestionSet()

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



