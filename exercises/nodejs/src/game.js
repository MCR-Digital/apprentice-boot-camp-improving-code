import generator from 'random-seed'

const numberOfCategories = 4
const numberOfQuestionsPerCategory = 50

var Game = function () {
  var players = new Array()

  var popQuestions = new Array()
  var scienceQuestions = new Array()
  var sportsQuestions = new Array()
  var rockQuestions = new Array()
  const board = [
    'Pop', 'Science', 'Sports', 'Rock','Pop', 'Science', 'Sports', 'Rock','Pop', 'Science', 'Sports', 'Rock',
  ]
  
  var questions = [popQuestions, scienceQuestions, sportsQuestions, rockQuestions]
  var questionCategories = ['Pop', 'Science', 'Sports', 'Rock']

  var currentPlayer = 0
  var isGettingOutOfPenaltyBox = false

  var didPlayerWin = function () {
    return !(players[currentPlayer].purse == 6)
  }

  var currentCategory = function () {
    return board[players[currentPlayer].place]
  }

  this.createQuestion = function(category, index){
    return `${category} Question ${index}`
  }

  for (var questionNumber = 0; questionNumber < numberOfQuestionsPerCategory; questionNumber++) {
    for(var j = 0; j < questionCategories.length; j++){
      questions[j].push(this.createQuestion(questionCategories[j], questionNumber))
    }
  }

  this.isPlayable = function (numberOfPlayers) {
    return numberOfPlayers >= 2
  }

  this.addNewPlayer = function (playerName) {
    players.push({
      playerName,
      place: 0,
      purse: 0,
      isInPenaltyBox: false
    })

    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)

    return true
  }

  this.numberOfPlayers = function () {
    return players.length
  }

  var askQuestion = function () {
    console.log('The category is ' + currentCategory())
    if (currentCategory() == 'Pop') { console.log(popQuestions.shift()) }
    if (currentCategory() == 'Science') { console.log(scienceQuestions.shift()) }
    if (currentCategory() == 'Sports') { console.log(sportsQuestions.shift()) }
    if (currentCategory() == 'Rock') { console.log(rockQuestions.shift()) }
  }

  this.roll = function (roll) {
    displayGameState()

    if (players[currentPlayer].isInPenaltyBox){
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true
        console.log(players[currentPlayer].playerName + ' is getting out of the penalty box')
      } else {
        console.log(players[currentPlayer].playerName + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
        return
      }
    } 

    movePlayer()
    askQuestion()

    function displayGameState() {
      console.log(players[currentPlayer].playerName + ' is the current player')
      console.log('They have rolled a ' + roll)
    }

    function movePlayer() {
      players[currentPlayer].place = players[currentPlayer].place + roll
      if (players[currentPlayer].place > 11) {
        players[currentPlayer].place = players[currentPlayer].place - 12
      }
      console.log(players[currentPlayer].playerName + "'s new location is " + players[currentPlayer].place)
    }
  }

  this.wasCorrectlyAnswered = function () {
    if (allowedToAnswer()) {
      return handleCorrectAnswer();
    } else {
      return nextPlayer();
    }

    function allowedToAnswer() {return players[currentPlayer].isInPenaltyBox && isGettingOutOfPenaltyBox || !players[currentPlayer].isInPenaltyBox}

    function handleCorrectAnswer(){
      console.log('Answer was correct!!!!')
      addCoin()
      var winner = didPlayerWin()
      nextPlayer()
      return winner
    }

    function nextPlayer() {
      currentPlayer += 1
      if (currentPlayer == players.length) { currentPlayer = 0}
      return true
    }

    function addCoin() {
      players[currentPlayer].purse += 1
      console.log(players[currentPlayer].playerName + ' now has ' +
        players[currentPlayer].purse + ' Gold Coins.')
    }
  }

  this.wasWronglyAnswered = function () {
    console.log('Question was incorrectly answered')
    console.log(players[currentPlayer].playerName + ' was sent to the penalty box')
    players[currentPlayer].isInPenaltyBox = true

    currentPlayer += 1
    if (currentPlayer == players.length) { currentPlayer = 0 }
    return true
  }
}

const gameRunner = (i) => {
  var notAWinner = false

  var game = new Game()

  game.addNewPlayer('Chet')
  game.addNewPlayer('Pat')
  game.addNewPlayer('Sue')

  const random = generator.create(i)

  do {
    game.roll(random.range(5) + 1)

    if (random.range(9) == 7) {
      notAWinner = game.wasWronglyAnswered()
    } else {
      notAWinner = game.wasCorrectlyAnswered()
    }
  } while (notAWinner)
}

export default gameRunner
