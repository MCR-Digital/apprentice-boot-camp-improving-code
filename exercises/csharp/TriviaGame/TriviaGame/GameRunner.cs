using System;

namespace TriviaGame
{
   public class GameRunner
    {
        private static Random _randomNumberGenerator;

        public static void Main(string[] args)
        {
            int seed = int.Parse(args[0]);
            _randomNumberGenerator = new Random(seed);

            var dice = new Dice(_randomNumberGenerator);
            Game game = new Game(new Board());

            game.AddPlayer(new Player("Chet"));
            game.AddPlayer(new Player("Pat"));
            game.AddPlayer(new Player("Sue"));

            bool isWinner;

            do
            {
                game.RollDice(dice);

                bool correctAnswer = _randomNumberGenerator.Next(9) != 7;

                game.AnswerQuestion(correctAnswer);

                isWinner = game.HasCurrentPlayerWon();

                game.MoveToNextPlayer();

            } while (!isWinner);
        }
    }
}
