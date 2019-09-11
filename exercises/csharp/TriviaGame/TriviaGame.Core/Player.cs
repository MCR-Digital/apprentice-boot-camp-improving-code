using System;

namespace TriviaGame.Core
{
  public class BoardPosition
  {
    private static readonly int NUMBER_OF_BOARD_SQUARES = 12;
    private readonly string[] _categories = { "Pop", "Science", "Sports", "Rock" };
    public int Position { get; private set; }
    public string Category => _categories[Position];
    public bool InPenaltyBox { get; private set; }

    public void Move(int roll)
    {
      if (!InPenaltyBox)
      {
        Position = (Position + roll) % NUMBER_OF_BOARD_SQUARES;
      }

      InPenaltyBox = InPenaltyBox && (roll % 2 != 0);
    }
  }

  public class Player
  {
    public readonly string Name;
    public int GoldCoins { get; private set; }
    public int BoardPosition { get; private set; }
    public BoardPosition Position { get; private set; }
    public bool IsInPenaltyBox { get; set; }
    private static readonly int NUMBER_OF_BOARD_SQUARES = 12;

    public Player(string name)
    {
      Name = name;
      Position = new BoardPosition();
    }

    public void TakeTurn()
    {
      Position.Move(5);

    }

    public void AddCoin()
    {
      GoldCoins++;
    }

    public void Move(int places)
    {
      Position.Move(places);
      BoardPosition = (BoardPosition + places) % NUMBER_OF_BOARD_SQUARES;
    }

    public void AnswerQuestion(Question question)
    {

    }

    public void PrintScore()
    {
      Console.WriteLine($"{Name} now has {GoldCoins} Gold Coins.");
    }

    public void PrintBoardPosition()
    {
      Console.WriteLine($"{Name}'s new location is {BoardPosition}");
    }
  }
}