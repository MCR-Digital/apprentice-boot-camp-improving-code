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
            Game round = new Game();

            round.Add("Chet");
            round.Add("Pat");
            round.Add("Sue");

            Random rand = new Random(Int32.Parse(args[0]));

            do
            {

                round.Roll(rand.Next(5) + 1);

                if (rand.Next(9) == 7)
                {
                    notAWinner = round.WrongAnswer();
                }
                else
                {
                    notAWinner = round.WasCorrectlyAnswered();
                }



            } while (notAWinner);

        }
    }
}
