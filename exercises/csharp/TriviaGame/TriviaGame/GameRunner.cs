﻿using System;

namespace TriviaGame
{
   public class GameRunner
    {
        private static bool _notAWinner;
        private static Random _randomNumberGenerator;

        public static void Main(string[] args)
        {
            int seed = int.Parse(args[0]);
            _randomNumberGenerator = new Random(seed);

            var dice = new Dice(_randomNumberGenerator);
            Game aGame = new Game(new Board());

            aGame.AddPlayer(new Player("Chet"));
            aGame.AddPlayer(new Player("Pat"));
            aGame.AddPlayer(new Player("Sue"));

            do
            {
                aGame.RollDice(dice);

                if (_randomNumberGenerator.Next(9) == 7)
                {
                    _notAWinner = aGame.GiveCurrentPlayerWrongAnswer();
                }
                else
                {
                    _notAWinner = aGame.wasCorrectlyAnswered();
                }



            } while (_notAWinner);

        }
    }
}
