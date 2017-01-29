package net.samagames.dropper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.samagames.dropper.level.DropperLevel;
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
		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(0).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(0).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(1).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(1).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(2).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(2).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(3).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(3).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(4).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(4).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(5).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(5).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(6).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(6).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(7).getSecretStart(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameJoin(player)));

		 ProximityUtils.onNearbyOf(this.instance,
				 this.getDropperLevel(7).getSecretEnd(),
				 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
						 () -> this.usualGameLeave(player, false)));

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
	 
	 public DropperMain getMainInstance(){
		 return this.instance;
	 }

	 public ItemStack getGameItem(int ref){
	 	return this.gameItems.get(ref);
	 }

	public Location getMapHub(){
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("map-hub").getAsString());
	}

	public Location getMapLevelHub(){
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("level-hub").getAsString());
	}

	 public DropperLevel getDropperLevel(int ref){
	 	return this.registeredLevels.get(ref);
	 }
	 
	 public void usualGameTypeUpdate(Player player, GameType newGameType){
		 // TODO Respond in function of old gameType
		 this.getPlayer(player.getUniqueId()).updatePlayerGameType(newGameType);
	 }
	 
	 public void usualGameJoin(Player player){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		 
		 if(dpPlayer.getGameType().equals(GameType.FREE)){
			 player.teleport(this.getMapLevelHub());
			 player.getInventory().clear();
			 player.getInventory().setItem(0, this.getGameItem(2));

		 } else if(dpPlayer.getGameType().equals(GameType.COMPETITION)){
			 /*
			  * TODO Function to get logically the next level
			  * TODO Broadcast message (function to syntax LevelName & GameType)
			  * TODO Update player inventory
			  */
		 }

		 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(
				 player.getName() + ChatColor.AQUA + " a commencé une nouvelle partie en mode "
						 + this.getGameTypeFormatColor(dpPlayer.getGameType())  + ChatColor.AQUA + " !",true);

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
	 
	 public void usualGameLeave(Player player, boolean byPlayer){
		 DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());

		 if(byPlayer){
			 player.sendMessage(ChatColor.AQUA + "Vous avez quitté votre partie en mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()));
		 } else {
			 SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(
					 player.getName() + ChatColor.AQUA + " a terminé une partie en mode "
							 + this.getGameTypeFormatColor(dpPlayer.getGameType())  + ChatColor.AQUA + " !",true);
		 }
		 
		 player.teleport(this.getMapHub());
		 dpPlayer.updatePlayerGameType(GameType.UNSELECTED);
		 dpPlayer.updateCurrentLevel(null);
		 player.getInventory().clear();
		 player.getInventory().setItem(0, this.getGameItem(0));
		 player.getInventory().setItem(1, this.getGameItem(1));
		 
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
