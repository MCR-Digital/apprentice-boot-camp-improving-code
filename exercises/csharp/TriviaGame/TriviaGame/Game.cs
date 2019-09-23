namespace TriviaGame
{
    public class Game
    {
        private const int COINS_TO_WIN = 6;

        private readonly PlayerTracker _playerTracker;
        private readonly Board _board;

        public Game(Board board)
        {
            _board = board;
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
            CurrentPlayer.LastRoll = dice.Roll();

            GameWriter.WriteCurrentPlayerRoll(_playerTracker, CurrentPlayer.LastRoll);

            if (CurrentPlayer.IsInPenaltyBox && CurrentPlayer.LastRoll.IsOdd())
            {
                GameWriter.WritePlayerLeavingPenaltyBox(CurrentPlayer);
            }
            else if (CurrentPlayer.IsInPenaltyBox)
            {
                GameWriter.WritePlayerNotLeavingPenaltyBox(CurrentPlayer);
                return;
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
            if (correct && CurrentPlayer.IsInPenaltyBox && !CurrentPlayer.LastRoll.IsOdd())
            {
                return;
            }

            if (correct)
            {
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