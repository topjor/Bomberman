package io.github.mdsimmo.bomberman;

import io.github.mdsimmo.bomberman.Bomb.DeathBlock;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Game implements Listener {

	private static HashMap<String, Game> gameRegestry = new HashMap<>();
	private static Plugin plugin = Bomberman.instance;

	/**
	 * finds the game associated with the given name
	 */
	public static Game findGame(String name) {
		return gameRegestry.get(name.toLowerCase());
	}

	public static List<String> allGames() {
		List<String> games = new ArrayList<>();
		for (String name : gameRegestry.keySet()) {
			games.add(name);
		}
		return games;
	}

	/**
	 * Registers the game
	 * 
	 * @param game
	 *            The game to register
	 */
	public static void register(Game game) {
		gameRegestry.put(game.name.toLowerCase(), game);
	}

	public void deregister() {
		gameRegestry.remove(name.toLowerCase());
		EntityDamageEvent.getHandlerList();
		terminate();
		for (PlayerRep rep : new ArrayList<PlayerRep>(observers)) {
			rep.destroy();
		}
		HandlerList.unregisterAll(protector);
		File f = new File(plugin.getDataFolder() + "/" + name + ".game");
		f.delete();
		f = new File(plugin.getDataFolder() + "/" + name + ".old.board");
		f.delete();
	}

	public static void loadGames() {
		File data = plugin.getDataFolder();
		if (!data.exists())
			data.mkdirs();
		File[] files = data.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return (name.endsWith(".game"));
			}
		});
		for (File f : files) {
			loadGame(f.getName().split(".game")[0]);
		}
	}

	public static void loadGame(String name) {
		YamlConfiguration save = YamlConfiguration.loadConfiguration(getSaveFile(name));
		name = save.getString("name");
		int x = save.getInt("location.x");
		int y = save.getInt("location.y");
		int z = save.getInt("location.z");
		World w = plugin.getServer().getWorld(save.getString("location.world"));
		Game game = new Game(name, new Location(w, x, y, z));
		game.save = save;
		game.board = BoardGenerator.loadBoard(save.getString("style.current"));
		game.oldBoard = BoardGenerator.loadBoard(save.getString("style.old"));
	
		game.initVars();
		
		register(game);
	}

	public void saveGame() {
		try {
			save.set("name", name);
			save.set("location.world", loc.getWorld().getName());
			save.set("location.x", loc.getBlockX());
			save.set("location.y", loc.getBlockY());
			save.set("location.z", loc.getBlockZ());
			save.set("style.current", board.name);
			save.set("style.old", oldBoard.name);
			
			save.save(getSaveFile(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (BoardGenerator.loadBoard(oldBoard.name) == null)
			BoardGenerator.saveBoard(oldBoard);
	}
	
	public static File getSaveFile(String name) {
		return new File(plugin.getDataFolder(), name.toLowerCase()+".game");
	}

	private YamlConfiguration save = new YamlConfiguration();
	protected String name;
	protected Location loc;
	protected Board oldBoard;
	protected boolean isPlaying;
	private GameProtection protector;
	protected ArrayList<PlayerRep> observers = new ArrayList<>();
	public ArrayList<PlayerRep> players = new ArrayList<>();
	protected Board board;
	private int bombs;
	private int power;
	private int lives;
	private int minPlayers;
	private ItemStack fare;
	private ItemStack prize;
	private boolean pot;
	public List<DeathBlock> deathBlocks = new ArrayList<>();
	public Map<Block, Bomb> explosions = new HashMap<>();
	private boolean autostart;
	private List<ItemStack> drops;
	private double dropChance;
	private List<Material> destructables;
	private List<Material> droppingBlocks;
	private int suddenDeath;
	private boolean suddenDeathStarted = false;
	private int timeout;
	
	public Game(String name, Location loc) {
		this.name = name;
		this.loc = loc;
		initVars();
		protector = new GameProtection(this);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void initVars() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(getSaveFile(name));
		prize 			= Config.PRIZE.getValue(config);
		pot				= Config.POT.getValue(config);
		fare			= Config.FARE.getValue(config);
		bombs			= Config.BOMBS.getValue(config);
		power			= Config.POWER.getValue(config);
		lives			= Config.LIVES.getValue(config);
		minPlayers		= Config.MIN_PLAYERS.getValue(config);
		autostart		= Config.AUTOSTART.getValue(config);
		destructables	= Config.BLOCKS_DESTRUCTABLE.getValue(config);
		droppingBlocks	= Config.BLOCKS_DROPPING.getValue(config);
		drops			= Config.DROPS_ITEMS.getValue(config);
		dropChance		= Config.DROPS_CHANCE.getValue(config);
		suddenDeath		= Config.SUDDEN_DEATH.getValue(config);
		timeout			= Config.TIME_OUT.getValue(config);
	}

	public boolean containsLocation(Location l) {
		return (l.getBlockX() >= loc.getX() && l.getBlockX() < loc.getBlockX()
				+ board.xSize)
				&& (l.getBlockY() >= loc.getY() && l.getBlockY() < loc
						.getBlockY() + board.ySize)
				&& (l.getBlockZ() >= loc.getZ() && l.getBlockZ() < loc
						.getBlockZ() + board.zSize);
	}

	public Vector findSpareSpawn() {
		for (Vector v : board.spawnPoints) {
			if (blockEmpty(v))
				return v;
		}
		return null;
	}

	/**
	 * gets if there are any players <b>in</b> the block given by the vector (from game corner)
	 * @return true if no player is in the block
	 */
	private boolean blockEmpty(Vector v) {
		for (PlayerRep rep : players) {
			Block under = rep.player.getLocation().getBlock();
			Block block = loc.clone().add(v).getBlock();
			if (block.equals(under))
				return false;
		}
		return true;
	}
	
	/**
	 * Starts the game
	 * @return true if the game was started succesfully
	 */
	public boolean startGame() {
		if (players.size() >= minPlayers) {
			new GameStarter();
			return true;
		} else {
			return false;
		}
	}

	private class GameStarter implements Runnable {
		int count = 3;

		public GameStarter() {
			plugin.getServer().getScheduler()
					.scheduleSyncDelayedTask(plugin, this);
		}

		public void run() {
			if (count > 0) {
				for (PlayerRep rep : players)
					rep.player.sendMessage("" + count);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 20);
			} else {
				for (PlayerRep rep : observers) {
					rep.player.sendMessage(ChatColor.YELLOW + "Game started!");
					isPlaying = true;
					if (suddenDeath >= 0 || timeout >= 0)
						new SuddenDeathCounter(Game.this);
				}
			}
			count--;
		}

	}

	public void drop(Location l, Material type) {
		if (Math.random() < dropChance && droppingBlocks.contains(type)) {
			int sum = 0;
			for (ItemStack stack : drops)
				sum += stack.getAmount();
			double rand = Math.random() * sum;
			for (ItemStack stack : drops) {
				rand -= stack.getAmount();
				if (rand <= 0) {
					ItemStack drop = stack.clone();
					drop.setAmount(1);
					l.getWorld().dropItem(l, drop);
					return;
				}
			}
		}
	}

	public PlayerRep getPlayerRep(Player player) {
		for (PlayerRep p : observers) {
			if (p.player == player) {
				return p;
			}
		}
		return null;
	}

	/** 
	 * Terminates the game. <br>
	 * Kicks all playes out. Doesn't give awards. Does not deregister the game
	 */
	public void terminate() {
		isPlaying = false;
		for (PlayerRep rep : new ArrayList<PlayerRep>(observers)) {
			rep.destroy();
		}
	}
	
	/**
	 * updates the status of the game.
	 * @return true if the game has finished;
	 */
	public boolean checkFinish() {
		if (players.size() <= 1 && isPlaying) {
			isPlaying = false;
			
			ArrayList<PlayerRep> winners = new ArrayList<>();
			for (PlayerRep rep : observers) {
				if (rep.deathTime != -1)
					addWinner(winners, rep);
			}
			
			// kill the remaining survivors and add them to the winners
			for (PlayerRep rep : new ArrayList<>(players)) {
				rep.kill(false);
				winners.add(0, rep);
			}
			
			// get the total winnings
			if (pot == true)
				if (fare == null)
					prize = null;
				else
					prize = new ItemStack(fare.getType(), fare.getAmount()*winners.size());
			
			// give the winner the prize
			if (prize != null) {
				Player topPlayer = winners.get(0).player;
				topPlayer.getInventory()
						.addItem(prize);
			}
			
			// display the scores
			for (PlayerRep rep : observers) {
				rep.player.sendMessage(ChatColor.YELLOW + "The game is over!");
				rep.player.sendMessage(scoreDisplay(winners));
			}
			
			// reset the game
			BoardGenerator.switchBoard(this.board, this.board, loc);
			terminate();
			
			return true;
		}
		return !isPlaying;
	}
	
	private void addWinner(ArrayList<PlayerRep> winners, PlayerRep rep) {
		for (int i = 0; i < winners.size(); i++) {
			if (rep.deathTime > winners.get(i).deathTime) {
				winners.add(i, rep);
				return;
			}
		}
		winners.add(rep);
	}

	public String scoreDisplay(ArrayList<PlayerRep> winners) {
		String display = "The scores are:\n";
		int i = 0;
		while (i < winners.size() && i < 8) {
			PlayerRep rep = winners.get(i);
			i++;
			String place;
			switch (i) {
			case 1:
				place = "1st";
				break;
			case 2:
				place = "2nd";
				break;
			case 3:
				place = "3rd";
				break;
			default:
				place = i+"th";
			}			
			display += " " + place + ": " + rep.player.getName() + " (" + rep.kills + " kills)\n";
		}
		return display;
	}

	/**
	 * call when a player dies
	 */
	public void alertRemoval(PlayerRep player) {
		if (!checkFinish()) {
			for (PlayerRep rep : observers) {
				if (rep.player != player)
					rep.player.sendMessage(ChatColor.YELLOW + player.player.getName()
							+ " is out");
			}
		}
	}

	public void addPlayer(PlayerRep rep) {
		players.add(rep);
		if (autostart)
			startGame();
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public void setBombs(int bombs) {
		this.bombs = bombs;
		save.set(Config.BOMBS.getPath(), bombs);
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
		save.set(Config.POWER.getPath(), power);
	}

	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
		save.set(Config.LIVES.getPath(), lives);
	}

	public int getMinPlayers() {
		return minPlayers;
	}
	
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
		save.set(Config.MIN_PLAYERS.getPath(), minPlayers);
	}
	
	public boolean getAutostart() {
		return autostart;
	}
	
	public void setAutostart(boolean autostart) {
		this.autostart = autostart;
		save.set(Config.AUTOSTART.getPath(), autostart);
	}
	
	public List<Material> getDestructables() {
		return destructables;
	}
	
	public List<Material> getDroppingBlocks() {
		return droppingBlocks;
	}
	
	public ItemStack getFare() {
		return fare;
	}
	
	public void setFare(ItemStack fare) {
		this.fare = fare;
		save.set(Config.FARE.getPath(), fare);
	}
	
	public ItemStack getPrize() {
		return prize;
	}
	
	public void setPrize(ItemStack prize) {
		this.prize = prize;
		pot = false;
		save.set(Config.PRIZE.getPath(), prize);
	}
	
	public boolean getPot() {
		return pot;
	}
	
	public void setPot(boolean pot) {
		this.pot = pot;
		if (pot)
			save.set(Config.PRIZE.getPath(), true);
		else
			save.set(Config.PRIZE.getPath(), prize);
	}
	
	public void setPrize(ItemStack prize, boolean pot) {
		setPrize(prize);
		setPot(pot);
	}

	public int getSuddenDeath() {
		return suddenDeath;
	}
	
	public void setSuddenDeath(int time) {
		suddenDeath = time;
		save.set(Config.SUDDEN_DEATH.getPath(), time);
	}
	
	public boolean isSuddenDeath() {
		return suddenDeathStarted;
	}
	
	public void setSuddenDeath(boolean started) {
		if (started == true)
			for (PlayerRep rep : players)
				rep.player.setHealth(1d);
		suddenDeathStarted = started;
	}
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int time) {
		timeout = time;
		save.set(Config.TIME_OUT.getPath(), time);
	}
}
