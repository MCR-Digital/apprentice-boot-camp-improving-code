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

        private static bool doesGameHaveWinner;

        public static void Main(String[] args)
        {
            // 1. There should be a method in here to create the game
            // 2. Then a method to run the game

            Game createGame = new Game();

            createGame.AddPlayer("Chet");
            createGame.AddPlayer("Pat");
            createGame.AddPlayer("Sue");

            Random rand = new Random(Int32.Parse(args[0]));

            do
            {
                // turn the below to a method that generates a random dice number
                createGame.RollDice(rand.Next(5) + 1);

                if (rand.Next(9) == 7)
                {
                    doesGameHaveWinner = createGame.WasIncorrectlyAnswered();
                }
                else
                {
                    doesGameHaveWinner = createGame.WasCorrectlyAnswered();
                }



            } while (doesGameHaveWinner);

        }
    }
}
