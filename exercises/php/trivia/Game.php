<?php

namespace trivia;

function echoln($string)
{
	echo $string . "\n";
}

class Game
{
	var $players;
	var $places;
	var $board;
	var $purses;
	var $inPenaltyBox;

	var $popQuestions;
	var $scienceQuestions;
	var $sportsQuestions;
	var $rockQuestions;

	var $currentPlayer = 0;
	var $isGettingOutOfPenaltyBox;

	private const MAX_QUESTIONS_PER_CATEGORY = 50;

	function  __construct()
	{
		$this->board = new GameBoard();


		$this->players = array();
		$this->places = array(0);
		$this->purses  = array(0);
		$this->inPenaltyBox  = array(0);

		$this->popQuestions = array();
		$this->scienceQuestions = array();
		$this->sportsQuestions = array();
		$this->rockQuestions = array();

		for ($index = 0; $index < self::MAX_QUESTIONS_PER_CATEGORY; $index++) {
			array_push($this->popQuestions, "Pop Question " . $index);
			array_push($this->scienceQuestions, ("Science Question " . $index));
			array_push($this->sportsQuestions, ("Sports Question " . $index));
			array_push($this->rockQuestions, $this->createRockQuestion($index));
		}
	}

	function createRockQuestion($index)
	{
		return "Rock Question " . $index;
	}

	function isPlayable()
	{
		return ($this->getPlayerCount() >= 2);
	}

	function addPlayer($playerName)
	{
		array_push($this->players, $playerName);
		$this->places[$this->getPlayerCount()] = 0;
		$this->purses[$this->getPlayerCount()] = 0;
		$this->inPenaltyBox[$this->getPlayerCount()] = false;

		echoln($playerName . " was added");
		echoln("They are player number " . count($this->players));
		return true;
	}

	function getPlayerCount()
	{
		return count($this->players);
	}

	function  rollDice($roll)
	{
		echoln($this->players[$this->currentPlayer] . " is the current player");
		echoln("They have rolled a " . $roll);

		if ($this->inPenaltyBox[$this->currentPlayer]) {

			$this->isRollOddAndDoesOtherThings($roll);
		} else {
			$this->movePlaces($roll);
		}
	}

	function  askQuestion()
	{
		if ($this->board->getcurrentCategory($this->places[$this->currentPlayer]) == "Pop")
			echoln(array_shift($this->popQuestions));
		if ($this->board->getcurrentCategory($this->places[$this->currentPlayer]) == "Science")
			echoln(array_shift($this->scienceQuestions));
		if ($this->board->getcurrentCategory($this->places[$this->currentPlayer]) == "Sports")
			echoln(array_shift($this->sportsQuestions));
		if ($this->board->getcurrentCategory($this->places[$this->currentPlayer]) == "Rock")
			echoln(array_shift($this->rockQuestions));
	}




	function wasCorrectlyAnswered()
	{
		if ($this->inPenaltyBox[$this->currentPlayer]) {
			if ($this->isGettingOutOfPenaltyBox) {
				echoln("Answer was correct!!!!");
				$this->getAnswerOutcome('correct');

				$winner = $this->didPlayerWin();
				$this->currentPlayer++;
				if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;

				return $winner;
			} else {
				$this->currentPlayer++;
				if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
				return true;
			}
		} else {

			echoln("Answer was corrent!!!!");
			$this->getAnswerOutcome('correct');

			$winner = $this->didPlayerWin();

			$this->nextPlayer();

			return $winner;
		}
	}

	function wasNotCorrectlyAnswered()
	{
		$this->getAnswerOutcome('incorrect');

		$this->inPenaltyBox[$this->currentPlayer] = true;

		$this->nextPlayer();

		return true;
	}


	function didPlayerWin()
	{
		return !($this->purses[$this->currentPlayer] == 6);
	}

	function nextPlayer()
	{
		$this->currentPlayer++;
		if ($this->currentPlayer == count($this->players)) $this->currentPlayer = 0;
	}

	function getAnswerOutcome($answer)
	{
		if ($answer === "correct") {
			$this->purses[$this->currentPlayer]++;
			echoln($this->players[$this->currentPlayer]
				. " now has "
				. $this->purses[$this->currentPlayer]
				. " Gold Coins.");
		} else {
			echoln("Question was incorrectly answered");
			echoln($this->players[$this->currentPlayer] . " was sent to the penalty box");
		}
	}

	function movePlaces($places)
	{

		$this->places[$this->currentPlayer] = $this->currentPlayerPlace()  + $places;
		if ($this->places[$this->currentPlayer] > 11) $this->places[$this->currentPlayer] = $this->currentPlayerPlace() - 12;

		echoln($this->players[$this->currentPlayer]
			. "'s new location is "
			. $this->currentPlayerPlace());
		echoln("The category is " . $this->board->getcurrentCategory($this->currentPlayerPlace()));

		$this->askQuestion();
	}

	function currentPlayerPlace()
	{
		return $this->places[$this->currentPlayer];
	}

	function isRollOddAndDoesOtherThings($roll)
	{
		if ($roll % 2 != 0) {
			$this->isGettingOutOfPenaltyBox = true;

			echoln($this->players[$this->currentPlayer] . " is getting out of the penalty box");
			$this->movePlaces($roll);
		} else {
			echoln($this->players[$this->currentPlayer] . " is not getting out of the penalty box");
			$this->isGettingOutOfPenaltyBox = false;
		}
	}
}
