using System;

namespace TriviaGame
{
    internal class Player
    {
        public Player(string playerName)
        {
            Name = playerName;
            Console.WriteLine($"{playerName} was added");
        }

        public int Coins { get; set; } = 0;
        public int Index { get; set; }
        public string Name { get; set; }
        public int Position { get; set; }
        public bool InPenaltyBox { get; set; }
        public bool CanAnswerQuestion { get; set; }
    }
}