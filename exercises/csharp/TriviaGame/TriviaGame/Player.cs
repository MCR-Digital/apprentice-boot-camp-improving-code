namespace TriviaGame
{
    public class Player
    {
        public string Name { get; set; }
        public int Place { get; set; } = 0;
        public int Purse { get; set; } = 0;
        public bool InPenaltyBox { get; set; } = false;
        public bool GettingOutOfPenaltyBox { get; set; } = false;

        public Player(string playerName)
        {
            Name = playerName;
        }

        public bool DidPlayerWin()
        {
            return !(Purse == 6);
        }
    }
}
