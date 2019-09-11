using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Player
    {
        public string Name { get; }
        public bool SkipTurn { get; set; } = false;
        public bool IsInPenaltyBox { get; set; } = false;
        public int CurrentSpace { get; set; }
        public int Coins;

        public Player(string name)
        {
            this.Name = name;
        }
    }
}
