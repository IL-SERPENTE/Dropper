package net.samagames.dropper;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.samagames.api.games.GamePlayer;

public class DropperPlayer extends GamePlayer {
	
	private Player bukkitPlayer;
	private GameType gameType;
	
	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
	}
	
	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
		this.gameType = GameType.UNSELECTED;
	}
	
	GameType getGameType(){
		return this.gameType;
	}
	
	void updatePlayerGameType(GameType newGameType){
		this.gameType = newGameType;
	}

}
