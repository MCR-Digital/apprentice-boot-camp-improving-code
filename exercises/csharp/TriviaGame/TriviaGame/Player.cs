using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public class Player
    {
        public Player(string name)
        {
            Name = name;
        }

        public string Name { get; }

        public int Place { get; set; }

        public int Coins { get; set; }
    }
}
