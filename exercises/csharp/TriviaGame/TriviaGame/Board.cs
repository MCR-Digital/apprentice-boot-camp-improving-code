namespace TriviaGame
{
    public class Board
    {
        // TODO: List<Player>?
        private readonly bool[] penaltyBox = new bool[Constants.MaxPlayers];
        // TODO: Place class with category and place number?
        private readonly int[] places = new int[Constants.MaxPlayers];

        public bool IsPlayerInPenaltyBox(int playerNumber)
        {
            return penaltyBox[playerNumber];
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

        public void InitializePlayerAtStartingPosition(int playerNumber)
        {
            places[playerNumber] = 0;
        }

        private void UpdatePlayerPenaltyBoxState(int playerNumber, bool playerState)
        {
            penaltyBox[playerNumber] = playerState;
        }
    }
}
