using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame.Core
{
  public class Game
  {
    public static readonly int NUMBER_OF_BOARD_SQUARES = 12;
    public static readonly int MINIMUM_PLAYERS = 2;
    public static readonly int MAX_QUESTIONS_PER_CATEGORY = 50;

    public bool IsPlayable => NumberOfPlayers >= MINIMUM_PLAYERS;
    public int NumberOfPlayers => _players.Count;

    private readonly string[] _categories = { "Pop", "Science", "Sports", "Rock" };
    private readonly List<string> _players = new List<string>();
    private readonly int[] _places = new int[6];
    private readonly int[] _purses = new int[6];
    private readonly bool[] _inPenaltyBox = new bool[6];
    private readonly Dictionary<string, List<string>> _questions = new Dictionary<string, List<string>>();

    private readonly LinkedList<string> _popQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _scienceQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _sportsQuestions = new LinkedList<string>();
    private readonly LinkedList<string> _rockQuestions = new LinkedList<string>();

    private bool _hasPlayerWon => CurrentPlayersGoldCoins != 6;
    private string _currentPlayerName => _players[_currentPlayer];
    private string _currentCategory => _categories[CurrentPlayerPlace % 4];

    private int _currentPlayer;
    private bool _isLeavingPenaltyBox;
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

    public Game()
    {
      _questions = _categories.ToDictionary(
        category => category,
        category => Enumerable
          .Repeat($"{category} Question ", MAX_QUESTIONS_PER_CATEGORY)
          .Select((question, index) => question + index)
          .ToList());

      for (int index = 0; index < MAX_QUESTIONS_PER_CATEGORY; index++)
      {
        _popQuestions.AddLast("Pop Question " + index);
        _scienceQuestions.AddLast(("Science Question " + index));
        _sportsQuestions.AddLast(("Sports Question " + index));
        _rockQuestions.AddLast("Rock Question " + index);
      }
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

    private void AdvancePlace(int roll)
    {
      CurrentPlayerPlace = (CurrentPlayerPlace + roll) % NUMBER_OF_BOARD_SQUARES;
    }

    private void MoveToNextPlayer()
    {
      _currentPlayer = (_currentPlayer + 1) % NumberOfPlayers;
    }

    private void AskQuestion()
    {
      var questions = _questions[_currentCategory];
      if (questions.Any())
      {
        System.Console.WriteLine(questions.First());
        questions.RemoveAt(0);
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

    private void PrintLocation()
    {
      Console.WriteLine(_currentPlayerName
                + "'s new location is "
                + CurrentPlayerPlace);
      Console.WriteLine("The category is " + _currentCategory);
    }
  }
}