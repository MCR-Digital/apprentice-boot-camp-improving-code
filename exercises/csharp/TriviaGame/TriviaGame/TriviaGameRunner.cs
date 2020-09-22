using System;

namespace TriviaGame
{
    public class TriviaGameRunner
    {

        private static bool notAWinner;

        public static void Main(String[] args)
        {
            TriviaGame aGame = new TriviaGame();

            aGame.AddPlayer("Chet");
            aGame.AddPlayer("Pat");
            aGame.AddPlayer("Sue");

            Random playerRoll = new Random(Int32.Parse(args[0]));

            do
            {

                aGame.RollDice(playerRoll.Next(5) + 1);

                if (playerRoll.Next(9) == 7)
                {
                    notAWinner = aGame.WrongAnswer();
                }
                else
                {
                    notAWinner = aGame.CanGameContinueAfterCorrectAnswer();
                }



            } while (notAWinner);

        }
    }
}
