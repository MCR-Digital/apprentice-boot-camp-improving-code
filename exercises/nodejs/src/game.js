const Game = function () {
  const players = new Array();
  const maximumPlayers = 6;
  const boardPositions = new Array(maximumPlayers);
  const purses = new Array(maximumPlayers);
  const inPenaltyBox = new Array(maximumPlayers);
  const popQuestions = new Array();
  const scienceQuestions = new Array();
  const sportsQuestions = new Array();
  const rockQuestions = new Array();

  let numberOfCatagories = 4;
  let currentPlayer = 0;
  let isGettingOutOfPenaltyBox = false;

  const didPlayerWin = function () {
    return !(purses[currentPlayer] == 6);
  };

  const currentCategory = function () {
    if (boardPositions[currentPlayer] % numberOfCatagories === 0) {
      return "Pop";
    }
    if (boardPositions[currentPlayer] % numberOfCatagories === 1) {
      return "Science";
    }
    if (boardPositions[currentPlayer] % numberOfCatagories === 2) {
      return "Sports";
    }
    return "Rock";
  };

  for (let i = 0; i < 50; i++) {
    popQuestions.push("Pop Question " + i);
    scienceQuestions.push("Science Question " + i);
    sportsQuestions.push("Sports Question " + i);
    rockQuestions.push("Rock Question " + i);
  }

  this.add = function (playerName) {
    players.push(playerName);
    boardPositions[this.howManyPlayers() - 1] = 0;
    purses[this.howManyPlayers() - 1] = 0;
    inPenaltyBox[this.howManyPlayers() - 1] = false;

    console.log(playerName + " was added");
    console.log("They are player number " + players.length);

    return true;
  };

  this.howManyPlayers = function () {
    return players.length;
  };

  const askQuestion = function () {
    switch (currentCategory()) {
      case "Pop":
        console.log(popQuestions.shift());
        break;
      case "Science":
        console.log(scienceQuestions.shift());
        break;
      case "Sports":
        console.log(sportsQuestions.shift());
        break;
      case "Rock":
        console.log(rockQuestions.shift());
        break;
    }
  };

  this.movePlayer = function (roll) {
    boardPositions[currentPlayer] = boardPositions[currentPlayer] + roll;
    if (boardPositions[currentPlayer] > 11) {
      boardPositions[currentPlayer] = boardPositions[currentPlayer] - 12;
    }
    console.log(
      players[currentPlayer] +
        "'s new location is " +
        boardPositions[currentPlayer]
    );
    console.log("The category is " + currentCategory());
  };

  this.isInPenaltyBox = function () {
    return inPenaltyBox[currentPlayer];
  };

  this.roll = function (roll) {
    console.log(players[currentPlayer] + " is the current player");
    console.log("They have rolled a " + roll);

    if (this.isInPenaltyBox()) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true;
        console.log(
          players[currentPlayer] + " is getting out of the penalty box"
        );
        this.movePlayer(roll);
        askQuestion();
      } else {
        console.log(
          players[currentPlayer] + " is not getting out of the penalty box"
        );
        isGettingOutOfPenaltyBox = false;
      }
    } else {
      this.movePlayer(roll);
      askQuestion();
    }
  };

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
    console.log("Question was incorrectly answered");
    console.log(players[currentPlayer] + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;
    this.getNextPlayer();
    return true;
  };
};

export default Game;
