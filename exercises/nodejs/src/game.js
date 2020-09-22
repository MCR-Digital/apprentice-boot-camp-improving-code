import generator from "random-seed";

var Game = function () {
  var players = new Array();
  var boardSquares = new Array(6);
  var playerCoins = new Array(6);
  var inPenaltyBox = new Array(6);

  var popQuestions = new Array();
  var scienceQuestions = new Array();
  var sportsQuestions = new Array();
  var rockQuestions = new Array();

  var currentPlayer = 0;
  var isGettingOutOfPenaltyBox = false;

  var didPlayerWin = function () {
    return !(playerCoins[parseInt(currentPlayer)] === 6);
  };

  var currentCategory = function () {
    let returnVal;
    switch (boardSquares[parseInt(currentPlayer)]) {
      case 0:
      case 4:
      case 8:
        returnVal = "Pop";
        break;
      case 1:
      case 5:
      case 9:
        returnVal = "Science";
        break;
      case 2:
      case 6:
      case 10:
        returnVal = "Sports";
        break;
      default:
        returnVal = "Rock";
        break;
    }
    return returnVal;
  };

  this.createRockQuestion = function (index) {
    return "Rock Question " + index;
  };

  for (var i = 0; i < 50; i++) {
    popQuestions.push("Pop Question " + i);
    scienceQuestions.push("Science Question " + i);
    sportsQuestions.push("Sports Question " + i);
    rockQuestions.push(this.createRockQuestion(i));
  }

  this.isPlayable = function (howManyPlayers) {
    return howManyPlayers >= 2;
  };

  this.add = function (playerName) {
    players.push(playerName);
    boardSquares[this.howManyPlayers() - 1] = 0;
    playerCoins[this.howManyPlayers() - 1] = 0;
    inPenaltyBox[this.howManyPlayers() - 1] = false;

    console.log(playerName + " was added");
    console.log("They are player number " + players.length);

    return true;
  };

  this.howManyPlayers = function () {
    return players.length;
  };

  var askQuestion = function () {
    if (currentCategory() === "Pop") {
      console.log(popQuestions.shift());
    }
    if (currentCategory() === "Science") {
      console.log(scienceQuestions.shift());
    }
    if (currentCategory() === "Sports") {
      console.log(sportsQuestions.shift());
    }
    if (currentCategory() === "Rock") {
      console.log(rockQuestions.shift());
    }
  };

  var moveCurrentPlayer = function (places, currentPlayer, roll) {
    places[parseInt(currentPlayer)] = places[parseInt(currentPlayer)] + roll;
    if (places[parseInt(currentPlayer)] > 11) {
      places[parseInt(currentPlayer)] = places[parseInt(currentPlayer)] - 12;
    }
  };

  var afterRoll = function () {
    console.log(
      players[parseInt(currentPlayer)] +
        "'s new location is " +
        boardSquares[parseInt(currentPlayer)]
    );
    console.log("The category is " + currentCategory());
    askQuestion();
  };

  this.roll = function (roll) {
    console.log(players[parseInt(currentPlayer)] + " is the current player");
    console.log("They have rolled a " + roll);

    if (inPenaltyBox[parseInt(currentPlayer)]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true;

        console.log(
          players[parseInt(currentPlayer)] +
            " is getting out of the penalty box"
        );
        moveCurrentPlayer(boardSquares, currentPlayer, roll);

        afterRoll();
      } else {
        console.log(
          players[parseInt(currentPlayer)] +
            " is not getting out of the penalty box"
        );
        isGettingOutOfPenaltyBox = false;
      }
    } else {
      moveCurrentPlayer(boardSquares, currentPlayer, roll);
      afterRoll();
    }
  };

  var changePlayerCounter = function (currentPlayer, players) {
    currentPlayer += 1;
    if (currentPlayer === players.length) {
      currentPlayer = 0;
    }
    return currentPlayer;
  };

  var logPlayerCoins = function (purses, currentPlayer, players) {
    purses[parseInt(currentPlayer)] += 1;
    console.log(
      players[parseInt(currentPlayer)] +
        " now has " +
        purses[parseInt(currentPlayer)] +
        " Gold Coins."
    );
  };

  var correctAnswer = function () {
    console.log("Answer was correct!!!!");
    logPlayerCoins(playerCoins, currentPlayer, players);

    var winner = didPlayerWin();
    currentPlayer = changePlayerCounter(currentPlayer, players);

    return winner;
  };

  this.wasCorrectlyAnswered = function () {
    if (inPenaltyBox[parseInt(currentPlayer)]) {
      if (isGettingOutOfPenaltyBox) {
        return correctAnswer();
      } else {
        currentPlayer = changePlayerCounter(currentPlayer, players);
        return true;
      }
    } else {
      return correctAnswer();
    }
  };

  this.wrongAnswer = function () {
    console.log("Question was incorrectly answered");
    console.log(
      players[parseInt(currentPlayer)] + " was sent to the penalty box"
    );
    inPenaltyBox[parseInt(currentPlayer)] = true;

    currentPlayer = changePlayerCounter(currentPlayer, players);
    return true;
  };
};

const gameRunner = (i) => {
  var notAWinner = false;

  var game = new Game();

  game.add("Chet");
  game.add("Pat");
  game.add("Sue");

  const random = generator.create(i);

  do {
    game.roll(random.range(5) + 1);

    if (random.range(9) === 7) {
      notAWinner = game.wrongAnswer();
    } else {
      notAWinner = game.wasCorrectlyAnswered();
    }
  } while (notAWinner);
};

export default gameRunner;
