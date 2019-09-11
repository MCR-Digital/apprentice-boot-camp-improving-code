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
    private readonly Dictionary<string, List<string>> _questions = new Dictionary<string, List<string>>();

    private readonly List<Player> _players = new List<Player>();
    private Player _commander => _players[_currentPlayer];

    private bool _hasPlayerWon => _commander.GoldCoins != 6;
    private string _currentCategory => _categories[CurrentPlayerPlace % 4];
    private int _currentPlayer;
    private bool _isLeavingPenaltyBox;
    private bool IsCurrentPlayerInPenaltyBox => _commander.IsInPenaltyBox;

    private int CurrentPlayerPlace => _commander.BoardPosition;

    private int CurrentPlayersGoldCoins => _commander.GoldCoins;

    public Game()
    {
      _questions = _categories.ToDictionary(
        category => category,
        category => Enumerable
          .Repeat($"{category} Question ", MAX_QUESTIONS_PER_CATEGORY)
          .Select((question, index) => question + index)
          .ToList());
    }

    public bool AddPlayer(String playerName)
    {
      _players.Add(new Player(playerName));

      Console.WriteLine(playerName + " was added");
      Console.WriteLine("They are player number " + NumberOfPlayers);
      return true;
    }


    public void OnDieRoll(int roll)
    {
      Console.WriteLine(_commander.Name + " is the current player");
      Console.WriteLine("They have rolled a " + roll);

      if (IsCurrentPlayerInPenaltyBox)
      {
        var isRollOdd = roll % 2 != 0;
        Console.WriteLine(_commander.Name + $" is{(isRollOdd ? " " : " not ")}getting out of the penalty box");
        _isLeavingPenaltyBox = isRollOdd;

        if (!isRollOdd)
        {
          return;
        }
      }

      AdvancePlace(roll);
      _commander.PrintBoardPosition();
      Console.WriteLine("The category is " + _currentCategory);
      AskQuestion();
    }

    private void AdvancePlace(int roll)
    {
      _commander.Move(roll);
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
      _commander.AddCoin();
      _commander.PrintScore();
      var winner = _hasPlayerWon;

      return winner;
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(_commander.Name + " was sent to the penalty box");
      _commander.IsInPenaltyBox = true;
      return true;
    }
  }
}