import generator from "random-seed";

const Game = function () {
  const players = new Array();
  const minimumPlayers = 2;
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

  let didPlayerWin = function () {
    return !(purses[currentPlayer] == 6);
  };

  let currentCategory = function () {
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

  this.createRockQuestion = function (index) {
    return "Rock Question " + index;
  };

  for (let i = 0; i < 50; i++) {
    popQuestions.push("Pop Question " + i);
    scienceQuestions.push("Science Question " + i);
    sportsQuestions.push("Sports Question " + i);
    rockQuestions.push(this.createRockQuestion(i));
  }

  this.isPlayable = function (howManyPlayers) {
    return howManyPlayers >= minimumPlayers;
  };

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

  let askQuestion = function () {
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

  function addGoldCoin() {
    console.log("Answer was correct!!!!");
    purses[currentPlayer] += 1;
    console.log(
      players[currentPlayer] +
        " now has " +
        purses[currentPlayer] +
        " Gold Coins."
    );
  }

  function changePlayer() {
    currentPlayer += 1;
    if (currentPlayer == players.length) {
      currentPlayer = 0;
    }
  }

  this.wasCorrectlyAnswered = function () {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        addGoldCoin();
        let winner = didPlayerWin();
        changePlayer();
        return winner;
      } else {
        changePlayer();
        return true;
      }
    } else {
      addGoldCoin();
      let winner = didPlayerWin();
      changePlayer();
      return winner;
    }
  };

  this.wasIncorrectlyAnswered = function () {
    console.log("Question was incorrectly answered");
    console.log(players[currentPlayer] + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;

    changePlayer();
    return true;
  };
};

const gameRunner = (i) => {
  let weHaveAWinner = false;

  let game = new Game();

  game.add("Chet");
  game.add("Pat");
  game.add("Sue");

  const random = generator.create(i);

  do {
    game.roll(random.range(5) + 1);

    if (random.range(9) == 7) {
      weHaveAWinner = game.wasIncorrectlyAnswered();
    } else {
      weHaveAWinner = game.wasCorrectlyAnswered();
    }
  } while (weHaveAWinner);
};

export default gameRunner;
