package net.samagames.dropper;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.level.DropperLevel;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DropperPlayer extends GamePlayer {

	/**
	 * This is the internal representation of a player in SamaGamesAPI.
	 * @author Vialonyx
	 */

	private Player bukkitPlayer;
	private GameType gameType;
	private DropperLevel current;
	private boolean isNeutralized, hasActiveCooldown, isOnTutorialLevel;
	private int competitionCategory;
	private DropperCooldown activeCooldown;
	private TimeCalculator currentCalculator;
	private AFKChecker afkChecker;

	public DropperPlayer(Player player){
		super(player);
		this.bukkitPlayer = player;
		this.isNeutralized = false;
		this.hasActiveCooldown = false;
		this.isOnTutorialLevel = false;
		this.competitionCategory = 1;
	}

	@Override
	public void handleLogin(boolean reconnect){
		this.bukkitPlayer.setGameMode(GameMode.ADVENTURE);
		this.gameType = GameType.UNSELECTED;
	}

	/**
	 * @param b True if the player is on the tutorial level.
	 */

	public void setPlayerInTutorial(boolean b){
		this.isOnTutorialLevel = b;
	}

	/**
	 * Set the player in tutorial level.
	 * @return true if the player is on the tutorial level.
	 */

	public boolean isOnTutorial(){
		return this.isOnTutorialLevel;
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

	/**
	 * Set the competition category.
	 * @param category The category.
	 */

	public void setCompetitionCategory(int category){
		this.competitionCategory = category;
	}

	/**
	 * Get the competition category.
	 * @return The competiton category.
	 */

	public int getCompetitionCategory(){
		return this.competitionCategory;
	}

}
