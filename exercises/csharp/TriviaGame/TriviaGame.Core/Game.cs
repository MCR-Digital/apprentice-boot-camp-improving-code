using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame.Core
{
  public class Game
  {
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

    public bool IsPlayable()
    {
      return (GetNumberOfPlayers() >= 2);
    }

    public bool AddPlayer(String playerName)
    {
      _players.Add(playerName);
      _places[GetNumberOfPlayers()] = 0;
      _purses[GetNumberOfPlayers()] = 0;
      _inPenaltyBox[GetNumberOfPlayers()] = false;

      Console.WriteLine(playerName + " was added");
      Console.WriteLine("They are player number " + _players.Count);
      return true;
    }

    public int GetNumberOfPlayers()
    {
      return _players.Count;
    }

    public void OnDieRoll(int roll)
    {
      Console.WriteLine(_players[_currentPlayer] + " is the current player");
      Console.WriteLine("They have rolled a " + roll);

      if (_inPenaltyBox[_currentPlayer])
      {
        if (roll % 2 != 0)
        {
          _isGettingOutOfPenaltyBox = true;

          Console.WriteLine(_players[_currentPlayer] + " is getting out of the penalty box");
          AdvancePlace(roll);

          PrintLocation();
          AskQuestion();
        }
        else
        {
          Console.WriteLine(_players[_currentPlayer] + " is not getting out of the penalty box");
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
      Console.WriteLine(_players[_currentPlayer]
                + "'s new location is "
                + _places[_currentPlayer]);
      Console.WriteLine("The category is " + CurrentCategory());
    }

    private void AdvancePlace(int roll)
    {
      _places[_currentPlayer] = (_places[_currentPlayer] + roll) % 12;
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
      if (_places[_currentPlayer] == 0) return "Pop";
      if (_places[_currentPlayer] == 4) return "Pop";
      if (_places[_currentPlayer] == 8) return "Pop";
      if (_places[_currentPlayer] == 1) return "Science";
      if (_places[_currentPlayer] == 5) return "Science";
      if (_places[_currentPlayer] == 9) return "Science";
      if (_places[_currentPlayer] == 2) return "Sports";
      if (_places[_currentPlayer] == 6) return "Sports";
      if (_places[_currentPlayer] == 10) return "Sports";
      return "Rock";
    }

    public bool OnCorrectAnswer()
    {
      if (_inPenaltyBox[_currentPlayer] && !_isGettingOutOfPenaltyBox)
      {
        MoveToNextPlayer();
        return true;
      }

      var congratulationsMessage = _inPenaltyBox[_currentPlayer]
        ? "Answer was correct!!!!"
        : "Answer was corrent!!!!";

      Console.WriteLine(congratulationsMessage);
      _purses[_currentPlayer]++;
      PrintPlayerScore();
      var winner = HasPlayerWon();
      MoveToNextPlayer();

      return winner;
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(_players[_currentPlayer] + " was sent to the penalty box");
      _inPenaltyBox[_currentPlayer] = true;

      MoveToNextPlayer();
      return true;
    }


    private bool HasPlayerWon()
    {
      return !(_purses[_currentPlayer] == 6);
    }

    private void PrintPlayerScore()
    {
      Console.WriteLine(_players[_currentPlayer]
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