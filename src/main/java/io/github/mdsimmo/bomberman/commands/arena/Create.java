package io.github.mdsimmo.bomberman.commands.arena;

import io.github.mdsimmo.bomberman.Board;
import io.github.mdsimmo.bomberman.BoardGenerator;
import io.github.mdsimmo.bomberman.commands.Command;
import io.github.mdsimmo.bomberman.messaging.Chat;
import io.github.mdsimmo.bomberman.messaging.Message;
import io.github.mdsimmo.bomberman.messaging.Text;
import io.github.mdsimmo.bomberman.utils.Box;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create extends Command {

	public Create(Command parent) {
		super(parent);
	}

	public Text name() {
		return Text.ARENA_CREATE_NAME;
	}

	@Override
	public List<String> options(CommandSender sender, List<String> args) {
		if (args.size() == 1)
			return BoardGenerator.allBoards();
		else
			return null;
	}

	@Override
	public boolean run(CommandSender sender, List<String> args) {
		if (args.size() != 1)
            return false;
        if (sender instanceof Player) {
            @SuppressWarnings("deprecation")
			Box box = BoardGenerator.getBoundingStructure(((Player)sender).getTargetBlock(null, 100));
            Board board = BoardGenerator.createArena(args.get(0), box);
            BoardGenerator.saveBoard(board);
            Chat.sendMessage(sender, getMessage(Text.ARENA_CREATED, sender, board));
        }
        return true;
	}

	@Override
	public Permission permission() {
		return Permission.ARENA_EDITING;
	}

	@Override
	public Message description(CommandSender sender, List<String> args) {
		return getMessage(Text.ARENA_CREATE_DESCRIPTION, sender);
	}

	@Override
	public Message usage(CommandSender sender, List<String> args) {
		return getMessage(Text.ARENA_CREATE_USAGE, sender);
	}

	@Override
	public Message extra(CommandSender sender, List<String> args) {
		return getMessage(Text.ARENA_CREATE_EXTRA, sender);
	}

	@Override
	public Message example(CommandSender sender, List<String> args) {
		return getMessage(Text.ARENA_CREATE_EXAMPLE, sender);
	}

}
