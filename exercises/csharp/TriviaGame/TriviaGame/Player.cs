namespace TriviaGame
{
    public class Player
    {
        public string playerName { get; set; }
        public int playerPurse { get; set; }
        public int playerLocation { get; set; }
        public bool isPlayerInPenaltyBox { get; set; }

        public Player(string name)
        {
            playerName = name;
            playerPurse = 0;
            playerLocation = 0;
            isPlayerInPenaltyBox = false;
        }
    }
}
