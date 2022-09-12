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

                game.ProcessPlayerTurn(randomNumber.Next(5) + 1);

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
