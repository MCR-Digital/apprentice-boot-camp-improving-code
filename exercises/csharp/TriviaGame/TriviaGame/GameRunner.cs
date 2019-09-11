using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
    public static class GameRunner
    {
        private static bool notAWinner;

        public static void Main(string[] args)
        {
            var currentGame = new Game();

            currentGame.AddPlayer("Chet");
            currentGame.AddPlayer("Pat");
            currentGame.AddPlayer("Sue");

            var rand = new Random(int.Parse(args[0]));

            do
            {
                currentGame.RollDice(rand.Next(5) + 1);

                if (rand.Next(9) == 7)
                {
                    notAWinner = currentGame.WrongAnswer();
                }
                else
                {
                    notAWinner = currentGame.CheckForWinner();
                }

            } while (notAWinner);
        }
    }
}
