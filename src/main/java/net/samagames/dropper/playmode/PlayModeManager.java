package net.samagames.dropper.playmode;

import org.bukkit.entity.Player;
import net.samagames.dropper.DropperGame;
import net.samagames.dropper.level.AbstractLevel;

public class PlayModeManager {
	
	private DropperGame game;
	public PlayModeManager(DropperGame game){
		this.game = game;
	}
	
	public void newGame(Player player, PlayMode mode){
		if(mode == PlayMode.CHALLENGE){
			this.game.getDPFromPlayer(player).updatePlayMode(PlayMode.CHALLENGE);
	    	this.game.getLevelManager().joinLevel(player, this.game.getLevelManager().LEVEL_1);
	    	player.getInventory().clear();
	    	player.getInventory().setItem(1, this.game.PLAYMODE_DEFI_LEAVE);
	    	
		} else if (mode == PlayMode.ENTERTAINMENT){
			this.game.getDPFromPlayer(player).updatePlayMode(PlayMode.ENTERTAINMENT);
			player.teleport(this.game.getLevelHub());
	    	player.getInventory().clear();
	    	player.getInventory().setItem(1, this.game.BACK_LEVEL_HUB);
		}
	}
	
	public void setChallengeLost(Player player){
		this.game.getDPFromPlayer(player).updatePlayMode(PlayMode.UNSET);
    	player.getInventory().clear();
    	player.getInventory().setItem(2, this.game.PLAYMODE_CHALLENGE);
        player.getInventory().setItem(4, this.game.PLAYMODE_ENTERTAINMENT);
        player.teleport(this.game.getMapHub());
	}
	
	public void processLevelSuccess(Player player, AbstractLevel level){
		this.game.getLevelManager().leaveLevel(player, false);
		this.game.getLevelManager().joinLevel(player, this.game.getLevelManager().getLevelLogic().get(level));
	}

}
