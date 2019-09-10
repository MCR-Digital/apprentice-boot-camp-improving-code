using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TriviaGame
{
   public class GameRunner
    {
        private static bool notAWinner;

        public static void Main(String[] args)
        {
            Game currentGame = new Game();

            currentGame.AddPlayer("Chet");
            currentGame.AddPlayer("Pat");
            currentGame.AddPlayer("Sue");

            Random rand = new Random(Int32.Parse(args[0]));

            do
            {
                currentGame.Roll(rand.Next(5) + 1);

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
