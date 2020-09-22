using System;

namespace TriviaGame
{
    public class GameRunner
    {

        private static bool doesGameHaveNoWinner;

        public static void Main(String[] args)
        {
            Game currentGame = new Game("Chet", "Pat", "Sue");

            Random dice = new Random(Int32.Parse(args[0]));

            int RollDice(int maxValue)
            {
                return dice.Next(maxValue);
            }

            do
            {
                currentGame.ProcessRoll(RollDice(5) + 1);

                if (RollDice(9) == 7)
                {
                    doesGameHaveNoWinner = currentGame.WrongAnswer();
                }
                else
                {
                    doesGameHaveNoWinner = currentGame.CorrectAnswer();
                }

            } while (doesGameHaveNoWinner);

        }
    }
}
