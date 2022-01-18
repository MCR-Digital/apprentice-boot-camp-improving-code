using System;

namespace TriviaGame
{
   public static class GameRunner
    {

        private static bool winner;

        public static void Main(String[] args)
        {
            Game aGame = new Game();
            
            aGame.Add("Chet");
            aGame.Add("Pat");
            aGame.Add("Sue");
            
            Random randomNumberGenerator = new Random(Int32.Parse(args[0]));

            do
            {
                aGame.Roll(randomNumberGenerator.Next(5) + 1);

                var isWrongAnswer = randomNumberGenerator.Next(9) == 7;

                winner = isWrongAnswer ? aGame.WasIncorrectlyAnswered() : aGame.WasCorrectlyAnswered();

            } while (!winner);

        }
    }
}
