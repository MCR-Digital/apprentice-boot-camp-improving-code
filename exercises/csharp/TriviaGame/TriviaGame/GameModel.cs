using System;
using System.Collections.Generic;
using System.Text;

namespace TriviaGame
{
    public class GameModel
    {
        public List<string> Players { get; set; }
        public int[] PlayerScores { get; set; }
        public int[] PlayerPlaces { get; set; }
        public bool[] InPenaltyBox { get; set; }
        public LinkedList<string> PopQuestions { get; set; }
        public LinkedList<string> ScienceQuestions { get; set; }
        public LinkedList<string> SportsQuestions { get; set; }
        public LinkedList<string> RockQuestions { get; set; }
        public int CurrentPlayer { get; set; }
        public bool IsGettingOutOfPenaltyBox { get; set; }
    }
}
