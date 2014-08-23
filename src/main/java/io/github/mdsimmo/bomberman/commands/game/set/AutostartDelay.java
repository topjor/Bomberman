package io.github.mdsimmo.bomberman.commands.game.set;

import io.github.mdsimmo.bomberman.Bomberman;
import io.github.mdsimmo.bomberman.Game;
import io.github.mdsimmo.bomberman.commands.Command;
import io.github.mdsimmo.bomberman.commands.game.GameCommand;

import java.util.List;

import org.bukkit.command.CommandSender;

public class AutostartDelay extends GameCommand {

	public AutostartDelay(Command parent) {
		super(parent);
	}

	@Override
	public String name() {
		return "autostartdelay";
	}

	@Override
	public List<String> shortOptions(CommandSender sender, List<String> args) {
		return null;
	}

	@Override
	public boolean runShort(CommandSender sender, List<String> args, Game game) {
		if (args.size() != 1)
			return false;
		        
        try {
            game.setAutostartDelay(Integer.parseInt(args.get(0)));
            Bomberman.sendMessage(sender, "Autostart delay set to %d", game.getAutostartDelay());
        } catch (NumberFormatException e) {
            Bomberman.sendMessage(sender, "Delay entered is not a valid number");
        }
        return true;
	}

	@Override
	public boolean firstIsGame(List<String> args) {
		return args.size() == 2;
	}
	
	@Override
	public String description() {
		return "Change the delay on a game's automated start";
	}

	@Override
	public String usage(CommandSender sender) {
		return "/" + path() + "<game> <amount>";
	}

	@Override
	public Permission permission() {
		return Permission.GAME_DICTATE;
	}

}
