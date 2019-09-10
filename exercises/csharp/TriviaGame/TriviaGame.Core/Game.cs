using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TriviaGame.Core
{
  public class Game
  {
    List<string> players = new List<string>();

    int[] places = new int[6];
    int[] purses = new int[6];

    bool[] inPenaltyBox = new bool[6];

    LinkedList<string> popQuestions = new LinkedList<string>();
    LinkedList<string> scienceQuestions = new LinkedList<string>();
    LinkedList<string> sportsQuestions = new LinkedList<string>();
    LinkedList<string> rockQuestions = new LinkedList<string>();

    int currentPlayer = 0;
    bool isGettingOutOfPenaltyBox;

    public Game()
    {
      for (int i = 0; i < 50; i++)
      {
        popQuestions.AddLast("Pop Question " + i);
        scienceQuestions.AddLast(("Science Question " + i));
        sportsQuestions.AddLast(("Sports Question " + i));
        rockQuestions.AddLast(CreateRockQuestion(i));
      }
    }

    public String CreateRockQuestion(int index)
    {
      return "Rock Question " + index;
    }

    public bool IsPlayable()
    {
      return (GetNumberOfPlayers() >= 2);
    }

    public bool AddPlayer(String playerName)
    {
      players.Add(playerName);
      places[GetNumberOfPlayers()] = 0;
      purses[GetNumberOfPlayers()] = 0;
      inPenaltyBox[GetNumberOfPlayers()] = false;

      Console.WriteLine(playerName + " was added");
      Console.WriteLine("They are player number " + players.Count);
      return true;
    }

    public int GetNumberOfPlayers()
    {
      return players.Count;
    }

    public void OnDieRoll(int roll)
    {
      Console.WriteLine(players[currentPlayer] + " is the current player");
      Console.WriteLine("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer])
      {
        if (roll % 2 != 0)
        {
          isGettingOutOfPenaltyBox = true;

          Console.WriteLine(players[currentPlayer] + " is getting out of the penalty box");
          places[currentPlayer] = places[currentPlayer] + roll;
          if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

          Console.WriteLine(players[currentPlayer]
                  + "'s new location is "
                  + places[currentPlayer]);
          Console.WriteLine("The category is " + CurrentCategory());
          AskQuestion();
        }
        else
        {
          Console.WriteLine(players[currentPlayer] + " is not getting out of the penalty box");
          isGettingOutOfPenaltyBox = false;
        }

      }
      else
      {

        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

        Console.WriteLine(players[currentPlayer]
                + "'s new location is "
                + places[currentPlayer]);
        Console.WriteLine("The category is " + CurrentCategory());
        AskQuestion();
      }

    }

    private void AskQuestion()
    {
      if (CurrentCategory() == "Pop")
      {
        Console.WriteLine(popQuestions.First());
        popQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Science")
      {
        Console.WriteLine(scienceQuestions.First());
        scienceQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Sports")
      {
        Console.WriteLine(sportsQuestions.First());
        sportsQuestions.RemoveFirst();
      }
      if (CurrentCategory() == "Rock")
      {
        Console.WriteLine(rockQuestions.First());
        rockQuestions.RemoveFirst();
      }
    }


    private String CurrentCategory()
    {
      if (places[currentPlayer] == 0) return "Pop";
      if (places[currentPlayer] == 4) return "Pop";
      if (places[currentPlayer] == 8) return "Pop";
      if (places[currentPlayer] == 1) return "Science";
      if (places[currentPlayer] == 5) return "Science";
      if (places[currentPlayer] == 9) return "Science";
      if (places[currentPlayer] == 2) return "Sports";
      if (places[currentPlayer] == 6) return "Sports";
      if (places[currentPlayer] == 10) return "Sports";
      return "Rock";
    }

    public bool OnCorrectAnswer()
    {
      if (inPenaltyBox[currentPlayer])
      {
        if (isGettingOutOfPenaltyBox)
        {
          Console.WriteLine("Answer was correct!!!!");
          purses[currentPlayer]++;
          Console.WriteLine(players[currentPlayer]
                  + " now has "
                  + purses[currentPlayer]
                  + " Gold Coins.");

          bool winner = HasPlayerWon();
          currentPlayer++;
          if (currentPlayer == players.Count) currentPlayer = 0;

          return winner;
        }
        else
        {
          currentPlayer++;
          if (currentPlayer == players.Count) currentPlayer = 0;
          return true;
        }



      }
      else
      {

        Console.WriteLine("Answer was corrent!!!!");
        purses[currentPlayer]++;
        Console.WriteLine(players[currentPlayer]
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");

        bool winner = HasPlayerWon();
        currentPlayer++;
        if (currentPlayer == players.Count) currentPlayer = 0;

        return winner;
      }
    }

    public bool OnIncorrectAnswer()
    {
      Console.WriteLine("Question was incorrectly answered");
      Console.WriteLine(players[currentPlayer] + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      currentPlayer++;
      if (currentPlayer == players.Count) currentPlayer = 0;
      return true;
    }


    private bool HasPlayerWon()
    {
      return !(purses[currentPlayer] == 6);
    }
  }
}