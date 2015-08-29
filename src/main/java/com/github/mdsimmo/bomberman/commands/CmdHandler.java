package com.github.mdsimmo.bomberman.commands;

import com.github.mdsimmo.bomberman.Bomberman;
import com.github.mdsimmo.bomberman.localisation.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CmdHandler implements CommandExecutor {

    private static final JavaPlugin plugin = Bomberman.instance();
    private static final HashMap<Class<? extends CmdGroup>, List<Cmd>> commands = new HashMap<Class<? extends CmdGroup>, List<Cmd>>();
    static {
        plugin.getCommand( "bomberman" ).setExecutor( new CmdHandler() );
    }

    public static void register( Cmd command ) {
        Class<CmdGroup> parent = command.parent();
        List<Cmd> subCommands = commands.get( parent );
        if ( subCommands == null )
            commands.put( parent, subCommands = new ArrayList<Cmd>() );
        subCommands.add( command );
    }

    public static List<Cmd> childrenOf( Class<? extends CmdGroup> cmd ) {
        if ( cmd == null )
            throw new NullPointerException( "command cannot be null" );
        List<Cmd> children = commands.get( cmd );
        if ( children == null )
            commands.put( cmd, children = new ArrayList<Cmd>() );
        return children;
    }

    private final BaseCmd baseCmd = new BaseCmd();

    private CmdHandler() {
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        if ( !command.getName().equalsIgnoreCase( "bomberman" ) )
            return false;

        // sort out passed arguments
        List<String> arguments = new ArrayList<String>();
        List<String> options = new ArrayList<String>();
        for ( String arg : args ) {
            if ( arg.startsWith( "-" ) )
                options.add( arg.substring( 1 ) );
            else
                arguments.add( arg );
        }

        boolean success = baseCmd.execute( sender, arguments, options );
        if ( !success )
            Chat.send( sender, baseCmd.usage() );

        return true;
    }
}
