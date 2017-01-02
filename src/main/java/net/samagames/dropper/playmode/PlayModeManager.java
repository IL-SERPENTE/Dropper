package net.samagames.dropper.playmode;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.samagames.dropper.DropperGame;
import net.samagames.dropper.level.AbstractLevel;

public class PlayModeManager {
	
	private DropperGame game;
	public PlayModeManager(DropperGame game){
		this.game = game;
	}
	
	public String syntaxPlayMode(PlayMode mode){
		
		if(mode == PlayMode.CHALLENGE){
			return ChatColor.YELLOW + "Défi";
		} else if (mode == PlayMode.ENTERTAINMENT){
			return ChatColor.GREEN + "Entrainement";
		} else if (mode == PlayMode.UNSET){
			return ChatColor.GRAY + "Non défini";
		}
		
		return "";
		
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
	
	public void newLost(Player player, PlayMode mode){
		if(mode == PlayMode.CHALLENGE){
			this.game.getDPFromPlayer(player).updatePlayMode(PlayMode.UNSET);
	    	player.getInventory().clear();
	    	player.getInventory().setItem(2, this.game.PLAYMODE_CHALLENGE);
	        player.getInventory().setItem(4, this.game.PLAYMODE_ENTERTAINMENT);
	        player.teleport(this.game.getMapHub());
	        
		} else if (mode == PlayMode.ENTERTAINMENT){
			player.teleport(this.game.getLevelHub());
		}
	}
	
	public void newWin(Player player, PlayMode mode){
		
		if(mode == PlayMode.CHALLENGE){
			this.game.getLevelManager().leaveLevel(player, false);
			this.game.getLevelManager().joinLevel(player, this.game.getLevelManager().getLevelLogic().get(this.game.getDPFromPlayer(player).getCurrentlyLevel()));
			
		} else if (mode == PlayMode.ENTERTAINMENT){
			player.teleport(this.game.getLevelHub());
		}
	}

}
