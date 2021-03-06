package io.github.mdsimmo.bomberman.commands.language;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.github.mdsimmo.bomberman.commands.Cmd;
import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Message;
import io.github.mdsimmo.bomberman.messaging.Text;

public class Reload extends Cmd {

	public Reload( Cmd parent ) {
		super( parent );
	}

	@Override
	public Message name( CommandSender sender ) {
		return getMessage( Text.LANG_RELOAD_NAME, sender );
	}

	@Override
	public List<String> options( CommandSender sender, List<String> args ) {
		return null;
	}

	@Override
	public boolean run( CommandSender sender, List<String> args ) {
		io.github.mdsimmo.bomberman.messaging.Language.reload();		
		Chat.sendMessage( getMessage( Text.LANG_RELOAD_SUCCESS, sender ) );
		return true;
	}

	@Override
	public Message extra( CommandSender sender ) {
		return getMessage( Text.LANG_RELOAD_EXTRA, sender );
	}

	@Override
	public Message example( CommandSender sender ) {
		return getMessage( Text.LANG_RELOAD_EXAMPLE, sender );
	}

	@Override
	public Message description( CommandSender sender ) {
		return getMessage( Text.LANG_RELOAD_DESCRIPTION, sender );
	}

	@Override
	public Message usage( CommandSender sender ) {
		return getMessage( Text.LANG_RELOAD_USAGE, sender );
	}


	@Override
	public Permission permission() {
		return Permission.OVERLORD;
	}
	
	

	
	

}
