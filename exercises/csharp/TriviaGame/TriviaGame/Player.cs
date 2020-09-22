namespace TriviaGame
{
    public class Player
    {
        public string Name { get; set; }
        public int Place { get; set; }
        public int Purse { get; set; }
        public bool InPenaltyBox { get; set; } = false;
        public bool GettingOutOfPenaltyBox { get; set; }

        public Player(string playerName)
        {
            Name = playerName;
        }

        public bool DidPlayerWin()
        {
            return Purse != 6;
        }
    }
}
