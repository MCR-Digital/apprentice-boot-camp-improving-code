namespace TriviaGame
{
    public class Player
    {
        public string name { get; set; }
        public int purse { get; set; }
        public int location { get; set; }
        public bool isInPenaltyBox { get; set; }
        public bool canAnswerQuestion { get; set; }

        public Player(string name)
        {
            this.name = name;
            purse = 0;
            location = 0;
            isInPenaltyBox = false;
        }
    }
}
