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
    private string _currentCategory => _categories[_commander.BoardPosition % 4];
    private int _currentPlayer;
    private bool _isLeavingPenaltyBox;
    private bool IsCurrentPlayerInPenaltyBox => _commander.IsInPenaltyBox;

    public Game()
    {
      var board = new Board();

      _questions = _categories.ToDictionary(
        category => category,
        category => Enumerable
          .Repeat($"{category} Question ", MAX_QUESTIONS_PER_CATEGORY)
          .Select((question, index) => question + index)
          .ToList());
    }

    public void AddPlayer(String playerName)
    {
      _players.Add(new Player(playerName));
      Console.WriteLine($"{playerName} was added");
      Console.WriteLine($"They are player number {NumberOfPlayers}");
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

      _commander.Move(roll);
      _commander.PrintBoardPosition();
      AskQuestion();
    }

    private void MoveToNextPlayer()
    {
      _currentPlayer = (_currentPlayer + 1) % NumberOfPlayers;
    }

    private void AskQuestion()
    {
      Console.WriteLine($"The category is {_commander.Position?.Category}");
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