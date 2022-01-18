/* eslint-disable no-console */

import generator from "random-seed";

var Game = function () {
  const players = new Array();
  const places = new Array(6);
  const purses = new Array(6);
  const inPenaltyBox = new Array(6);
  
  const popQuestions = new Array();
  const scienceQuestions = new Array();
  const sportsQuestions = new Array();
  const rockQuestions = new Array();
  
  const numberOfCategories = 4;
  const winningNumberofCoins = 6;
  const boardSize = 12;

  let currentPlayer = 0;
  let isGettingOutOfPenaltyBox = false;

  const checkPlayerHasWon = () => {
    return !(purses[currentPlayer] === winningNumberofCoins);
  };

  const getCurrentPlayer = () => {
    return players[currentPlayer];
  };

  const getCurrentLocation = () => {
    return places[currentPlayer];
  };

  this.getNumberOfPlayers = function () {
    return players.length;
  };

  this.getLastPlayer = function () {
    return this.getNumberOfPlayers() - 1;
  };

  this.setLocation = function (player, position) {
    places[player] = position;
  };

  this.setGoldCoins = function (player, coins) {
    purses[player] = coins;
  };

  this.setPenaltyBox = function (player, inBox) {
    inPenaltyBox[player] = inBox;
  };

  const currentCategory = () => {
    const categoryNumber = getCurrentLocation() % numberOfCategories;

    switch (categoryNumber) {
      case 0:
        return "Pop";
      case 1:
        return "Science";
      case 2:
        return "Sports";
      case 3:
        return "Rock";
    }
  };

  const createQuestion = (category, index) => {
    return `${category} Question ${index}`;
  };

  for (var i = 0; i < 50; i++) {
    popQuestions.push(createQuestion("Pop", i));
    scienceQuestions.push(createQuestion("Science", i));
    sportsQuestions.push(createQuestion("Sports", i));
    rockQuestions.push(createQuestion("Rock", i));
  }

  this.checkIsPlayable = function (howManyPlayers) {
    const minimumPlayers = 2;
    return howManyPlayers >= minimumPlayers;
  };

  this.addPlayer = function (playerName) {
    players.push(playerName);
    this.setLocation(this.getLastPlayer(), 0);
    this.setGoldCoins(this.getLastPlayer(), 0);
    this.setPenaltyBox(this.getLastPlayer(), false);

    console.log(playerName + " was added");
    console.log("They are player number " + this.getNumberOfPlayers());

    return true;
  };

  const askQuestion = () => {
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

  const movePlayer = (roll) => {
    this.setLocation(currentPlayer, getCurrentLocation() + roll);
    if (getCurrentLocation() > 11) {
      this.setLocation(currentPlayer, getCurrentLocation() - boardSize);
    }

    console.log(
      players[currentPlayer] + "'s new location is " + places[currentPlayer]
    );
    console.log("The category is " + currentCategory());
    askQuestion();
  };

  this.getInPenaltyBox = function () {
    return inPenaltyBox[currentPlayer];
  };

  this.roll = function (roll) {
    console.log(players[currentPlayer] + " is the current player");
    console.log("They have rolled a " + roll);
    const rollIsOddNumber = roll % 2 !== 0;

    if (this.getInPenaltyBox()) {
      if (roll % 2 !== 0) {
        isGettingOutOfPenaltyBox = true;

        console.log(
          players[currentPlayer] + " is getting out of the penalty box"
        );

        movePlayer(roll);
      } else {
        console.log(
          players[currentPlayer] + " is not getting out of the penalty box"
        );
        isGettingOutOfPenaltyBox = false;
      }
    } else {
      movePlayer(roll);
    }
  };

  const nextPlayer = () => {
    currentPlayer += 1;
    if (currentPlayer === players.length) {
      currentPlayer = 0;
    }
    return true;
  };

  this.canAnswerQuestion = () =>
    !this.getInPenaltyBox() || isGettingOutOfPenaltyBox;

  this.wasCorrectlyAnswered = function () {
    const addGoldCoin = () => {
      console.log("Answer was correct!!!!");
      purses[currentPlayer] += 1;
      console.log(
        getCurrentPlayer() +
          " now has " +
          purses[currentPlayer] +
          " Gold Coins."
      );

      var winner = checkPlayerHasWon();
      currentPlayer += 1;
      if (currentPlayer === players.length) {
        currentPlayer = 0;
      }

      return winner;
    };

    if (this.canAnswerQuestion()) {
      return addGoldCoin();
    } else {
      return nextPlayer();
    }
  };

  this.wasIncorrectlyAnswered = function () {
    console.log("Question was incorrectly answered");
    console.log(players[currentPlayer] + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;

    return nextPlayer();
  };
};

const gameRunner = (i) => {
  const game = new Game();
  
  let hasWonGame = false;

  game.addPlayer("Chet");
  game.addPlayer("Pat");
  game.addPlayer("Sue");

  const random = generator.create(i);

  do {
    game.roll(random.range(5) + 1);

    if (random.range(9) === 7) {
      hasWonGame = game.wasIncorrectlyAnswered();
    } else {
      hasWonGame = game.wasCorrectlyAnswered();
    }
  } while (hasWonGame);
};

export default gameRunner;
