package net.samagames.dropper;

import net.samagames.dropper.level.LevelCooldown;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.level.DropperLevel;

public class DropperPlayer extends GamePlayer {

	/**
	 * This is the internal representation of a player in SamaGamesAPI.
	 * @author Vialonyx
	 */
	
	private Player bukkitPlayer;
	private GameType gameType;
	private DropperLevel current;
	private boolean hasActiveCooldown;
	private LevelCooldown activeCooldown;
	
	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
		this.hasActiveCooldown = false;
	}
	
	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
		this.gameType = GameType.UNSELECTED;
	}

	/**
	 * Get the current gametype of the player.
	 * @return The current gametype.
	 */
	
	public GameType getGameType(){
		return this.gameType;
	}

	/**
	 * Get the current level of the player.
	 * @return The current level.
	 */
	
	public DropperLevel getCurrentLevel(){
		return this.current;
	}

	/**
	 * Update the current level.
	 * @param newLevel The new level.
	 */
	
	public void updateCurrentLevel(DropperLevel newLevel){
		this.current = newLevel;
	}

	/**
	 * Update the player gametype.
	 * @param newGameType The new gametype.
	 */
	
	public void updatePlayerGameType(GameType newGameType){
		this.gameType = newGameType;
	}

	/**
	 * Get if player has active cooldown now.
	 * @return true if the player has active cooldown actually.
	 */

	public boolean hasActiveCooldown(){
		return this.hasActiveCooldown;
	}

	/**
	 * Assign new cooldown to the player.
	 * @param cooldown The cooldown.
	 */

	public void enableCooldown(LevelCooldown cooldown){
		this.activeCooldown = cooldown;
		this.hasActiveCooldown = true;
	}

	/**
	 * Reset the cooldown data.
	 */

	public void resetCooldownData(){
		this.hasActiveCooldown = false;
		this.activeCooldown = null;
	}

	/**
	 * Get the active cooldown.
	 * @return the active cooldown.
	 */

	public LevelCooldown getActiveCooldown(){
		return this.activeCooldown;
	}

}
