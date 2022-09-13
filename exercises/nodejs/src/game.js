import generator from 'random-seed'

var Game = function () {
  let playerNames = new Array()
  let places = new Array(6)
  let purses = new Array(6)
  let inPenaltyBox = new Array(6)
  const numberOfFields = 12;

  let popQuestions = new Array()
  let scienceQuestions = new Array()
  let sportsQuestions = new Array()
  let rockQuestions = new Array()

  // var questions = [popQuestions, scienceQuestions, sportsQuestions, rockQuestions]
  // var categories = ['Pop', 'Science', 'Sports', 'Rock']
  // var Questions = {Pop:popQuestions, Science:scienceQuestions, Sports:sportsQuestions,Rock: rockQuestions}

  const maxQuestionsInCategory = 50;

  let currentPlayer = 0
  let isGettingOutOfPenaltyBox = false

  var didPlayerWin = function () {
    return !(purses[currentPlayer] == 6)
  }

  var currentCategory = function () { // every 4 fields
    if (places[currentPlayer] % 4 === 0) { return 'Pop' }
    if (places[currentPlayer] % 4 === 1) { return 'Science' }
    if (places[currentPlayer] % 4 === 2) { return 'Sports' }
    if (places[currentPlayer] % 4 === 3) { return 'Rock' }
  }

  this.createQuestion = function (category,index) {
    return category + ' Question ' + index
  }

  for (var i = 0; i < maxQuestionsInCategory; i++) {
    popQuestions.push(this.createQuestion('Pop', i))
    scienceQuestions.push(this.createQuestion('Science', i))
    sportsQuestions.push(this.createQuestion('Sports', i))
    rockQuestions.push(this.createQuestion('Rock', i))
    
    // for (var j = 0; j < questions.length; i++) {
    //   questions[j].push(this.createQuestion(categories[j], i))
    // }   // Good idea, but hard to implement

    // TRY THIS ONE
    // for (var j = 0; j < Object.keys(Questions).length; j++) {
    //   Questions.Object.keys[j].push(this.createQuestion(Object.keys[j], i))
    // }

  }

  this.isEnoughPlayers = function (howManyPlayers) {
    return howManyPlayers >= 2
  }

  this.add = function (playerName) {
    playerNames.push(playerName)
    places[this.howManyPlayers() - 1] = 0
    purses[this.howManyPlayers() - 1] = 0
    inPenaltyBox[this.howManyPlayers() - 1] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + playerNames.length)

    return true
  }

  this.howManyPlayers = function () {
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
        places[currentPlayer] = places[currentPlayer] + roll
        if (places[currentPlayer] > (numberOfFields-1)) {
          places[currentPlayer] = places[currentPlayer] - numberOfFields
        }

        console.log(playerNames[currentPlayer] + "'s new location is " + places[currentPlayer])
        console.log('The category is ' + currentCategory())
        askQuestion()
      } else {
        console.log(playerNames[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
      places[currentPlayer] = places[currentPlayer] + roll
      if (places[currentPlayer] > (numberOfFields-1)) {
        places[currentPlayer] = places[currentPlayer] - numberOfFields
      }

      console.log(playerNames[currentPlayer] + "'s new location is " + places[currentPlayer])
      console.log('The category is ' + currentCategory())
      askQuestion()
    }
  }

  this.wasCorrectlyAnswered = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        console.log('Answer was correct!!!!')
        purses[currentPlayer] += 1
        console.log(playerNames[currentPlayer] + ' now has ' +
            purses[currentPlayer] + ' Gold Coins.')

        var winner = didPlayerWin()
        currentPlayer += 1
        if (currentPlayer == playerNames.length) { currentPlayer = 0 }

        return winner
      } else {
        currentPlayer += 1
        if (currentPlayer == playerNames.length) { currentPlayer = 0 }
        return true
      }
    } else {
      console.log('Answer was correct!!!!')

      purses[currentPlayer] += 1
      console.log(playerNames[currentPlayer] + ' now has ' +
          purses[currentPlayer] + ' Gold Coins.')

      var winner = didPlayerWin()

      currentPlayer += 1
      if (currentPlayer == playerNames.length) { currentPlayer = 0 }

      return winner
    }
  }

  this.wrongAnswer = function () {
    console.log('Question was incorrectly answered')
    console.log(playerNames[currentPlayer] + ' was sent to the penalty box')
    inPenaltyBox[currentPlayer] = true

    currentPlayer += 1
    if (currentPlayer == playerNames.length) { currentPlayer = 0 }
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
      notAWinner = game.wrongAnswer()
    } else {
      notAWinner = game.wasCorrectlyAnswered()
    }
  } while (notAWinner)
}

export default gameRunner
