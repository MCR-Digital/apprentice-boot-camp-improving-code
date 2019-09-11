package com.adaptionsoft.games.uglytrivia;

public class Player {

	private String playerName;
	private int playerCoins;
	private boolean isInPenaltyBox;

	public Player(String playerName){
		this.playerName = playerName;
	}

	public void addToCoins(){
		playerCoins++;
	}

	public int getPlayerCoins(){
		return playerCoins;
	}

	public boolean isPlayerInPenaltyBox(){
		return isInPenaltyBox;
	}

	@Override
	public String toString(){
		return playerName;
	}


}
