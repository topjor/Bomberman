package io.github.mdsimmo.bomberman.arenabuilder;

import io.github.mdsimmo.bomberman.BlockRep;
import io.github.mdsimmo.bomberman.Board;
import io.github.mdsimmo.bomberman.Bomberman;
import io.github.mdsimmo.bomberman.arenabuilder.ArenaDetector.BoundingListener;
import io.github.mdsimmo.bomberman.save.BoardSaver;
import io.github.mdsimmo.bomberman.utils.Box;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * A class designed to greatly symplify the process of generating arenas.
 */
public class ArenaGenerator {

	private static HashMap<String, Board> loadedBoards = new HashMap<>();
	private static Plugin plugin = Bomberman.instance;

	/**
	 * Copies all the default boards into the config folder
	 */
	public static void copyDefaults() {
		String[] defaults = { "default", "layers" };
		for ( String name : defaults ) {
			File file = toFile( name );
			if ( file.exists() ) {
				// already copied
				continue;
			}
			plugin.getLogger()
					.info( "Copying the default arena '" + name + "'" );
			try {
				file.createNewFile();
				InputStream inputStream = plugin.getResource( name + ".arena" );
				FileOutputStream fos = new FileOutputStream( file );
				int read = 0;
				byte[] bytes = new byte[1024];

				while ( ( read = inputStream.read( bytes ) ) != -1 )
					fos.write( bytes, 0, read );

				fos.flush();
				fos.close();
				inputStream.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	public interface BuildListener {
		public void onContructionComplete();
	}
	
	/**
	 * Cleanly replaces the current board with the next board. The boards should 
	 * always be of equal size. Use this method to destroy and create board;
	 * Note: when changing boards, this method should be used twice; once to the
	 * original board, then to the new board. 
	 * 
	 * @param current
	 *            the board currently in the world. May be not be null
	 * @param next
	 *            the board that you want. Must not be null.
	 * @param box
	 *            the box bounding the boards.
	 * @param l the listener to inform the build has finished. Null to not listen
	 * @throws IllegalArgumentException if the boards are not the same size
	 */
	public static void switchBoard( Board current, Board next, Box box, BuildListener l ) {
		if ( current.xSize != next.xSize || current.xSize != box.xSize 
				|| current.ySize != next.ySize || current.ySize != box.ySize
				|| current.zSize != next.zSize || current.zSize != box.zSize )
			throw new IllegalArgumentException( "Boards must all be the same size" );
		
		// destroy all items
		for ( Entity entity : box.getEntities() ) {
			if ( entity instanceof Item )
				entity.remove();
		}
		
		// destroy delayed blocks first so they don't pop off
		for ( Vector v : current.delayed.keySet() ) {
			BlockRep.createBlank().setBlock( box.fromCorner( v ).getBlock() );
		}
		// build other blocks
		new ArenaBuilder( next, box.corner(), l );
	}

	/**
	 * Loads a board arena out of cache/save files.
	 * 
	 * @param name
	 *            the arena name
	 * @return the loaded board
	 */
	public static Board loadBoard( String name ) {
		try {
			if ( loadedBoards.containsKey( name ) )
				return loadedBoards.get( name );
			return BoardSaver.loadBoard( toFile( name ) );
		} catch ( IOException e ) {
			return null;
		}
	}

	/**
	 * Removes a board from the cache. Does not delete its file
	 * @param name the name of the board to remove
	 */
	public static void remove( String name ) {
		loadedBoards.remove( name );
	}

	/**
	 * Gets the file that contains the board
	 * @param name the name of the board
	 * @return the boards save file
	 */
	public static File toFile( String name ) {
		return new File( plugin.getDataFolder(), name.toLowerCase() + ".arena" );
	}

	/**
	 * Gets a list of all the boards that are installed.
	 * @return a list of al the boards name's
	 */
	public static List<String> allBoards() {
		List<String> boards = new ArrayList<>();
		File[] files = plugin.getDataFolder().listFiles( new FilenameFilter() {

			@Override
			public boolean accept( File dir, String name ) {
				return ( name.endsWith( ".arena" ) );
			}
		} );
		for ( File f : files ) {
			boards.add( f.getName().split( "\\.arena" )[0] );
		}
		return boards;
	}

	/**
	 * Saves a board to its file
	 * @param board the board to save
	 */
	public static void saveBoard( Board board ) {
		loadedBoards.put( board.name, board );
		new BoardSaver( board ).save();
	}

	/**
	 * Gets the box that surrounds the target block. Happens in an synchronised thread.
	 * @param target any block in the structure
	 * @param callBack who it inform that the structure has finished being detected
	 */
	public static void getBoundingStructure( Block target,
			BoundingListener callBack ) {
		ArenaDetector detector = new ArenaDetector();
		detector.getBoundingStructure( target, callBack );
	}

	/**
	 * Makes an arena from the given box. Does not save the arena.
	 * @param arena what to call the arena
	 * @param box the box that contains the arena's structure
	 * @return The newly generated arena
	 */
	public static Board createArena( String arena, Box box ) {
		Board board = new Board( arena, (int)box.xSize, (int)box.ySize,
				(int)box.zSize );
		for ( int i = 0; i < box.xSize; i++ ) {
			for ( int j = 0; j < box.ySize; j++ ) {
				for ( int k = 0; k < box.zSize; k++ ) {
					Vector v = new Vector( i, j, k );
					BlockRep block = BlockRep.createBlock( box.corner().add( v )
							.getBlock() );
					board.addBlock( block, v );
				}
			}
		}
		return board;
	}
}
