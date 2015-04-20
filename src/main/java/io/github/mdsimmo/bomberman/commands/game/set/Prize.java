package io.github.mdsimmo.bomberman.commands.game.set;

import io.github.mdsimmo.bomberman.Game;
import io.github.mdsimmo.bomberman.commands.Cmd;
import io.github.mdsimmo.bomberman.commands.GameCommand;
import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Phrase;
import io.github.mdsimmo.bomberman.messaging.Text;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class Prize extends GameCommand {

	public Prize(Cmd parent) {
		super(parent);
	}

	@Override
	public Phrase nameShort() {
		return Text.PRIZE_NAME;
	}

	@Override
	public List<String> shortOptions(CommandSender sender, List<String> args) {
		if (args.size() == 1 ) {
			List<String> options = new ArrayList<>();
			options.add(Text.PRIZE_NONE.getMessage(sender).toString());
			options.add(Text.PRIZE_POT.getMessage(sender).toString());
			for (Material m : Material.values())
				options.add(m.toString());
			return options;
		} else
			return null;
	}

	@Override
	public boolean runShort(CommandSender sender, List<String> args, Game game) {
		if (args.size() < 1 || args.size() > 2)
			return false;

		if (args.size() == 1) {
			if (args.get(0).equalsIgnoreCase(Text.PRIZE_NONE.getMessage(sender).toString())) {
				game.setFare(null);
			} else if (args.get(0).equalsIgnoreCase(Text.PRIZE_POT.getMessage(sender).toString())) {
				game.setPot(true);
			} else {
				return false;
			}
		} else if (args.size() == 2) {
			Material m = Material.getMaterial(args.get(0).toUpperCase());
			if (m == null) {
				Chat.sendMessage(getMessage(Text.INVALID_MATERIAL, sender).put( "number", args.get(0)));
				return true;
			}
			try {
				int amount = Integer.parseInt(args.get(1));
				game.setPrize(new ItemStack(m, amount));
			} catch (Exception e) {
				Chat.sendMessage(getMessage(Text.INVALID_NUMBER, sender).put( "number", args.get(1)));
				return true;
			}
		} else {
			return false;
		}
		Chat.sendMessage(getMessage(Text.PRIZE_SET, sender).put( "game", game));
		return true;
	}
	
	@Override
	public Permission permission() {
		return Permission.GAME_DICTATE;
	}

	@Override
	public Phrase extraShort() {		
		return Text.PRIZE_EXTRA;
	}

	@Override
	public Phrase exampleShort() {
		return Text.PRIZE_EXAMPLE;
	}

	@Override
	public Phrase descriptionShort() {
		return Text.PRIZE_DESCRIPTION;
	}

	@Override
	public Phrase usageShort() {
		return Text.PRIZE_USAGE;
	}
}
