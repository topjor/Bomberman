package io.github.mdsimmo.bomberman;

import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Text;
import io.github.mdsimmo.bomberman.utils.BlockLocation;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Attachable;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public class SignHandler implements Listener {

    private final static Plugin plugin = Bomberman.instance;
    private static final BlockFace[] surrounds = new BlockFace[] {
        BlockFace.DOWN, BlockFace.UP, BlockFace.EAST, BlockFace.WEST,
        BlockFace.NORTH, BlockFace.SOUTH };
    static {
        new SignHandler();
    }
    
    static void load() {
        // loading is done in static block above
    }
    
    private SignHandler() {
        plugin.getServer().getPluginManager().registerEvents( this, plugin );
    }
    
    @EventHandler( priority = EventPriority.NORMAL )
    public void onPlayerClick( PlayerInteractEvent e ) {
        if ( e.isCancelled() )
            return;
        if ( e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking() ) {
            Block b = e.getClickedBlock();
            BlockLocation l = BlockLocation.getLocation( b );
            if ( CommandSign.execute( l, e.getPlayer() ) )
                e.setCancelled( true );
        }
    }

    // backup needed in case a sign breaks without our knowing of it (piston, anther plugin, etc)
    @EventHandler( priority = EventPriority.HIGH )
    public void onSignBreak( BlockPhysicsEvent e ) {
        if ( e.isCancelled() )
            return;
        Block b = e.getBlock();
		// if the block is a command sign but is now over air, destroy the sign
		if ( e.getBlock().getType() == Material.AIR ) {
			BlockLocation l = BlockLocation.getLocation( b );
			if ( CommandSign.removeSign( l ) )
				plugin.getLogger( ).warning( "Command sign forcibly removed at " + l );
		}
    }
    
    @EventHandler( priority = EventPriority.LOW )
    public void stopSignBreaking( BlockBreakEvent e ) {
        if ( e.isCancelled() )
            return;
        Block b = e.getBlock();
        BlockLocation l = BlockLocation.getLocation( b );
        if ( CommandSign.isCommandSign( l ) ) {
            Chat.sendMessage( Text.SIGN_CANT_BREAK.getMessage(
                    e.getPlayer() ).put( "block", b ).put( "signblock", b ) );
            e.setCancelled( true );
            return;
        }
        for ( BlockFace face : surrounds ) {
            Block adjacent = b.getRelative( face );
            MaterialData data = adjacent.getState().getData();
            if ( !(data instanceof Attachable) )
                continue;
            Attachable attachable = (Attachable)data;
            if ( attachable.getAttachedFace().getOppositeFace() != face )
                continue;
            BlockLocation l2 = BlockLocation.getLocation( adjacent );
            if ( CommandSign.isCommandSign( l2 ) ) {
                Chat.sendMessage( Text.SIGN_CANT_BREAK.getMessage(
                        e.getPlayer() ).put( "block", b ).put( "signblock", adjacent ) );
                e.setCancelled( true );
                return;
            }
        }
    }
}
