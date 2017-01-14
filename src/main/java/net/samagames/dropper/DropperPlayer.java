package net.samagames.dropper;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.samagames.api.games.GamePlayer;

public class DropperPlayer extends GamePlayer {
	
	private Player bukkitPlayer;
	
	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
	}
	
	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
	}

}
