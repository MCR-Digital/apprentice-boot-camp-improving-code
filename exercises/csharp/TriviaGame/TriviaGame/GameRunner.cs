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
            Game createGame = new Game();

            createGame.AddPlayer("Chet");
            createGame.AddPlayer("Pat");
            createGame.AddPlayer("Sue");

            Random rand = new Random(Int32.Parse(args[0]));

            do
            {

                createGame.RollDice(rand.Next(5) + 1);

                if (rand.Next(9) == 7)
                {
                    notAWinner = createGame.WrongAnswer();
                }
                else
                {
                    notAWinner = createGame.WasCorrectlyAnswered();
                }



            } while (notAWinner);

        }
    }
}
