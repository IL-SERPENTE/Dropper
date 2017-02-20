package net.samagames.dropper;

import java.util.*;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.dropper.level.LevelCooldown;
import net.samagames.tools.ProximityUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.tools.LocationUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Dropper extends Game<DropperPlayer> {
	
	private DropperMain instance;
	private Map<Integer, ItemStack> gameItems;
	private List<DropperLevel> registeredLevels;
	
	 public Dropper(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, DropperMain instance) {
		 super(gameCodeName, gameName, gameDescription, gamePlayerClass);
		 
		 this.instance = instance;

		 // Registering items
		 this.gameItems = new HashMap<>();
		 this.gameItems.put(0, this.stackBuilder("Entrainement", null, Material.DIRT, (byte) 0));
		 this.gameItems.put(1, this.stackBuilder("Compétition", null, Material.GRASS, (byte) 0));
		 this.gameItems.put(2, this.stackBuilder("Quitter le mode de jeu actuel", null, Material.BONE, (byte) 0));
		 this.gameItems.put(3, this.stackBuilder("Quitter le niveau actuel", null, Material.BIRCH_DOOR_ITEM, (byte) 0));
		 this.gameItems.put(4, this.stackBuilder("Sélectionner un niveau", null, Material.ITEM_FRAME, (byte) 0));

		 // Registering levels
		 this.registeredLevels = new ArrayList<>();
		 this.registeredLevels.add(new DropperLevel(1, "Rainbow", "n/a"));
		 this.registeredLevels.add(new DropperLevel(2, "Isengard", "n/a"));
		 this.registeredLevels.add(new DropperLevel(3, "Neo", "n/a"));
		 this.registeredLevels.add(new DropperLevel(4, "Symbols", "n/a"));
		 this.registeredLevels.add(new DropperLevel(5, "The Three", "n/a"));
		 this.registeredLevels.add(new DropperLevel(6, "Embryo", "n/a"));
		 this.registeredLevels.add(new DropperLevel(7, "Brain", "n/a"));
		 this.registeredLevels.add(new DropperLevel(8, "Dimension Jumper", "n/a"));

		 // Start proximity tasks
		 BukkitScheduler bukkitScheduler = this.instance.getServer().getScheduler();
		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(0).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(1).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(2).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(3).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(4).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(5).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(6).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

		 ProximityUtils.onNearbyOf(this.instance, this.getDropperLevel(7).getSecretEnd(), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
		() -> this.usualLevelLeave(player)));

	 }
	 
	 @Override 
	 public void handleLogin(Player player){
		 super.handleLogin(player);
		 player.teleport(this.getMapHub());
		 player.getInventory().clear();
		 player.getInventory().setItem(0, this.getGameItem(0));
		 player.getInventory().setItem(1, this.getGameItem(1));
		 player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2));
	 }

	 public ItemStack getGameItem(int ref){
	 	return this.gameItems.get(ref);
	 }

	 public DropperMain getInstance(){
	 	return this.instance;
	 }

	 public List<DropperLevel> getRegisteredLevels(){
	 	return this.registeredLevels;
	 }

	public Location getMapHub(){
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("map-hub").getAsString());
	}

	 public DropperLevel getDropperLevel(int ref){
	 	return this.registeredLevels.get(ref);
	 }
	 
	 public void usualGameTypeUpdate(Player player, GameType newGameType){
		 this.getPlayer(player.getUniqueId()).updatePlayerGameType(newGameType);

		 player.getInventory().clear();

		 if(! newGameType.equals(GameType.UNSELECTED)){
			 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
					 .writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " joues désormais en mode " + this.getGameTypeFormatColor(newGameType),true);
		 }

		 if(newGameType.equals(GameType.FREE)){
			 player.getInventory().addItem(this.getGameItem(2));
			 player.getInventory().addItem(this.getGameItem(4));

		 } else if(newGameType.equals(GameType.COMPETITION)){
			 player.getInventory().clear();
			 player.getInventory().setItem(0,this.getGameItem(2));
			 usualLevelJoin(player, 0);

		 }

	 }

	 public void usualLevelJoin(Player player, int levelRef) {
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		 DropperLevel level = this.getDropperLevel(levelRef);

		 level.buildPlatform();
         player.getInventory().remove(this.getGameItem(4));
         player.teleport(level.getPlayLocation());
         new LevelCooldown(this, player, level).runTaskTimer(this.instance, 0L, 20L);
	 }

	 public void usualLevelLeave(Player player){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		 DropperLevel level = dpPlayer.getCurrentLevel();

		 LevelQuitEvent levelQuitEvent = new LevelQuitEvent(player, level);
		 this.getInstance().getServer().getPluginManager().callEvent(levelQuitEvent);

		 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
		.writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a terminé le niveau " + ChatColor.RED + ChatColor.BOLD + "#" + level.getID() +  ChatColor.RED + "(" + ChatColor.ITALIC + level.getName() + ")" + ChatColor.RESET + " en mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()),true);

	 }

	 public String getGameTypeFormatColor(GameType type){
	 	if(type.equals(GameType.UNSELECTED)){
	 		return ChatColor.GRAY + "Non sélectionné";
		} else if(type.equals(GameType.FREE)){
	 		return ChatColor.GREEN + "Entrainement";
		} else if(type.equals(GameType.COMPETITION)){
			return ChatColor.RED + "Compétition";
		}
		return "";
	 }
	 
	 public void usualGameLeave(Player player){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());

		 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
		.writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a quitté la partie en mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()),true);
		 
		 player.teleport(this.getMapHub());
		 dpPlayer.updatePlayerGameType(GameType.UNSELECTED);
		 dpPlayer.updateCurrentLevel(null);
		 player.getInventory().clear();
		 player.getInventory().setItem(0, this.getGameItem(0));
		 player.getInventory().setItem(1, this.getGameItem(1));
		 
	 }

	 public DropperLevel getNextFromCurrent(DropperLevel current){
	 	int id = current.getID();
	 	if(id < this.registeredLevels.size() + 1){
			id++;
		}
		 return this.registeredLevels.get(id);
	 }
	 
	 private ItemStack stackBuilder(String name, List<String> lore, Material material, byte data){ 
	        org.bukkit.inventory.ItemStack tmpStack = new ItemStack(material, 1, data); 
	        ItemMeta tmpStackMeta = tmpStack.getItemMeta(); 
	        tmpStackMeta.setDisplayName(name); 
	        tmpStackMeta.setLore(lore); 
	        tmpStack.setItemMeta(tmpStackMeta); 
	 
	        return tmpStack; 
	    }
	
}
