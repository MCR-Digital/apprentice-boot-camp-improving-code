import generator from 'random-seed'

const numberOfCategories = 4
const numberOfQuestionsPerCategory = 50

var Game = function () {
  var players = new Array()
  var places = new Array(6)
  var purses = new Array(6)
  var inPenaltyBox = new Array(6)

  var popQuestions = new Array()
  var scienceQuestions = new Array()
  var sportsQuestions = new Array()
  var rockQuestions = new Array()
  
  var questions = [popQuestions, scienceQuestions, sportsQuestions, rockQuestions]
  var questionCategories = ['Pop', 'Science', 'Sports', 'Rock']

  var currentPlayer = 0
  var isGettingOutOfPenaltyBox = false

  var didPlayerWin = function () {
    return !(purses[currentPlayer] == 6)
  }

  var currentCategory = function () {
      if (places[currentPlayer] % numberOfCategories === 0) { return 'Pop' }
	    if (places[currentPlayer] % numberOfCategories === 1) { return 'Science' }
	    if (places[currentPlayer] % numberOfCategories === 2) { return 'Sports' }
	    else { return 'Rock' }
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
    players.push({playerName})
    places[this.numberOfPlayers() - 1] = 0
    purses[this.numberOfPlayers() - 1] = 0
    inPenaltyBox[this.numberOfPlayers() - 1] = false

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

    if (inPenaltyBox[currentPlayer]){
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
      places[currentPlayer] = places[currentPlayer] + roll
      if (places[currentPlayer] > 11) {
        places[currentPlayer] = places[currentPlayer] - 12
      }
      console.log(players[currentPlayer].playerName + "'s new location is " + places[currentPlayer])
    }
  }

  this.wasCorrectlyAnswered = function () {
    if (allowedToAnswer()) {
      return handleCorrectAnswer();
    } else {
      return nextPlayer();
    }

    function allowedToAnswer() {return inPenaltyBox[currentPlayer] && isGettingOutOfPenaltyBox || !inPenaltyBox[currentPlayer]}

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
      purses[currentPlayer] += 1
      console.log(players[currentPlayer].playerName + ' now has ' +
        purses[currentPlayer] + ' Gold Coins.')
    }
  }

  this.wasWronglyAnswered = function () {
    console.log('Question was incorrectly answered')
    console.log(players[currentPlayer].playerName + ' was sent to the penalty box')
    inPenaltyBox[currentPlayer] = true

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
