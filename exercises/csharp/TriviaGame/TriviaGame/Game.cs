namespace TriviaGame
{
    public class Game
    {
        private const int COINS_TO_WIN = 6;

        private readonly PlayerTracker _playerTracker;
        private readonly Board _board;

        public Game()
        {
            _board = new Board();
            _playerTracker = new PlayerTracker();
        }

        public void AddPlayer(Player player)
        {
            _playerTracker.Add(player);
            GameWriter.WritePlayerAdded(player, _playerTracker.Players.Count);
        }

        public Player CurrentPlayer
        {
            get
            {
                return _playerTracker.CurrentPlayer;
            }
        }

        public void RollDice(Dice dice)
        {
            CurrentPlayer.RollDice(dice);

            GameWriter.WriteCurrentPlayerRoll(_playerTracker, CurrentPlayer.LastRoll);

            if (CurrentPlayer.IsInPenaltyBox)
            {
                if (!CurrentPlayer.CanMove)
                {
                    GameWriter.WritePlayerNotLeavingPenaltyBox(CurrentPlayer);
                    return;
                }

                GameWriter.WritePlayerLeavingPenaltyBox(CurrentPlayer);
            }

            _board.MovePlayer(CurrentPlayer, CurrentPlayer.LastRoll);

            AskQuestion();
        }

        public void AskQuestion()
        {
            var question = _board.GetQuestionForPlayer(CurrentPlayer);

            GameWriter.WritePlayerNewLocation(CurrentPlayer);
            GameWriter.WriteCategory(question.Category);
            GameWriter.WriteQuestion(question);
        }

        public void AnswerQuestion(bool correct)
        {
            if (correct)
            {
                if (!CurrentPlayer.CanMove) return;

                CurrentPlayer.GiveCoin();
                GameWriter.WriteAnswerWasCorrect();
                GameWriter.WriteNewCoinAmount(CurrentPlayer);
            }
            else
            {
                CurrentPlayer.IsInPenaltyBox = true;
                GameWriter.WriteAnswerWasIncorrect(CurrentPlayer);
            }
        }

        public bool HasCurrentPlayerWon()
        {
            return CurrentPlayer.Coins == COINS_TO_WIN;
        }

        public void MoveToNextPlayer()
        {
            _playerTracker.MoveToNextPlayer();
        }
    }
}