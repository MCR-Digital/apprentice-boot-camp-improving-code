using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame.Core
{
  public class Game
  {
    int NUMBER_OF_BOARD_SQUARES = 12;
    int MINIMUM_PLAYERS = 2;
    List<string> _players = new List<string>();

    int[] _places = new int[6];
    int[] _purses = new int[6];

    bool[] _inPenaltyBox = new bool[6];

    LinkedList<string> _popQuestions = new LinkedList<string>();
    LinkedList<string> _scienceQuestions = new LinkedList<string>();
    LinkedList<string> _sportsQuestions = new LinkedList<string>();
    LinkedList<string> _rockQuestions = new LinkedList<string>();

    int _currentPlayer = 0;
    bool _isLeavingPenaltyBox;

    public Game()
    {
      for (int index = 0; index < 50; index++)
      {
        _popQuestions.AddLast("Pop Question " + index);
        _scienceQuestions.AddLast(("Science Question " + index));
        _sportsQuestions.AddLast(("Sports Question " + index));
        _rockQuestions.AddLast("Rock Question " + index);
      }
    }

    public bool IsPlayable => NumberOfPlayers >= MINIMUM_PLAYERS;
    public int NumberOfPlayers => _players.Count;

    private bool HasPlayerWon => !(CurrentPlayersGoldCoins == 6);
    private string CurrentPlayerName => _players[_currentPlayer];

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
      Console.WriteLine(CurrentPlayerName + " is the current player");
      Console.WriteLine("They have rolled a " + roll);

      if (IsCurrentPlayerInPenaltyBox)
      {
        var isRollOdd = roll % 2 != 0;
        Console.WriteLine(CurrentPlayerName + $" is{(isRollOdd ? " " : " not ")}getting out of the penalty box");
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
      Console.WriteLine(CurrentPlayerName
                + "'s new location is "
                + CurrentPlayerPlace);
      Console.WriteLine("The category is " + CurrentCategory());
    }

    private void AdvancePlace(int roll)
    {
      CurrentPlayerPlace = (CurrentPlayerPlace + roll) % NUMBER_OF_BOARD_SQUARES;
    }

    private void AskQuestion()
    {
      if (CurrentCategory() == "Pop")
      {
        Console.WriteLine(_popQuestions.First());
        _popQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Science")
      {
        Console.WriteLine(_scienceQuestions.First());
        _scienceQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Sports")
      {
        Console.WriteLine(_sportsQuestions.First());
        _sportsQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Rock")
      {
        Console.WriteLine(_rockQuestions.First());
        _rockQuestions.RemoveFirst();
      }
    }


    private String CurrentCategory()
    {
      switch (CurrentPlayerPlace % 4)
      {
        case 0: return "Pop";
        case 1: return "Science";
        case 2: return "Sports";
        default: return "Rock";
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
      var winner = HasPlayerWon;

      return winner;
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(CurrentPlayerName + " was sent to the penalty box");
      IsCurrentPlayerInPenaltyBox = true;
      return true;
    }

    private void PrintPlayerScore()
    {
      Console.WriteLine(CurrentPlayerName
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