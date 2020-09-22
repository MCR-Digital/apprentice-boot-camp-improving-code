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

            aGame.AddPlayer("Chet");
            aGame.AddPlayer("Pat");
            aGame.AddPlayer("Sue");

            Random randomNumber = new Random(Int32.Parse(args[0]));

            do
            {
                aGame.RollDice(randomNumber.Next(5) + 1);

                notAWinner = (randomNumber.Next(9) == 7) ? aGame.WrongAnswer() : aGame.CorrectAnswer();

            } while (notAWinner);
        }
    }
}
