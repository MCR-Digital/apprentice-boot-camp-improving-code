using System;

namespace TriviaGame
{
   public class GameRunner
    {

        private static bool isNotAWinner;

        public static void Main(String[] args)
        {
            Game game = new Game();

            game.AddPlayer("Chet");
            game.AddPlayer("Pat");
            game.AddPlayer("Sue");

            Random randomNumber = new Random(Int32.Parse(args[0]));

            do
            {
                var dieSideValue = randomNumber.Next(5) + 1;

                game.Roll(dieSideValue);

                if (randomNumber.Next(9) == 7)
                {
                    isNotAWinner = game.IsWrongAnswer();
                }
                else
                {
                    isNotAWinner = game.IsCorrectAnswer();
                }
            } while (isNotAWinner);

        }
    }
}
