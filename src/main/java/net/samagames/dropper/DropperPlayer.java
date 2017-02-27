package net.samagames.dropper;

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
	private Boolean isNeutralized;
	private boolean hasActiveCooldown;
	private DropperCooldown activeCooldown;
	private TimeCalculator currentCalculator;
	private AFKChecker afkChecker;

	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
		this.isNeutralized = false;
		this.hasActiveCooldown = false;
	}

	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
		this.gameType = GameType.UNSELECTED;
	}

	/**
	 * @param isNeutralized Set true if player is neutralized.
	 */

	public void neutralizePlayer(boolean b){
		this.isNeutralized = b;
    }

	/**
	 * @return True if player isneutralized else false
	 */

	public boolean isNeutralized(){
		return this.isNeutralized;
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

	public void enableCooldown(DropperCooldown cooldown){
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

	public DropperCooldown getActiveCooldown(){
		return this.activeCooldown;
	}

	/**
	 * Assign new TimeCalculator to the player.
	 * @param calculator The calculator.
	 */

	public void defineNewCalculator(TimeCalculator calculator){
		this.currentCalculator = calculator;
	}

	/**
	 * Get the current Time Calculator.
	 * @return The current calculator.
	 */

	public TimeCalculator getCurrentCalculator(){
		return this.currentCalculator;
	}

	/**
	 * Assign new AFKChecker to the player.
	 * @param checker An instance of AFKChecker.
	 */

	public void defineNewAFKChecker(AFKChecker checker){
		this.afkChecker = checker;
	}

	/**
	 * Get the AFKChecker of the player.
	 * @return an instance of AFKChecker.
	 */

	public AFKChecker getAfkChecker(){
		return this.afkChecker;
	}

}
