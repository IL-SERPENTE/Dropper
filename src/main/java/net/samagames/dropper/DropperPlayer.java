package net.samagames.dropper;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.level.DropperLevel;

public class DropperPlayer extends GamePlayer {
	
	private Player bukkitPlayer;
	private GameType gameType;
	private DropperLevel current;
	
	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
	}
	
	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
		this.gameType = GameType.UNSELECTED;
	}
	
	public GameType getGameType(){
		return this.gameType;
	}
	
	public DropperLevel getCurrentLevel(){
		return this.current;
	}
	
	public void updateCurrentLevel(DropperLevel newLevel){
		this.current = newLevel;
	}
	
	public void updatePlayerGameType(GameType newGameType){
		this.gameType = newGameType;
	}

}
