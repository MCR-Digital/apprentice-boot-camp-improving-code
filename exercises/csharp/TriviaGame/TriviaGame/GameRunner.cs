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
            Trivia_Game CreateNewTriviaGame = new Trivia_Game();

            CreateNewTriviaGame.player.AddNewPlayer("Chet");
            CreateNewTriviaGame.player.AddNewPlayer("Pat");
            CreateNewTriviaGame.player.AddNewPlayer("Sue");

            Random RandomNumber = new Random(Int32.Parse(args[0]));

            do
            {
                CreateNewTriviaGame.RollDice(RandomNumber.Next(5) + 1);

                if (RandomNumber.Next(9) == 7)
                {
                    notAWinner = CreateNewTriviaGame.IsWrongAnswer();
                }
                else
                {
                    notAWinner = CreateNewTriviaGame.IsCorrectAnswer();
                }
            } while (notAWinner);

        }
    }
}
