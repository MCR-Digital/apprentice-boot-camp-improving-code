namespace TriviaGame
{
    internal class Player
    {
        public Player(string playerName)
        {
            Name = playerName;
        }

        public int Coins { get; set; } = 0;
        public int Index { get; set; }
        public string Name { get; set; }
    }
}