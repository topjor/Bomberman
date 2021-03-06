package io.github.mdsimmo.bomberman;

import io.github.mdsimmo.bomberman.playerstates.GamePlayingState;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Bomb implements Runnable {

	private Plugin plugin = Bomberman.instance;
	private PlayerRep rep;
	private Block tnt;
	private Location spawn;
	private Game game;
	private int strength;
	private int eTaskId;

	public Bomb( Game game, PlayerRep rep, Block tnt ) {
		this.game = game;
		this.rep = rep;
		this.tnt = tnt;
		strength = ((GamePlayingState)rep.getState()).bombStrength();
		spawn = tnt.getLocation();
		eTaskId = plugin.getServer().getScheduler()
				.scheduleSyncDelayedTask( plugin, this, 60 );
		game.explosions.put( tnt, this );
	}

	@Override
	public void run() {
		new Explosion();
	}

	public class Explosion implements Listener, Runnable {

		public Explosion() {
			game.explosions.remove( tnt );
			// avoid bombs going off after game has finished
			if ( game.state != Game.State.PLAYING )
				return;
			tnt.setType( Material.AIR );
			plugin.getServer().getPluginManager().registerEvents( this, plugin );
			plugin.getServer().getScheduler()
					.scheduleSyncDelayedTask( plugin, this, 20 );
			spawn.getWorld().playSound( spawn, Sound.ENTITY_GENERIC_EXPLODE, 1,
					(float)Math.random() + 0.5f );
			createFire();
		}

		/**
		 * creates fire in the '+' pattern
		 */
		private void createFire() {
			createFire( 0, 1 );
			createFire( 0, -1 );
			createFire( 1, 0 );
			createFire( -1, 0 );
		}

		/**
		 * creates a line of fire in the given x, z direction;
		 * 
		 * @param xstep
		 *            the unit to step in the x direction
		 * @param zstep
		 *            the unit to step in the z direction
		 */
		private void createFire( int xstep, int zstep ) {
			for ( int i = 0; i <= strength; i++ ) {
				createFire( i * xstep, 1, i * zstep );
				createFire( i * xstep, -1, i * zstep );
				if ( createFire( i * xstep, 0, i * zstep ) )
					return;
			}
		}

		/**
		 * creates fire at the given location if it can. Returns true if the
		 * fire-ball should stop
		 */
		private boolean createFire( int x, int y, int z ) {
			Location l = spawn.clone().add( z, y, x );
			Block b = l.getBlock();

			// destroy dirt (or other blocks that can be blown up)
			if ( game.board.isDestructable( b.getType() ) ) {
				new DeathBlock( b, rep );
				return true;
			}

			// create fire on non solid blocks
			if ( !b.getType().isSolid() ) {
				new DeathBlock( b, rep );
				return false;
			}

			// explode other tnts
			for ( Block otherTnt : new HashSet<>( game.explosions.keySet() ) ) {
				if ( otherTnt.equals( b ) ) {
					Bomb other = game.explosions.get( otherTnt );
					plugin.getServer().getScheduler()
							.cancelTask( other.eTaskId );
					other.run();
					return true;
				}
			}
			// not solid so stop
			return true;
		}

		@Override
		public void run() {
			// check that the player is still playing
			if ( rep.isPlaying() && rep.getState().getGame() == game )
				rep.getPlayer().getInventory().addItem(
						new ItemStack( game.getBombMaterial() ) );
		}

	}

	public class DeathBlock implements Runnable {

		public PlayerRep cause;
		private Block block;
		private int duration = 20;
		private int dbTaskId;
		private Material original;

		public DeathBlock( Block block, PlayerRep cause ) {
			this.block = block;
			this.cause = cause;

			original = block.getType();
			block.setType( Material.FIRE );
			dbTaskId = plugin.getServer().getScheduler()
					.scheduleSyncRepeatingTask( plugin, this, 0, 1 );

			for ( DeathBlock db : new ArrayList<>( game.deathBlocks ) )
				if ( db.block.equals( block ) ) {
					// remove the old block
					plugin.getServer().getScheduler().cancelTask( db.dbTaskId );
					game.deathBlocks.remove( db );
				}
			game.deathBlocks.add( this );
		}

		@Override
		public void run() {
			for ( PlayerRep rep : new ArrayList<PlayerRep>( game.players ) ) {
				if ( touching( rep.getPlayer() ) ) {
					if (!(rep.getState() instanceof GamePlayingState))
						return;
					GamePlayingState state = (GamePlayingState)rep.getState();
					state.damage( cause );
				}
			}
			if ( --duration <= 0 ) {
				if ( block.getType() == Material.FIRE )
					block.setType( Material.AIR );
				game.drop( block.getLocation().add( 0.5, 0.5, 0.5 ), original );
				plugin.getServer().getScheduler().cancelTask( dbTaskId );
				game.deathBlocks.remove( this );
			}
		}

		public boolean touching( Player player ) {
			double margin = 0.295; // magical value that seems to be how far
									// fire burns
			Location l = player.getLocation();
			Location min = block.getLocation().add( 0, -1, 0 );
			Location max = block.getLocation().add( 1, 2, 1 );
			if ( l.getX() >= min.getX() - margin
					&& l.getX() <= max.getX() + margin
					&& l.getY() >= min.getY() - margin
					&& l.getY() <= max.getY() + margin
					&& l.getZ() >= min.getZ() - margin
					&& l.getZ() <= max.getZ() + margin )
				return true;
			else
				return false;

		}
	}

}