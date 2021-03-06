package io.github.mdsimmo.bomberman.commands.game.set;

import io.github.mdsimmo.bomberman.Game;
import io.github.mdsimmo.bomberman.commands.Cmd;
import io.github.mdsimmo.bomberman.commands.GameCommand;
import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Phrase;
import io.github.mdsimmo.bomberman.messaging.Text;

import java.util.List;

import org.bukkit.command.CommandSender;

public class Bombs extends GameCommand {

	public Bombs(Cmd parent) {
		super(parent);
	}

	@Override
	public Phrase nameShort() {
		return Text.BOMBS_NAME;
	}

	@Override
	public List<String> shortOptions(CommandSender sender, List<String> args) {
		return null;
	}

	@Override
	public boolean runShort(CommandSender sender, List<String> args, Game game) {
		if (args.size() != 1)
			return false;
				
		int amount;
		try {
			amount = Integer.parseInt(args.get(0));
		} catch (Exception e) {
			Chat.sendMessage(getMessage(Text.INVALID_NUMBER, sender).put( "number", args.get(0)));
			return true;
		}
		game.setBombs(amount);	
		Chat.sendMessage(getMessage(Text.BOMBS_SET, sender).put( "game", game) );
		return true;
	}

	@Override
	public Permission permission() {
		return Permission.GAME_DICTATE;
	}

	@Override
	public Phrase extraShort() {
		return Text.BOMBS_EXTRA;
	}

	@Override
	public Phrase exampleShort() {
		return Text.BOMBS_EXAMPLE;
	}

	@Override
	public Phrase descriptionShort() {
		return Text.BOMBS_DESCRIPTION;
	}

	@Override
	public Phrase usageShort() {
		return Text.BOMBS_USAGE;
	}

}
