package io.github.mdsimmo.bomberman.commands.game.set;

import io.github.mdsimmo.bomberman.Game;
import io.github.mdsimmo.bomberman.PlayerRep;
import io.github.mdsimmo.bomberman.commands.Cmd;
import io.github.mdsimmo.bomberman.commands.GameCommand;
import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Phrase;
import io.github.mdsimmo.bomberman.messaging.Text;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Handicap extends GameCommand {

	public Handicap(Cmd parent) {
		super(parent);
	}

	@Override
	public Phrase nameShort() {
		return Text.HANDICAP_NAME;
	}

	@Override
	public List<String> shortOptions(CommandSender sender, List<String> args) {
		if (args.size() == 1) {
			List<String> options = new ArrayList<>();
			for (Player p : Bukkit.getServer().getOnlinePlayers())
				options.add(p.getName());
			return options;
		} else {
			return null;
		}
		
	}

	@Override
	public boolean runShort(CommandSender sender, List<String> args, Game game) {
		if (args.size() != 2)
			return false;
		
		@SuppressWarnings("deprecation")
		PlayerRep rep = PlayerRep.getPlayerRep(Bukkit.getPlayer(args.get(0)));
		if (rep == null) {
			Chat.sendMessage(getMessage(Text.INVALID_PLAYER, sender).put( "player", args.get(0)));
			return true;
		}
		int handicap = 0;
		try {
			handicap = Integer.parseInt(args.get(1));
		} catch (NumberFormatException e) {
			Chat.sendMessage(getMessage(Text.INVALID_NUMBER, sender).put( "number", args.get(1)));
		}
		game.setHandicap(rep, handicap);
		rep.setActiveGame( game ); //TODO remove this line
		// its needed so that the handicap message gets the correct game
		Chat.sendMessage(getMessage(Text.HANDICAP_SET, sender).put( "game", game).put( "player", rep ));
		
		if ( !rep.isPlaying() )
			return true;
		
		game.initialise(rep);
		
		return true;
	}
	
	@Override
	public Permission permission() {
		return Permission.GAME_DICTATE;
	}

	@Override
	public Phrase extraShort() {
		return Text.HANDICAP_EXTRA;
	}

	@Override
	public Phrase exampleShort() {
		return Text.HANDICAP_EXAMPLE;
	}

	@Override
	public Phrase descriptionShort() {
		return Text.HANDICAP_DESCRIPTION;
	}

	@Override
	public Phrase usageShort() {
		return Text.HANDICAP_USAGE;
	}

}
