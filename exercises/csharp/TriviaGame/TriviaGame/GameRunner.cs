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
            Game aGame = new Game();

            //adds 3 players to the game
            aGame.Add("Chet");
            aGame.Add("Pat");
            aGame.Add("Sue");

            //creates a random number
            Random rand = new Random(Int32.Parse(args[0]));

            do
            {
                //rolls using a random number below 5?
                aGame.Roll(rand.Next(5) + 1);

                //generate a random number, if it is 7, the answer is incorrect
                if (rand.Next(9) == 7)
                {
                    notAWinner = aGame.WrongAnswer();
                }
                else
                {
                    notAWinner = aGame.WasCorrectlyAnswered();
                }

                //do this until there is a winner

            } while (notAWinner);

        }
    }
}
