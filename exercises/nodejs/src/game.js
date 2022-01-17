import generator from "random-seed";

var Game = function () {
	var players = new Array();
	const maxNumberOfPlayers = 6;
	const numberOfPositionsOnTheBoard = 12;
	var playersPositionOnTheBoard = new Array(maxNumberOfPlayers);
	var playersScore = new Array(maxNumberOfPlayers);
	const maxScore = 6;
	var inPenaltyBox = new Array(maxNumberOfPlayers);
	var popQuestions = new Array();
	var scienceQuestions = new Array();
	var sportsQuestions = new Array();
	var rockQuestions = new Array();
	var currentPlayer = 0;
	var isGettingOutOfPenaltyBox = false;
	var didPlayerWin = function () {
		return !(playersScore[currentPlayer] == maxScore);
		//is their purse not equal to 6?
		//returns true, their purse is NOT equal to 6
		//or false, their purse is equal to 6
	};
	var currentCategory = function () {
		const playersCurrentPostion = playersPositionOnTheBoard[currentPlayer];
		if ([0, 4, 8].includes(playersCurrentPostion)) {
			return "Pop";
		}
		if ([1, 5, 9].includes(playersCurrentPostion)) {
			return "Science";
		}
		if ([2, 6, 10].includes(playersCurrentPostion)) {
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
		players.push(playerName);
		playersPositionOnTheBoard[this.howManyPlayers() - 1] = 0;
		playersScore[this.howManyPlayers() - 1] = 0;
		inPenaltyBox[this.howManyPlayers() - 1] = false;
		console.log(playerName + " was added");
		console.log("They are player number " + players.length);
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
	this.actionAfterRoll = function (roll) {
		console.log(players[currentPlayer] + " is the current player");
		console.log("They have rolled a " + roll);
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				//if they roll a number NOT divisible by 2 - an ODD NUMBER - thye wll escape the penalty box
				console.log(players[currentPlayer] + " is getting out of the penalty box");
				playersPositionOnTheBoard[currentPlayer] = playersPositionOnTheBoard[currentPlayer] + roll;
				//their position gets set to their current place + the number they have rolled
				if (playersPositionOnTheBoard[currentPlayer] > numberOfPositionsOnTheBoard - 1) {
					playersPositionOnTheBoard[currentPlayer] = playersPositionOnTheBoard[currentPlayer] - numberOfPositionsOnTheBoard;
				}
				// if their position is greater than 11, their new position is -12
				console.log(players[currentPlayer] + "'s new location is " + playersPositionOnTheBoard[currentPlayer]);
				// say their new position
				console.log("The category is " + currentCategory());
				// say what category it is
				askQuestion();
				//ask them a question
			} else {
				console.log(players[currentPlayer] + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}
		} else {
			playersPositionOnTheBoard[currentPlayer] = playersPositionOnTheBoard[currentPlayer] + roll;
			if (playersPositionOnTheBoard[currentPlayer] > numberOfPositionsOnTheBoard - 1) {
				playersPositionOnTheBoard[currentPlayer] = playersPositionOnTheBoard[currentPlayer] - numberOfPositionsOnTheBoard;
			}
			console.log(players[currentPlayer] + "'s new location is " + playersPositionOnTheBoard[currentPlayer]);
			console.log("The category is " + currentCategory());
			askQuestion();
		}
	};
	this.wasCorrectlyAnswered = function () {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				console.log("Answer was correct!!!!");
				playersScore[currentPlayer] += 1;
				console.log(players[currentPlayer] + " now has " + playersScore[currentPlayer] + " Gold Coins.");
				var winner = didPlayerWin();
				currentPlayer += 1;
				if (currentPlayer == players.length) {
					currentPlayer = 0;
				}
				return winner;
			} else {
				currentPlayer += 1;
				if (currentPlayer == players.length) {
					currentPlayer = 0;
				}
				return true;
			}
		} else {
			console.log("Answer was correct!!!!");
			playersScore[currentPlayer] += 1;
			console.log(players[currentPlayer] + " now has " + playersScore[currentPlayer] + " Gold Coins.");
			var winner = didPlayerWin(); //if true, they don't have 6 coins
			//if false, they do have six coins?
			currentPlayer += 1;
			if (currentPlayer == players.length) {
				currentPlayer = 0;
			}
			//turns - if the last player has been go back to player 0
			return winner;
		}
	};
	this.answerWasWrongAndCheckThereIsNoWinner = function () {
		console.log("Question was incorrectly answered");
		console.log(players[currentPlayer] + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		//they are in the penalty box, but can keep playing to get out of this
		currentPlayer += 1;
		if (currentPlayer == players.length) {
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
			notAWinner = game.wasCorrectlyAnswered();
		}
		//set notAWinner variable to point to true or false
	} while (notAWinner); // while not a winner is true, keep rolling?
};
export default gameRunner;
