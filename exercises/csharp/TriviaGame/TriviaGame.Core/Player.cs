using System;

namespace TriviaGame.Core
{
  public class Player
  {
    public readonly string Name;
    public int GoldCoins { get; private set; }
    public int BoardPosition { get; private set; }
    public bool IsInPenaltyBox { get; set; }
    private static readonly int NUMBER_OF_BOARD_SQUARES = 12;

    public Player(string name)
    {
      Name = name;
    }

    public void AddCoin()
    {
      GoldCoins++;
    }

    public void Move(int places)
    {
      BoardPosition = (BoardPosition + places) % NUMBER_OF_BOARD_SQUARES;
    }

    public void AnswerQuestion(Question question)
    {

    }

    public void PrintScore()
    {
      Console.WriteLine(Name
                      + " now has "
                      + GoldCoins
                      + " Gold Coins.");
    }

    public void PrintBoardPosition()
    {
      Console.WriteLine(Name
                + "'s new location is "
                + BoardPosition);
    }
  }
}