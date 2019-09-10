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
    bool _isGettingOutOfPenaltyBox;

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
    private bool HasPlayerWon => !(_purses[_currentPlayer] == 6);
    private string CurrentPlayerName => _players[_currentPlayer];
    private bool IsCurrentPlayerInPenalty
    {
      get => _inPenaltyBox[_currentPlayer];
      set => _inPenaltyBox[_currentPlayer] = value;
    }
    private int CurrentPlayerPlace
    {
      get => _places[_currentPlayer];
      set => _places[_currentPlayer] = value;
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

      if (IsCurrentPlayerInPenalty)
      {
        if (roll % 2 != 0)
        {
          _isGettingOutOfPenaltyBox = true;

          Console.WriteLine(CurrentPlayerName + " is getting out of the penalty box");
          AdvancePlace(roll);
          PrintLocation();
          AskQuestion();
        }
        else
        {
          Console.WriteLine(CurrentPlayerName + " is not getting out of the penalty box");
          _isGettingOutOfPenaltyBox = false;
        }
      }
      else
      {
        AdvancePlace(roll);
        PrintLocation();
        AskQuestion();
      }
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
      if (CurrentPlayerPlace == 0) return "Pop";
      if (CurrentPlayerPlace == 4) return "Pop";
      if (CurrentPlayerPlace == 8) return "Pop";
      if (CurrentPlayerPlace == 1) return "Science";
      if (CurrentPlayerPlace == 5) return "Science";
      if (CurrentPlayerPlace == 9) return "Science";
      if (CurrentPlayerPlace == 2) return "Sports";
      if (CurrentPlayerPlace == 6) return "Sports";
      if (CurrentPlayerPlace == 10) return "Sports";
      return "Rock";
    }

    public bool OnCorrectAnswer()
    {
      if (IsCurrentPlayerInPenalty && !_isGettingOutOfPenaltyBox)
      {
        MoveToNextPlayer();
        return true;
      }

      var congratulationsMessage = IsCurrentPlayerInPenalty
        ? "Answer was correct!!!!"
        : "Answer was corrent!!!!";

      Console.WriteLine(congratulationsMessage);
      _purses[_currentPlayer]++;
      PrintPlayerScore();
      var winner = HasPlayerWon;
      MoveToNextPlayer();

      return winner;
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(CurrentPlayerName + " was sent to the penalty box");
      IsCurrentPlayerInPenalty = true;

      MoveToNextPlayer();
      return true;
    }

    private void PrintPlayerScore()
    {
      Console.WriteLine(CurrentPlayerName
                      + " now has "
                      + _purses[_currentPlayer]
                      + " Gold Coins.");
    }

    private void MoveToNextPlayer()
    {
      _currentPlayer = (_currentPlayer + 1) % _players.Count;
    }
  }
}