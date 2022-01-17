import generator from "random-seed";

var Game = function () {
  var players = new Array();
  var places = new Array(6);
  var purses = new Array(6);
  var inPenaltyBox = new Array(6);

  var popQuestions = new Array();
  var scienceQuestions = new Array();
  var sportsQuestions = new Array();
  var rockQuestions = new Array();

  var currentPlayer = 0;
  var isGettingOutOfPenaltyBox = false;

  var checkPlayerHasWon = function () {
    return !(purses[currentPlayer] == 6);
  };

  const currentCategory = () => {
    const numberOfCategories = 4;
    const categoryNumber = places[currentPlayer] % numberOfCategories;

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

  const createRockQuestion = (category, index) => {
    return `${category} Question ${index}`;
  };

  for (var i = 0; i < 50; i++) {
    popQuestions.push(createRockQuestion("Pop", i));
    scienceQuestions.push(createRockQuestion("Science", i));
    sportsQuestions.push(createRockQuestion("Sports", i));
    rockQuestions.push(createRockQuestion("Rock", i));
  }

  const checkIsPlayable = (howManyPlayers) => {
    const minimumPlayers = 2;
    return howManyPlayers >= minimumPlayers;
  };

  this.addPlayer = function (playerName) {
    players.push(playerName);
    places[this.getNumberOfPlayers() - 1] = 0;
    purses[this.getNumberOfPlayers() - 1] = 0;
    inPenaltyBox[this.getNumberOfPlayers() - 1] = false;

    console.log(playerName + " was added");
    console.log("They are player number " + players.length);

    return true;
  };

  this.getNumberOfPlayers = function () {
    return players.length;
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
    places[currentPlayer] = places[currentPlayer] + roll;
    if (places[currentPlayer] > 11) {
      places[currentPlayer] = places[currentPlayer] - 12;
    }

    console.log(
      players[currentPlayer] + "'s new location is " + places[currentPlayer]
    );
    console.log("The category is " + currentCategory());
    askQuestion();
  };

  this.roll = function (roll) {
    console.log(players[currentPlayer] + " is the current player");
    console.log("They have rolled a " + roll);

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
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
    if (currentPlayer == players.length) {
      currentPlayer = 0;
    }
    return true;
  };

  this.wasCorrectlyAnswered = function () {
    const addGoldCoin = () => {
      console.log("Answer was correct!!!!");
      purses[currentPlayer] += 1;
      console.log(
        players[currentPlayer] +
          " now has " +
          purses[currentPlayer] +
          " Gold Coins."
      );

      var winner = checkPlayerHasWon();
      currentPlayer += 1;
      if (currentPlayer == players.length) {
        currentPlayer = 0;
      }

      return winner;
    };

    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        return addGoldCoin();
      } else {
        return nextPlayer();
      }
    } else {
      return addGoldCoin();
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
  var hasWonGame = false;

  var game = new Game();

  game.addPlayer("Chet");
  game.addPlayer("Pat");
  game.addPlayer("Sue");

  const random = generator.create(i);

  do {
    game.roll(random.range(5) + 1);

    if (random.range(9) == 7) {
      hasWonGame = game.wasIncorrectlyAnswered();
    } else {
      hasWonGame = game.wasCorrectlyAnswered();
    }
  } while (hasWonGame);
};

export default gameRunner;
