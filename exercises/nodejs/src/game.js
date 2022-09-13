import generator from 'random-seed'

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
    switch(places[currentPlayer]){
      case 0: case 4: case 8: return 'Pop'
      case 1: case 5: case 9: return 'Science'
      case 2: case 6: case 10: return 'Sports'
      default: return 'Rock';
    }
  }

  this.createQuestion = function(category, index){
    return `${category} Question ${index}`
  }

  for (var questionNumber = 0; questionNumber < 50; questionNumber++) {
    for(var j = 0; j < questionCategories.length; j++){
      questions[j].push(this.createQuestion(questionCategories[j], questionNumber))
    }
  }

  this.isPlayable = function (numberOfPlayers) {
    return numberOfPlayers >= 2
  }

  this.addNewPlayer = function (playerName) {
    players.push(playerName)
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
    console.log(players[currentPlayer] + ' is the current player')
    console.log('They have rolled a ' + roll)

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true
        console.log(players[currentPlayer] + ' is getting out of the penalty box')
        movePlayer()
        askQuestion()
      } else {
        console.log(players[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
        movePlayer();
        askQuestion()
    }

    function movePlayer() {
      places[currentPlayer] = places[currentPlayer] + roll
      if (places[currentPlayer] > 11) {
        places[currentPlayer] = places[currentPlayer] - 12
      }
      console.log(players[currentPlayer] + "'s new location is " + places[currentPlayer])

    }
  }

  this.wasCorrectlyAnswered = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        return handleCorrectAnswer()
      } else {
        return nextPlayer()
      }
    } else {
      return handleCorrectAnswer()
    }

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
      console.log(players[currentPlayer] + ' now has ' +
        purses[currentPlayer] + ' Gold Coins.')
    }
  }

  this.wasWronglyAnswered = function () {
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
