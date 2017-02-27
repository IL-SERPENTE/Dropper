package net.samagames.dropper;

import java.util.*;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.dropper.level.EffectManager;
import net.samagames.tools.ProximityUtils;
import net.samagames.tools.Titles;
import net.samagames.tools.chat.ActionBarAPI;
import net.samagames.tools.tutorials.Tutorial;
import org.bukkit.*;
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
	 * This is the game class, the game was mainly managed here.
	 * @author Vialonyx
	 */

	private static DropperMain instance;
	private EffectManager effectManager;
	private List<DropperLevel> registeredLevels;
	private Map<ItemStack, String> itemsDescriptions;
	private Tutorial tutorial;


	// Creating game items.
	public static final ItemStack ITEM_MODE_FREE = stackBuilder(" ", Arrays.asList(ChatColor.GREEN + "Entrainez vous autant que vous le voulez sur chaque niveau !"), Material.BANNER, (byte) 10);
	public static final ItemStack ITEM_MODE_COMPETITION = stackBuilder(" ", Arrays.asList(ChatColor.RED + "Enchainez le plus de niveaux possibles, la moindre erreur vous sera fatale !"), Material.BANNER, (byte) 1);
	public static final ItemStack ITEM_QUIT_GAME = stackBuilder(" ", Arrays.asList(ChatColor.RED + "Quitter le mode de jeu"), Material.WOOD_DOOR, (byte) 0);
	public static final ItemStack ITEM_QUIT_LEVEL = stackBuilder(" ", Arrays.asList(ChatColor.RED + "Quitter le niveau actuel"), Material.BARRIER, (byte) 0);
	public static final ItemStack ITEM_SELECTGUI = stackBuilder(" ", Arrays.asList(ChatColor.GOLD + "Sélectionnez un niveau pour vous entrainer !"), Material.ITEM_FRAME, (byte) 0);

	public Dropper(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, DropperMain instance) {
		super(gameCodeName, gameName, gameDescription, gamePlayerClass);

		this.instance = instance;
		getWorlds().get(0).setSpawnLocation(this.getSpawn().getBlockX(), this.getSpawn().getBlockY(), this.getSpawn().getBlockZ());

		// Creating descriptions for items.
        this.itemsDescriptions = new HashMap<>();
        this.itemsDescriptions.put(this.ITEM_MODE_FREE, this.ITEM_MODE_FREE.getItemMeta().getLore().toString().replace("[", "").replace("]", ""));
        this.itemsDescriptions.put(this.ITEM_MODE_COMPETITION, this.ITEM_MODE_COMPETITION.getItemMeta().getLore().toString().replace("[", "").replace("]", ""));
        this.itemsDescriptions.put(this.ITEM_QUIT_GAME, this.ITEM_QUIT_GAME.getItemMeta().getLore().toString().replace("[", "").replace("]", ""));
        this.itemsDescriptions.put(this.ITEM_QUIT_LEVEL, this.ITEM_QUIT_LEVEL.getItemMeta().getLore().toString().replace("[", "").replace("]", ""));
        this.itemsDescriptions.put(this.ITEM_SELECTGUI, this.ITEM_SELECTGUI.getItemMeta().getLore().toString().replace("[", "").replace("]", ""));

		// Registering levels.
		this.registeredLevels = new ArrayList<>();
		this.registeredLevels.add(new DropperLevel(1, 1,"Madness", "n/a"));
        this.registeredLevels.add(new DropperLevel(2, 1,"The Fall", "n/a"));
        this.registeredLevels.add(new DropperLevel(3, 1,"In The Middle", "n/a"));
        this.registeredLevels.add(new DropperLevel(4, 1,"Cars", "n/a"));
        this.registeredLevels.add(new DropperLevel(5, 1,"Mine", "n/a"));
        this.registeredLevels.add(new DropperLevel(6, 1,"Curtain", "n/a"));
        this.registeredLevels.add(new DropperLevel(7, 1,"Some holes are closed", "n/a"));
        this.registeredLevels.add(new DropperLevel(8, 1,"Reflection", "n/a"));
        this.registeredLevels.add(new DropperLevel(9, 1,"Step by Step", "n/a"));
        this.registeredLevels.add(new DropperLevel(10, 1,"Rail", "n/a"));
        this.registeredLevels.add(new DropperLevel(11, 1,"Hell", "n/a"));
        this.registeredLevels.add(new DropperLevel(12, 1,"The End", "n/a"));
		this.registeredLevels.add(new DropperLevel(13,2, "Isengard", "En pleine terre du milieu ..."));
		this.registeredLevels.add(new DropperLevel(14,2, "Neo", "Un nouvel univers informatique"));
		this.registeredLevels.add(new DropperLevel(15,2, "Symbols", "Arriverez vous à déchiffrer les symboles ?"));
		this.registeredLevels.add(new DropperLevel(16,2, "The Three", "Panique en plein pique-nique !"));
		this.registeredLevels.add(new DropperLevel(17,2, "Embryo", "Au commencement d'une vie"));
		this.registeredLevels.add(new DropperLevel(18,2, "Brain", "Dans votre masse cérébrale ..."));
		this.registeredLevels.add(new DropperLevel(29,2, "Dimension Jumper", "Hé non ! rien à voir avec le Dimensions !"));
		this.registeredLevels.add(new DropperLevel(20,2, "BeetleJuice", "Ne vous perdez pas dans l'illusion !"));
		this.registeredLevels.add(new DropperLevel(21,2, "Web", "L'arignée est en train de tisser sa toile ..."));
		this.registeredLevels.add(new DropperLevel(22,2, "Armor", "L'acordéon de la chute vous attend "));
		this.registeredLevels.add(new DropperLevel(23,2, "Dracula's Bedroom", "Protégez vous bien des morsures !"));
		this.registeredLevels.add(new DropperLevel(24,2, "DNA", "Votre code génétique est-il si complexe ?"));
		this.registeredLevels.add(new DropperLevel(25,2, "Minecraft is huge", "Ce monde cubique paraît si petit ..."));
		this.registeredLevels.add(new DropperLevel(26,2, "Hardware", "Votre ordinateur vous cache des choses !"));
		this.registeredLevels.add(new DropperLevel(27,2, "Moria", "Sauve qui peut !"));

		// Registering the level manager.
		this.effectManager = new EffectManager();

		// Registering the tutorial.
		this.tutorial = new DropperTutorial(this);

		// Create proximity tasks for special levels.
		BukkitScheduler bukkitScheduler = this.instance.getServer().getScheduler();

		ProximityUtils.onNearbyOf(this.instance, this.armorStandBuilder(new Location(getWorlds().get(0), 1444, 16, 1327), getWorlds().get(0)), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
				() -> player.teleport(new Location(getWorlds().get(0), 510, 177, 1531))));

		ProximityUtils.onNearbyOf(this.instance, this.armorStandBuilder(new Location(getWorlds().get(0), 2727, 6, -302), getWorlds().get(0)), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
				() -> player.teleport(new Location(getWorlds().get(0),2705, 251, -366))));

		ProximityUtils.onNearbyOf(this.instance, this.armorStandBuilder(new Location(getWorlds().get(0),  2706, 9, -368), getWorlds().get(0)), 1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
				() -> player.teleport(new Location(getWorlds().get(0),2586, 251, -445))));

	}

	/**
	 * Called by SamaGamesAPI when player login.
	 * @param player the player.
	 */

	@Override
	public void handleLogin(Player player){
		super.handleLogin(player);
		player.teleport(this.getSpawn());
		player.getInventory().clear();
		player.getInventory().setItem(3, this.ITEM_MODE_FREE);
		player.getInventory().setItem(5, this.ITEM_MODE_COMPETITION);
		player.setCollidable(false);
		player.setBedSpawnLocation(this.getSpawn(), true);
		this.effectManager.restoreDefaultEffects(player);
        this.getPlayer(player.getUniqueId()).defineNewAFKChecker(new AFKChecker(this.instance, player));
	}

	@Override
    public void handleLogout(Player player){
	    this.getPlayer(player.getUniqueId()).getAfkChecker().cancel();
    }

	/**
	 * Get the main instance of Dropper game.
	 * @return an instance of dropper game plugin.
	 */

	public static DropperMain getInstance(){
		return instance;
	}

	/**
	 * Get an instance of the effect manager.
	 * @return an instance of effect manager.
	 */

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
	 * Get the location of the spawn from the json file.
	 * @return the spawn location.
	 */

	public Location getSpawn(){
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		Location loc = LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("map-hub").getAsString());

		return loc.add(loc.getX() > 0 ? 0.5 : -0.5, 0.0, loc.getZ() > 0 ? 0.5 : -0.5);
	}

    /**
     * Get description for items.
     * @return a map of items & descriptions as String.
     */

	public Map<ItemStack, String> getItemsDescriptions(){
	    return this.itemsDescriptions;
    }

	/**
	 * Get a dropper level by his ID.
	 * @param ref the level ID.
	 * @return the DropperLevel with gived ID.
	 */

	public DropperLevel getDropperLevel(int ref){
		return this.registeredLevels.get(ref);
	}

    /**
     * Get the Dropper tutorial.
     * @return the dropper tutorial.
     */

	public Tutorial getTutorial(){
	    return this.tutorial;
    }

	/**
	 * Update the gametype of the player.
	 * @param player the player.
	 * @param newGameType the new gametype.
	 */

	public void usualGameTypeUpdate(Player player, GameType newGameType){

		this.getPlayer(player.getUniqueId()).updatePlayerGameType(newGameType);
		player.getInventory().clear();

		if(! newGameType.equals(GameType.UNSELECTED)){
		    player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + " Vous jouez désormais en mode " + this.getGameTypeFormatColor(newGameType));
		}

		if(newGameType.equals(GameType.FREE)){
			player.getInventory().setItem(3, this.ITEM_SELECTGUI);
			player.getInventory().setItem(5, this.ITEM_QUIT_GAME);

		} else if(newGameType.equals(GameType.COMPETITION)){
			player.getInventory().clear();
			player.getInventory().setItem(4,this.ITEM_QUIT_GAME);
			this.usualLevelJoin(player, this.getRegisteredLevels().get(0));
		}

	}

	/**
	 * This is the entry point of the level-joining process.
	 * @param player the player.
     * @param level the level.
	 */

	public void usualLevelJoin(Player player, DropperLevel level) {
        DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());

			// Managing player inventory.
			player.getInventory().clear();
			player.getInventory().setItem(4, this.ITEM_QUIT_GAME);

			// Updating current level of player.
			this.getPlayer(player.getUniqueId()).updateCurrentLevel(level);

			// Sending title with level's name & his description.
			Titles.sendTitle(player, 30, 70, 30, "" + ChatColor.YELLOW + ChatColor.BOLD + level.getName(), "" + ChatColor.RED + ChatColor.ITALIC + level.getDescription());

			// Starting cooldown if he does not have anyone started before.
			if (!this.getPlayer(player.getUniqueId()).hasActiveCooldown()) {
				new DropperCooldown(this, player, level).runTaskTimer(this.instance, 0L, 20L);
			}
    }

	/**
	 * This is the entry point of the level-leaving process.
	 * @param player the player.
	 * @param cancelled true if the level was leaved during the cooldown.
	 */

	public void usualLevelLeave(Player player, boolean cancelled){
		DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		DropperLevel level = dpPlayer.getCurrentLevel();

		// Checking if the level is cancelled.
		if(cancelled){

			// Stopping current cooldown and sending message to the player.
			dpPlayer.getActiveCooldown().cancel();
			dpPlayer.resetCooldownData();
			ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.DARK_RED + "Démarrage du niveau annulé !");

			// Sending message to the player.
            player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + " Vous avez quitté le niveau " + ChatColor.GOLD + level.getID() + ChatColor.AQUA + " (" + level.getName() + ChatColor.AQUA + ")");
		} else {
            player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + " Vous avez terminé le niveau " + ChatColor.GOLD + level.getID() + ChatColor.AQUA + " (" + level.getName() + ChatColor.AQUA + ")");
		}

		// Calling the custom LevelQuitEvent.
		LevelQuitEvent levelQuitEvent = new LevelQuitEvent(player, level);
		this.getInstance().getServer().getPluginManager().callEvent(levelQuitEvent);

	}

	/**
	 * This is the entry point of the level-leaving process.
	 * @param player The player.
	 */

	public void usualGameLeave(Player player){
		DropperPlayer dpPlayer = this.getPlayer(player.getUniqueId());
		if(dpPlayer.getCurrentLevel() != null)
		    player.teleport(getSpawn());

		if(dpPlayer.hasActiveCooldown()){
			dpPlayer.getActiveCooldown().cancel();
			dpPlayer.resetCooldownData();
			ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.RED + "Démarrage du niveau annulé !");
		}

        player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + " Vous avez quitté le mode " + this.getGameTypeFormatColor(dpPlayer.getGameType()));
		if(dpPlayer.getCurrentLevel() == null){
			player.teleport(this.getSpawn());
		}

		dpPlayer.updatePlayerGameType(GameType.UNSELECTED);
		dpPlayer.updateCurrentLevel(null);
		player.getInventory().clear();
		player.getInventory().setItem(3, this.ITEM_MODE_FREE);
		player.getInventory().setItem(5, this.ITEM_MODE_COMPETITION);

	}

	/**
	 * Logically get to the next level from the current (used as competition gametype.)
	 * @param current the current level.
	 * @return the next level.
	 */

	public DropperLevel getNextFromCurrent(DropperLevel current){
		return this.getDropperLevel(current.getID()-1);
	}

	/**
	 * Get the gametype color.
	 * @param type the gametype.
	 * @return the colored gametype.
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
	 * Build an ItemStack simply.
	 * @param name Item name.
	 * @param lore Item lore.
	 * @param material The material of item.
	 * @param data Item data.
	 * @return The builded ItemStack.
	 */

	public static ItemStack stackBuilder(String name, List<String> lore, Material material, byte data){
		org.bukkit.inventory.ItemStack tmpStack = new ItemStack(material, 1, data);
		ItemMeta tmpStackMeta = tmpStack.getItemMeta();
		tmpStackMeta.setDisplayName(name);
		tmpStackMeta.setLore(lore);
		tmpStack.setItemMeta(tmpStackMeta);

		return tmpStack;
	}

	/**
	 * Build an ArmorStand simply. Used for Proximity Tasks.
	 * @param spawn the location where we want to spawn the AS.
	 * @param world the world.
	 * @return
	 */

	public static ArmorStand armorStandBuilder(Location spawn, World world){
		ArmorStand as = (ArmorStand) world.spawnEntity(spawn, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setGravity(false);
		return as;
	}

	/**
	 * Get the word at singular/plural depends to the act value.
	 * @param act the current value of cooldown.
	 * @return a correct word.
	 */

	public static String formatSecondsText(int act){
		if(act > 1){
			return "secondes";
		} else {
			return "seconde";
		}
	}

}
