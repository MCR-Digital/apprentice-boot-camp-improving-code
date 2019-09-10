using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame.Core
{
  public class Game
  {
    public const int NUMBER_OF_BOARD_SQUARES = 12;
    public const int MINIMUM_PLAYERS = 2;
    public const int MAX_QUESTIONS_PER_CATEGORY = 50;

    private readonly string[] _categories = new string[] { "Pop", "Science", "Sports", "Rock" };
    private readonly List<string> _players = new List<string>();
    private readonly int[] _places = new int[6];
    private readonly int[] _purses = new int[6];
    private readonly bool[] _inPenaltyBox = new bool[6];
    private readonly LinkedList<string> _popQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _scienceQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _sportsQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _rockQuestions = new LinkedList<string>();

    private int _currentPlayer = 0;
    private bool _isLeavingPenaltyBox;

    public Game()
    {
      for (int index = 0; index < MAX_QUESTIONS_PER_CATEGORY; index++)
      {
        _popQuestions.AddLast("Pop Question " + index);
        _scienceQuestions.AddLast(("Science Question " + index));
        _sportsQuestions.AddLast(("Sports Question " + index));
        _rockQuestions.AddLast("Rock Question " + index);
      }
    }

    public bool IsPlayable => NumberOfPlayers >= MINIMUM_PLAYERS;
    public int NumberOfPlayers => _players.Count;

    private bool _hasPlayerWon => !(CurrentPlayersGoldCoins == 6);
    private string _currentPlayerName => _players[_currentPlayer];
    private string _currentCategory => _categories[CurrentPlayerPlace % 4];

    private bool IsCurrentPlayerInPenaltyBox
    {
      get => _inPenaltyBox[_currentPlayer];
      set => _inPenaltyBox[_currentPlayer] = value;
    }
    private int CurrentPlayerPlace
    {
      get => _places[_currentPlayer];
      set => _places[_currentPlayer] = value;
    }

    private int CurrentPlayersGoldCoins
    {
      get => _purses[_currentPlayer];
      set => _purses[_currentPlayer] = value;
    }

    public bool AddPlayer(String playerName)
    {
      _players.Add(playerName);
      _places[NumberOfPlayers] = 0;
      _purses[NumberOfPlayers] = 0;
      _inPenaltyBox[NumberOfPlayers] = false;

      Console.WriteLine(playerName + " was added");
      Console.WriteLine("They are player number " + NumberOfPlayers);
      return true;
    }


    public void OnDieRoll(int roll)
    {
      Console.WriteLine(_currentPlayerName + " is the current player");
      Console.WriteLine("They have rolled a " + roll);

      if (IsCurrentPlayerInPenaltyBox)
      {
        var isRollOdd = roll % 2 != 0;
        Console.WriteLine(_currentPlayerName + $" is{(isRollOdd ? " " : " not ")}getting out of the penalty box");
        _isLeavingPenaltyBox = isRollOdd;

        if (!isRollOdd)
        {
          return;
        }
      }

      AdvancePlace(roll);
      PrintLocation();
      AskQuestion();
    }

    private void PrintLocation()
    {
      Console.WriteLine(_currentPlayerName
                + "'s new location is "
                + CurrentPlayerPlace);
      Console.WriteLine("The category is " + _currentCategory);
    }

    private void AdvancePlace(int roll)
    {
      CurrentPlayerPlace = (CurrentPlayerPlace + roll) % NUMBER_OF_BOARD_SQUARES;
    }

    private void AskQuestion()
    {
      if (_currentCategory == "Pop")
      {
        Console.WriteLine(_popQuestions.First());
        _popQuestions.RemoveFirst();
      }
      if (_currentCategory == "Science")
      {
        Console.WriteLine(_scienceQuestions.First());
        _scienceQuestions.RemoveFirst();
      }
      if (_currentCategory == "Sports")
      {
        Console.WriteLine(_sportsQuestions.First());
        _sportsQuestions.RemoveFirst();
      }
      if (_currentCategory == "Rock")
      {
        Console.WriteLine(_rockQuestions.First());
        _rockQuestions.RemoveFirst();
      }
    }

    public bool OnAnswer(bool isCorrect)
    {
      var winner = isCorrect
        ? OnCorrectAnswer()
        : OnIncorrectAnswer();

      MoveToNextPlayer();
      return winner;
    }

    public bool OnCorrectAnswer()
    {
      if (IsCurrentPlayerInPenaltyBox && !_isLeavingPenaltyBox)
      {
        return true;
      }

      var congratulationsMessage = IsCurrentPlayerInPenaltyBox
        ? "Answer was correct!!!!"
        : "Answer was corrent!!!!";

      Console.WriteLine(congratulationsMessage);
      CurrentPlayersGoldCoins++;
      PrintPlayerScore();
      var winner = _hasPlayerWon;

      return winner;
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(_currentPlayerName + " was sent to the penalty box");
      IsCurrentPlayerInPenaltyBox = true;
      return true;
    }

    private void PrintPlayerScore()
    {
      Console.WriteLine(_currentPlayerName
                      + " now has "
                      + CurrentPlayersGoldCoins
                      + " Gold Coins.");
    }

    private void MoveToNextPlayer()
    {
      _currentPlayer = (_currentPlayer + 1) % NumberOfPlayers;
    }
  }
}