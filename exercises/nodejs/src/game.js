/* eslint-disable no-console */

const Game = function () {
  const maxNumberOfPlayers = 6;
  const players = new Array();
  const places = new Array(maxNumberOfPlayers);
  const purses = new Array(maxNumberOfPlayers);
  const inPenaltyBox = new Array(maxNumberOfPlayers);

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

  for (let index = 0; index < 50; index++) {
    popQuestions.push(createQuestion("Pop", index));
    scienceQuestions.push(createQuestion("Science", index));
    sportsQuestions.push(createQuestion("Sports", index));
    rockQuestions.push(createQuestion("Rock", index));
  }

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

  this.isLeavingPenaltyBox = function (isLeaving) {
    isGettingOutOfPenaltyBox = isLeaving;
    console.log(
      players[currentPlayer] +
        ` is ${!isLeaving ? "not " : ""}getting out of the penalty box`
    );
  };

  this.roll = function (roll) {
    console.log(players[currentPlayer] + " is the current player");
    console.log("They have rolled a " + roll);
    const rollIsOddNumber = roll % 2 !== 0;
    const canMove = !(this.getInPenaltyBox() && !rollIsOddNumber);

    this.getInPenaltyBox() && this.isLeavingPenaltyBox(rollIsOddNumber);

    canMove && movePlayer(roll);
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

      const winner = checkPlayerHasWon();
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

export default Game;
