package net.samagames.dropper.playmode;

import org.bukkit.entity.Player;
import net.samagames.dropper.DropperGame;
import net.samagames.dropper.level.AbstractLevel;

public class PlayModeManager {
	
	private DropperGame game;
	public PlayModeManager(DropperGame game){
		this.game = game;
	}
	
	// -- CHALLENGE -- 
	
	public void newGameChallenge(Player player){
		this.game.getRegisteredGamePlayers().get(player.getUniqueId()).updatePlayMode(PlayMode.CHALLENGE);
    	this.game.getLevelManager().joinLevel(player, this.game.getLevelManager().LEVEL_1);
    	player.getInventory().clear();
    	player.getInventory().setItem(1, this.game.PLAYMODE_DEFI_LEAVE);
	}
	
	public void setChallengeLost(Player player){
		this.game.getRegisteredGamePlayers().get(player.getUniqueId()).updatePlayMode(PlayMode.UNSET);
    	player.getInventory().clear();
    	player.getInventory().setItem(2, this.game.PLAYMODE_CHALLENGE);
        player.getInventory().setItem(4, this.game.PLAYMODE_ENTERTAINMENT);
        player.teleport(this.game.getMapHub());
	}
	
	public void processLevelSuccess(Player player, AbstractLevel level){
		this.game.getLevelManager().leaveLevel(player, false);
		this.game.getLevelManager().joinLevel(player, this.game.getLevelManager().getLevelLogic().get(level));
	}
	
	// -- ENTERTAINMENT -- 
	
	public void newGameEntertainment(Player player){
		this.game.getRegisteredGamePlayers().get(player.getUniqueId()).updatePlayMode(PlayMode.ENTERTAINMENT);
		player.teleport(this.game.getLevelHub());
    	player.getInventory().clear();
    	player.getInventory().setItem(1, this.game.BACK_LEVEL_HUB);
	}

}
