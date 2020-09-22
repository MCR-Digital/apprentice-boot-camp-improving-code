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

            CreateNewTriviaGame.AddNewPlayer("Chet");
            CreateNewTriviaGame.AddNewPlayer("Pat");
            CreateNewTriviaGame.AddNewPlayer("Sue");

            Random RandomNumber = new Random(Int32.Parse(args[0]));

            do
            {
                CreateNewTriviaGame.RollDice(RandomNumber.Next(5) + 1);

                if (RandomNumber.Next(9) == 7)
                {
                    notAWinner = CreateNewTriviaGame.WrongAnswer();
                }
                else
                {
                    notAWinner = CreateNewTriviaGame.WasCorrectlyAnswered();
                }
            } while (notAWinner);
        }
    }
}
