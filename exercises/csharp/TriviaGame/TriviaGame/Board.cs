namespace TriviaGame
{
    public class Board
    {
        // TODO: List<Player>?
        private readonly bool[] PenaltyBox = new bool[Constants.MaxPlayers];

        public bool IsPlayerInPenaltyBox(int playerNumber)
        {
            return PenaltyBox[playerNumber];
        }

        // TODO: initialise in better way
        public void InitialisePlayerStateInPenaltyBox(int playerNumber)
        {
            UpdatePlayerPenaltyBoxState(playerNumber, false);
        }

        public void AddPlayerToPenaltyBox(int playerNumber)
        {
            UpdatePlayerPenaltyBoxState(playerNumber, true);
        }

        private void UpdatePlayerPenaltyBoxState(int playerNumber, bool playerState)
        {
            PenaltyBox[playerNumber] = playerState;
        }
    }
}
