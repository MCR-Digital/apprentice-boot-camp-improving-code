import generator from "random-seed";

var Player = function (name) {
  var playerName = name;
}
var Game = function () {
  const maxNumberOfPlayers = 6;
  const numberOfPositionsOnTheBoard = 12;
  const maxScore = 6;

  var playerNames = new Array(); // we are trying to strangle this
  var players = new Array();
  var playersPositionOnTheBoard = new Array(maxNumberOfPlayers);

  var playersScore = new Array(maxNumberOfPlayers);
  var inPenaltyBox = new Array(maxNumberOfPlayers);

  var currentPlayer = 0;
  var isGettingOutOfPenaltyBox = false;

  
  var popQuestions = new Array();
  var scienceQuestions = new Array();
  var sportsQuestions = new Array();
  var rockQuestions = new Array();

  
  var didPlayerWin = function () {
    return !(playersScore[currentPlayer] == maxScore);
    //is their purse not equal to 6?
    //returns true, their purse is NOT equal to 6
    //or false, their purse is equal to 6
  };

  var currentCategory = function () {
    const playersCurrentPosition = playersPositionOnTheBoard[currentPlayer];
    if ([0, 4, 8].includes(playersCurrentPosition)) {
      return "Pop";
    }
    if ([1, 5, 9].includes(playersCurrentPosition)) {
      return "Science";
    }
    if ([2, 6, 10].includes(playersCurrentPosition)) {
      return "Sports";
    }
    return "Rock";
  };
  // + 4


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


  this.addAPlayerToTheGame = function (playerName) {
    playerNames.push(playerName);
    players.push(new Player(playerName))
    playersPositionOnTheBoard[this.howManyPlayers() - 1] = 0;
    playersScore[this.howManyPlayers() - 1] = 0;
    inPenaltyBox[this.howManyPlayers() - 1] = false;
    console.log(playerName + " was added");
    console.log("They are player number " + this.howManyPlayers());
    return true;
  };

  this.howManyPlayers = function () {
    return players.length;
  };

  var askQuestion = function () {

    if (currentCategory() == "Pop") {
      console.log(popQuestions.shift());
    }

    if (currentCategory() == "Science") {
      console.log(scienceQuestions.shift());
    }

    if (currentCategory() == "Sports") {
      console.log(sportsQuestions.shift());
    }

    if (currentCategory() == "Rock") {
      console.log(rockQuestions.shift());
    }

  };

  this.playerIsInPenaltyBox = function () {
    return inPenaltyBox[currentPlayer];
  };

  this.playerGetsOutOfPenaltyBox = function (roll) {
    return roll % 2 != 0;
  };

  this.playerTakesTurn = function (roll) {
    playersPositionOnTheBoard[currentPlayer] += roll;
    if (
      playersPositionOnTheBoard[currentPlayer] >
      numberOfPositionsOnTheBoard - 1
    ) {
      playersPositionOnTheBoard[currentPlayer] -= numberOfPositionsOnTheBoard;
    }
    console.log(
      playerNames[currentPlayer] + "'s new location is " +playersPositionOnTheBoard[currentPlayer]
    );
    console.log("The category is " + currentCategory());
    askQuestion();
  };


  this.actionAfterRoll = function (roll) {
    console.log(playerNames[currentPlayer] + " is the current player");
    console.log("They have rolled a " + roll);
    if (this.playerIsInPenaltyBox() && this.playerGetsOutOfPenaltyBox(roll)) {
      isGettingOutOfPenaltyBox = true;
      console.log(
        playerNames[currentPlayer] + " is getting out of the penalty box"
      );
      this.playerTakesTurn(roll);
    } else if (this.playerIsInPenaltyBox() & !this.playerGetsOutOfPenaltyBox(roll)) {
      console.log( playerNames[currentPlayer] + " is not getting out of the penalty box" );
      isGettingOutOfPenaltyBox = false;
    } else {
      this.playerTakesTurn(roll);
    }
  };


  this.moveToNextPlayer = function () {
    currentPlayer += 1;
    if (currentPlayer == this.howManyPlayers()) {
      currentPlayer = 0;
    }
  };

  this.answerWasCorrect = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        console.log("Answer was correct!!!!");
        playersScore[currentPlayer] += 1;
        console.log( playerNames[currentPlayer] + " now has " +  playersScore[currentPlayer] + " Gold Coins." );
        var winner = didPlayerWin();
        this.moveToNextPlayer();
        return winner;
      } else {
        this.moveToNextPlayer();
        return true;
      }
    } else {
      console.log("Answer was correct!!!!");
      playersScore[currentPlayer] += 1;
      console.log(playerNames[currentPlayer] + " now has " + playersScore[currentPlayer] +" Gold Coins." );
      var winner = didPlayerWin(); //if true, they don’t have 6 coins
      //if false, they do have six coins?
      this.moveToNextPlayer();
      //turns - if the last player has been go back to player 0
      return winner;
    }
  };
  this.answerWasWrongAndCheckThereIsNoWinner = function () {
    console.log("Question was incorrectly answered");
    console.log(playerNames[currentPlayer] + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;
    //they are in the penalty box, but can keep playing to get out of this
    currentPlayer += 1;
    if (currentPlayer == this.howManyPlayers()) {
      currentPlayer = 0;
    }
    return true;
    //checks turn - if this player is the last player in the set of of people, go back to player 0
  };
};
const gameRunner = (i) => {
  var notAWinner = false;
  var game = new Game();
  game.addAPlayerToTheGame("Chet");
  game.addAPlayerToTheGame("Pat");
  game.addAPlayerToTheGame("Sue");
  const random = generator.create(i);
  do {
    game.actionAfterRoll(random.range(5) + 1);
    if (random.range(9) == 7) {
      notAWinner = game.answerWasWrongAndCheckThereIsNoWinner();
    } else {
      notAWinner = game.answerWasCorrect();
    }
    //set notAWinner variable to point to true or false
  } while (notAWinner); // while not a winner is true, keep rolling?
};
export default gameRunner;