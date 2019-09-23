using System;

namespace TriviaGame
{
    public class Dice
    {
        private readonly Random _random;

        public Dice(Random random)
        {
            _random = random;
        }

        public int LastRoll { get; private set; }

        public int Roll()
        {
            LastRoll = _random.Next(5) + 1;
            return LastRoll;
        }
    }
}
