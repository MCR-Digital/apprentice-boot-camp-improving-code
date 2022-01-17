import generator from 'random-seed'

let Game = function () {
  const players = new Array()
  const boardPositions = new Array(6)
  const purses = new Array(6)
  const inPenaltyBox = new Array(6)

  const popQuestions = new Array()
  const scienceQuestions = new Array()
  const sportsQuestions = new Array()
  const rockQuestions = new Array()

  let currentPlayer = 0
  let isGettingOutOfPenaltyBox = false

  let didPlayerWin = function () {
    return !(purses[currentPlayer] == 6)
  }

  let currentCategory = function () {
    if (boardPositions[currentPlayer] == 0) { return 'Pop' }
    if (boardPositions[currentPlayer] == 4) { return 'Pop' }
    if (boardPositions[currentPlayer] == 8) { return 'Pop' }
    if (boardPositions[currentPlayer] == 1) { return 'Science' }
    if (boardPositions[currentPlayer] == 5) { return 'Science' }
    if (boardPositions[currentPlayer] == 9) { return 'Science' }
    if (boardPositions[currentPlayer] == 2) { return 'Sports' }
    if (boardPositions[currentPlayer] == 6) { return 'Sports' }
    if (boardPositions[currentPlayer] == 10) { return 'Sports' }
    return 'Rock'
  }

  this.createRockQuestion = function (index) {
    return 'Rock Question ' + index
  }

  for (let i = 0; i < 50; i++) {
    popQuestions.push('Pop Question ' + i)
    scienceQuestions.push('Science Question ' + i)
    sportsQuestions.push('Sports Question ' + i)
    rockQuestions.push(this.createRockQuestion(i))
  }


  //magic number
  this.isPlayable = function (howManyPlayers) {
    return howManyPlayers >= 2
  }

  this.add = function (playerName) {
    players.push(playerName)
    boardPositions[this.howManyPlayers() - 1] = 0
    purses[this.howManyPlayers() - 1] = 0
    inPenaltyBox[this.howManyPlayers() - 1] = false

    console.log(playerName + ' was added')
    console.log('They are player number ' + players.length)

    return true
  }

  this.howManyPlayers = function () {
    return players.length
  }

  let askQuestion = function () {
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
        boardPositions[currentPlayer] = boardPositions[currentPlayer] + roll
        if (boardPositions[currentPlayer] > 11) {
          boardPositions[currentPlayer] = boardPositions[currentPlayer] - 12
        }

        console.log(players[currentPlayer] + "'s new location is " + boardPositions[currentPlayer])
        console.log('The category is ' + currentCategory())
        askQuestion()
      } else {
        console.log(players[currentPlayer] + ' is not getting out of the penalty box')
        isGettingOutOfPenaltyBox = false
      }
    } else {
      boardPositions[currentPlayer] = boardPositions[currentPlayer] + roll
      if (boardPositions[currentPlayer] > 11) {
        boardPositions[currentPlayer] = boardPositions[currentPlayer] - 12
      }

      console.log(players[currentPlayer] + "'s new location is " + boardPositions[currentPlayer])
      console.log('The category is ' + currentCategory())
      askQuestion()
    }
  }

  this.wasCorrectlyAnswered = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        console.log('Answer was correct!!!!')
        purses[currentPlayer] += 1
        console.log(players[currentPlayer] + ' now has ' +
            purses[currentPlayer] + ' Gold Coins.')

        let winner = didPlayerWin()
        currentPlayer += 1
        if (currentPlayer == players.length) { currentPlayer = 0 }

        return winner
      } else {
        currentPlayer += 1
        if (currentPlayer == players.length) { currentPlayer = 0 }
        return true
      }
    } else {
      console.log('Answer was correct!!!!')

      purses[currentPlayer] += 1
      console.log(players[currentPlayer] + ' now has ' +
          purses[currentPlayer] + ' Gold Coins.')

      let winner = didPlayerWin()

      currentPlayer += 1
      if (currentPlayer == players.length) { currentPlayer = 0 }

      return winner
    }
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
  let weHaveAWinner = false

  let game = new Game()

  game.add('Chet')
  game.add('Pat')
  game.add('Sue')

  const random = generator.create(i)

  do {
    game.roll(random.range(5) + 1)

    if (random.range(9) == 7) {
      weHaveAWinner= game.wasIncorrectlyAnswered()
    } else {
      weHaveAWinner= game.wasCorrectlyAnswered()
    }
  } while (weHaveAWinner)
}

export default gameRunner
