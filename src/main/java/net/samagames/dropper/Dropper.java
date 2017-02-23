package net.samagames.dropper;

import java.util.*;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.dropper.level.EffectManager;
import net.samagames.dropper.level.LevelCooldown;
import net.samagames.tools.ProximityUtils;
import net.samagames.tools.Titles;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.tools.LocationUtils;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getWorlds;

public class Dropper extends Game<DropperPlayer> {

	/**
	 * This is the game class, the game was managed globally here.
	 * @author Vialonyx
	 */
	
	private DropperMain instance;
	private EffectManager effectManager;
	private List<DropperLevel> registeredLevels;

	// Creating game items.
	public static final ItemStack ITEM_MODE_FREE = stackBuilder(ChatColor.GRAY + "Mode " + ChatColor.GREEN + "Entrainement", Arrays.asList(ChatColor.GREEN + "Entrainez vous autant que vous le voulez sur chaque niveau !"), Material.BANNER, (byte) 2);
	public static final ItemStack ITEM_MODE_COMPETITION = stackBuilder(ChatColor.GRAY + "Mode " + ChatColor.RED + "Compétition", Arrays.asList(ChatColor.RED + "Défiez vos amis et améliorez votre propre record !"), Material.BANNER, (byte) 1);
	public static final ItemStack ITEM_QUIT_GAME = stackBuilder(ChatColor.WHITE + "Quitter le mode de jeu", null, Material.BIRCH_DOOR_ITEM, (byte) 0);
	public static final ItemStack ITEM_QUIT_LEVEL = stackBuilder(ChatColor.RED + "Quitter le niveau", null, Material.BARRIER, (byte) 0);;
	public static final ItemStack ITEM_SELECTGUI = stackBuilder(ChatColor.WHITE + "Sélectionner un niveau", null, Material.BOOK, (byte) 0);
	
	 public Dropper(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, DropperMain instance) {
		 super(gameCodeName, gameName, gameDescription, gamePlayerClass);
		 
		 this.instance = instance;

		 // Registering levels
		 this.registeredLevels = new ArrayList<>();
		 this.registeredLevels.add(new DropperLevel(1, "Rainbow", "Plongez dans le monde des couleurs !"));
		 this.registeredLevels.add(new DropperLevel(2, "Isengard", "En pleine terre du milieu ..."));
		 this.registeredLevels.add(new DropperLevel(3, "Neo", "Un nouvel univers informatique"));
		 this.registeredLevels.add(new DropperLevel(4, "Symbols", "Arriverez vous à déchiffrer les symboles ?"));
		 this.registeredLevels.add(new DropperLevel(5, "The Three", "Panique en plein pique-nique !"));
		 this.registeredLevels.add(new DropperLevel(6, "Embryo", "Au commencement d'une vie"));
		 this.registeredLevels.add(new DropperLevel(7, "Brain", "Dans votre masse cérébrale ..."));
		 this.registeredLevels.add(new DropperLevel(8, "Dimension Jumper", "Hé non ! rien à voir avec le Dimensions !"));
		 this.registeredLevels.add(new DropperLevel(9, "BeetleJuice", "Ne vous perdez pas dans l'illusion !"));
		 this.registeredLevels.add(new DropperLevel(10, "Web", "L'arignée est en train de tisser sa toile ..."));
		 this.registeredLevels.add(new DropperLevel(11, "Armor", "L'acordéon de la chute vous attend "));
		 this.registeredLevels.add(new DropperLevel(12, "Dracula's Bedroom", "Protégez vous bien des morsures !"));
		 this.registeredLevels.add(new DropperLevel(13, "DNA", "Votre code génétique est-il si complexe ?"));
		 this.registeredLevels.add(new DropperLevel(14, "Minecraft is huge", "Ce monde cubique paraît si petit ..."));
		 this.registeredLevels.add(new DropperLevel(15, "Hardware", "Votre ordinateur vous cache des choses !"));
		 this.registeredLevels.add(new DropperLevel(16, "Moria", "Sauve qui peut !"));

		 this.effectManager = new EffectManager(this);

		 // Start proximity tasks
		 BukkitScheduler bukkitScheduler = this.instance.getServer().getScheduler();		 
		 for(DropperLevel level : this.getRegisteredLevels()){
			 ProximityUtils.onNearbyOf(this.instance, level.getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
			() -> this.usualLevelLeave(player, false)));
		 }

		 // Special proximity task for level 14
		 ArmorStand specialLvl14 = this.armorStandBuilder(new Location(getWorlds().get(0), 1444, 16, 1327), getWorlds().get(0));
		 ProximityUtils.onNearbyOf(this.instance, specialLvl14, 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
				 () -> player.teleport(new Location(getWorlds().get(0), 510, 177, 1531))));

	 }

	 /*
	  * handlelogin was called by SamaGamesAPI when a player is joining the game.
	  */
	 
	 @Override 
	 public void handleLogin(Player player){
		 super.handleLogin(player);
		 player.teleport(this.getMapHub());
		 player.setBedSpawnLocation(this.getMapHub());
		 player.getInventory().clear();
		 player.getInventory().setItem(3, this.ITEM_MODE_FREE);
		 player.getInventory().setItem(5, this.ITEM_MODE_COMPETITION);
		 this.effectManager.restoreDefaultEffects(player);
	 }

	public DropperMain getInstance(){
	 	return this.instance;
	 }

	 public EffectManager getEffectManager(){
		return this.effectManager;
	 }

	/**
	 * Get all registered levels.
	 * @return a list of registered levels.
	 */

	public List<DropperLevel> getRegisteredLevels(){
	 	return this.registeredLevels;
	 }

	/**
	 * Get the location of the map hub via the Json file.
	 * @return the location of map hub.
	 */

	public Location getMapHub(){
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("map-hub").getAsString());
	}

	/**
	 * Get a dropper level by his id.
	 * @param ref The level ID.
	 * @return The DropperLevel from given id.
	 */

	 public DropperLevel getDropperLevel(int ref){
	 	return this.registeredLevels.get(ref);
	 }

	/**
	 * Update the gametype of the player.
	 * @param player The player.
	 * @param newGameType The new gametype.
	 */
	 
	 public void usualGameTypeUpdate(Player player, GameType newGameType){

		 this.getPlayer(player.getUniqueId()).updatePlayerGameType(newGameType);
		 player.getInventory().clear();

		 if(! newGameType.equals(GameType.UNSELECTED)){
			 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
					 .writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " joues désormais en mode " + this.getGameTypeFormatColor(newGameType),true);
		 }

		 if(newGameType.equals(GameType.FREE)){
			 player.getInventory().setItem(3, this.ITEM_SELECTGUI);
			 player.getInventory().setItem(5, this.ITEM_QUIT_GAME);

		 } else if(newGameType.equals(GameType.COMPETITION)){
			 player.getInventory().clear();
			 player.getInventory().setItem(4,this.ITEM_QUIT_GAME);
			 this.usualLevelJoin(player, 0);
		 }

	 }

	/**
	 * This is the entry point of the level-joining process.
	 * @param player The player.
	 * @param levelRef The level ref.
	 */

	 public void usualLevelJoin(Player player, int levelRef) {
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		 DropperLevel level = this.getDropperLevel(levelRef);

         player.getInventory().clear();
         player.getInventory().setItem(4, this.ITEM_QUIT_GAME);
         dpPlayer.updateCurrentLevel(level);

		 Titles.sendTitle(player, 30, 70, 30, "" + ChatColor.YELLOW + ChatColor.BOLD + level.getName(), "" + ChatColor.RED + ChatColor.ITALIC + level.getDescription());

         if(! dpPlayer.hasActiveCooldown()){
			 new LevelCooldown(this, player, level).runTaskTimer(this.instance, 0L, 20L);
		 }

	 }

	/**
	 * This is the entry point of the level-leaving process.
	 * @param player The player.
	 * @param cancelled True if if the level was leaved during the cooldown.
	 */

	public void usualLevelLeave(Player player, boolean cancelled){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		 DropperLevel level = dpPlayer.getCurrentLevel();

		 if(dpPlayer.hasActiveCooldown()){
		 	dpPlayer.getActiveCooldown().cancel();
		 	dpPlayer.resetCooldownData();
		 	ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.DARK_RED + "Démarrage du niveau annulé !");
		 }

		 LevelQuitEvent levelQuitEvent = new LevelQuitEvent(player, level);
		 this.getInstance().getServer().getPluginManager().callEvent(levelQuitEvent);

		 if(cancelled){
			 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
					 .writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a quitté le niveau " + ChatColor.RED + ChatColor.BOLD + "#" + level.getID() +  ChatColor.RED + "(" + ChatColor.ITALIC + level.getName() + ")" + ChatColor.RESET + " en mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()),true);
		 } else {
			 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
					 .writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a terminé le niveau " + ChatColor.RED + ChatColor.BOLD + "#" + level.getID() +  ChatColor.RED + "(" + ChatColor.ITALIC + level.getName() + ")" + ChatColor.RESET + " en mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()),true);
		 }

	 }

	/**
	 * Format the gametype.
	 * @param type The type
	 * @return The gametype colored.
	 */

	 public String getGameTypeFormatColor(GameType type){
	 	if(type.equals(GameType.UNSELECTED)){
	 		return ChatColor.GRAY + "Non sélectionné";
		} else if(type.equals(GameType.FREE)){
	 		return "" + ChatColor.GREEN + ChatColor.BOLD + "Entrainement";
		} else if(type.equals(GameType.COMPETITION)){
			return "" + ChatColor.RED + ChatColor.BOLD + "Compétition";
		}
		return "";
	 }

	/**
	 * This is the entry point of the level-leaving process.
	 * @param player The player.
	 */

	public void usualGameLeave(Player player){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());

		 if(dpPlayer.hasActiveCooldown()){
			 dpPlayer.getActiveCooldown().cancel();
			 dpPlayer.resetCooldownData();
			 ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.RED + "Démarrage du niveau annulé !");
		 }

		 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
		.writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a quitté le mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()),true);

		 if(dpPlayer.getCurrentLevel() != null){
			 player.teleport(this.getMapHub());
		 }

		 dpPlayer.updatePlayerGameType(GameType.UNSELECTED);
		 dpPlayer.updateCurrentLevel(null);
		 player.getInventory().clear();
		 player.getInventory().setItem(3, this.ITEM_MODE_FREE);
		 player.getInventory().setItem(5, this.ITEM_MODE_COMPETITION);
		 
	 }

	/**
	 * Get the next level from current (as competition gametype).
	 * @param current The current level.
	 * @return The next level.
	 */

	public DropperLevel getNextFromCurrent(DropperLevel current){
	 	return this.getDropperLevel(current.getID()-1);
	 }

	/**
	 * Build an ItemStack simply.
	 * @param name Item name.
	 * @param lore Item lore.
	 * @param material The material of item.
	 * @param data Item data.
	 * @return The builded ItemStack.
	 */
	 
	 private static ItemStack stackBuilder(String name, List<String> lore, Material material, byte data){
	        org.bukkit.inventory.ItemStack tmpStack = new ItemStack(material, 1, data); 
	        ItemMeta tmpStackMeta = tmpStack.getItemMeta(); 
	        tmpStackMeta.setDisplayName(name); 
	        tmpStackMeta.setLore(lore); 
	        tmpStack.setItemMeta(tmpStackMeta); 
	 
	        return tmpStack; 
	 }

	public static ArmorStand armorStandBuilder(Location spawn, World world){
		ArmorStand as = (ArmorStand) world.spawnEntity(spawn, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setGravity(false);
		return as;
	}
	
}
