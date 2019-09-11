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

        public int Roll()
        {
            return _random.Next(5) + 1;
        }
    }
}
