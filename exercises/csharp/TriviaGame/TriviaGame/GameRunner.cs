using System;

namespace TriviaGame
{
    public static class GameRunner
    {
        private static bool noWinner;

        public static void Main(string[] args)
        {
            Game game = new Game();

            game.AddPlayer("Chet");
            game.AddPlayer("Pat");
            game.AddPlayer("Sue");

            Random randomNumberGenerator = new Random(int.Parse(args[0]));

            do
            {
                game.RollDice(randomNumberGenerator.Next(5) + 1);

                if (randomNumberGenerator.Next(9) == 7)
                {
                    noWinner = game.WrongAnswer();
                }
                else
                {
                    noWinner = game.WasCorrectlyAnswered();
                }
            }
            while (noWinner);
        }
    }
}
