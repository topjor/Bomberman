package io.github.mdsimmo.bomberman.messaging;

import io.github.mdsimmo.bomberman.PlayerRep;

import org.bukkit.command.CommandSender;

public enum Text {

	GAME(					"game",								"Game"),
	ARENA(					"arena",							"Arena"),
	
	SPECIFY_GAME (			"join.specify-game",				"You must specify a game to join" ),
	ALREADY_PLAYING (		"join.already-playing",				"You're already part of game {1}" ),
	GAME_FULL (				"join.game-full",					"Game {1} is full" ),
	TOO_POOR (				"join.poor-man",					"You need {2} to join {1}" ),
	PLAYER_JOINED (			"join.player-joined",				"{2} joined game {1}" ),
	
	EDIT_BUILD_DENIED(		"editmode.builddenied",				"Cannot build outside while in editmode"),
	EDIT_DESTROY_DENIED(	"editmode.destroydenied",			"Cannot destroy blocks outside while in editmode"),
	
	TELEPORT_DENIED(		"game-play.teleport-denied",		"{RED|Cannot teleport while part of a game}"),
	HIT_OPPONENT( 			"game-play.hit-opponent", 			"You hit {2}"),
	HIT_SUICIDE( 			"game-play.hit-suicide", 			"You hit yourself"),
	HIT_BY(					"game-play.hit-by",					"You were hit by {2}"),
	KILL_OPPONENT(			"game-play.kill-opponent", 			"{YELLOW|You killed {2}}"),
	KILL_SUICIDE(			"game-play.kill-suicide",			"{RED|You killed yourself!}"),
	KILLED_BY(				"game-play.killed-by",				"{RED|Killed by {2}}"),
	PLAYER_KILLED_PLAYERS(	"game-play.player-killed.players",	"{2} is out!"),
	PLAYER_KILLED_OBSERVERS("game-play.player-killed.observers","{2} died in game {1}!"),
	PLAYER_KILLED_ALL(		"game-play.player-killed.all", 		""),
	PLAYER_LEFT_PLAYERS(	"game-play.player-left.players",	"{2} left!"),
	PLAYER_LEFT_OBSERVERS(	"game-play.player-left.observers",	"{2} left game {1}!"),
	PLAYER_LEFT_ALL(		"game-play.player-left.all", 		""),
	NO_REGEN(				"game-play.no-regen",				"No regen in sudden death!"),
	GAME_STARTING_PLAYERS(	"game-play.starting.players",		"Game {1} is starting soon"),
	GAME_STARTING_OBSERVERS("game-play.starting.observers",		"Game {1} is starting soon. Type {aqua|/bm game join {1}} to play!"),
	GAME_STARTING_ALL(		"game-play.starting.all",			"Game {1} is starting soon. Type {aqua|/bm game join {1}} to play!"),
	GAME_COUNT_PLAYERS(		"game-play.count.players",			"Game starting in {2} seconds..."),
	GAME_COUNT_OBSERVERS(	"game-play.count.observers",		"Game starting in {2} seconds..."),
	GAME_COUNT_ALL(			"game-play.count.all",				""),
	GAME_STARTED_PLAYERS(	"game-play.started.players",		"{YELLOW|Game started!}"),
	GAME_STARTED_OBSERVERS(	"game-play.started.observers",		"Game {1} started!"),
	GAME_STARTED_ALL(		"game-play.started.all",			"Game {1} started!"),
	COUNT_STOPPED_PLAYERS(	"game-play.stopped-count.players",	"Not enough players remaining. The countdown timer has been stopped"),
	COUNT_STOPPED_OBSERVERS("game-play.stopped-count.observers",""),
	COUNT_STOPPED_ALL(		"game-play.stopped-count.all",		""),
	GAME_OVER_PLAYERS(		"game-play.game-over.players",		"{YELLOW|The game is over!}"),
	GAME_OVER_OBSERVERS(	"game-play.game-over.observers",	"Game {1} is over!"),
	GAME_OVER_ALL(			"game-play.game-over.all",			""),
	
	SUDDENDEATH_COUNT_P(	"suddendeath.count.players",		"Sudden death in {2} seconds..."),
	SUDDENDEATH_COUNT_O(	"suddendeath.count.observers",		""),
	SUDDENDEATH_COUNT_A(	"suddendeath.count.all",			""),
	SUDDENDEATH_P(			"suddendeath.start.players",		"{RED|Sudden death started!}"),
	SUDDENDEATH_O(			"suddendeath.start.observers",		"Sudden death started in {1}"),
	SUDDENDEATH_A(			"suddendeath.start.all",			""),
	TIMEOUT_COUNT_P(		"timeout.count.players",			"Time out in {2} seconds..."),
	TIMEOUT_COUNT_O(		"timeout.count.observers",			""),
	TIMEOUT_COUNT_A(		"timeout.count.all",				""),
	TIMEOUT_P(				"timeout.start.players",			"Game timed out!"),
	TIMEOUT_O(				"timeout.start.observers",			"Game {1} timed out!"),
	TIMEOUT_A(				"timeout.start.all",				""),
	
	SCORE_DISPLAY(			"score.display",					" {2}: {3} lives, {4} kills and {5} deaths"),
	SCORE_ANNOUNCE(			"score.anounce",					"The winners are:"),
	SCORE_SEE_SCORES(		"score.see-score",					"Type {AQUA|\bm game scores {1}} to see scores"),
			
	HELP(					"command.titles.help",				"Help: /{1}"),
	LIST(					"command.titles.list",				"List: {1}"),
	INFO(					"command.titles.info",				"Info: {1}"),
	DESCTIPTION(			"command.titles.description",		"Description"),
	USAGE(					"command.titles.usage",				"Usage"),
	EXTRA(					"command.titles.extra",				"Extra"),
	COMMANDS(				"command.titles.commands",			"Commands"),
	EXAMPLE(				"command.titles.example",			"Example"),
	
	DENY_PERMISSION(		"command.deny-permission",			"{RED|You do not have permission to use /{2}!}"),
	INCORRECT_USAGE(		"command.incorrect-usage",			"{RED|Incorrect usage: {3} {4}}"),
	UNKNOWN_COMMAND(		"command.unknown-command",			"{RED|Unknown command: {3}}"),
	MUST_BE_PLAYER(			"command.must-be-player",			"You must be a player"),
	TRUE(					"command.true",						"true"),
	FALSE(					"command.false",					"false"),
	INVALID_NUMBER(			"command.invalid-number",			"{3} is not a valid number"),
	INVALID_PLAYER(			"command.invalid-player",			"Cannot find {3}"),
	INVALID_MATERIAL(		"command.invalid-matierial",		"Unknown material {3}"),
	INVALID_ARENA(			"command.invalid-arena",			"Arena {3} does not exist"),
	INVALID_GAME(			"command.invalid-game",				"Game {3} not found"),
	
	BOMBERMAN_NAME(			"command.bomberman.name", 			"bm"),
	BOMBERMAN_DESCRIPTION(	"command.bomberman.description", 	"Main command for Bomberman - contains all sub-commands"),
	
	HELP_NAME(				"command.help.name",				"help"),
	HELP_DESCRIPTION(		"command.help.descripion",			"Help for the selected command"),
	HELP_USAGE(				"command.help.usage",				"/{2}<command>"),
	HELP_EXAMPLE(			"command.help.example",				"/{2} game set fare"),
	HELP_EXTRA(				"command.help.extra",				""),
	
	ARENA_NAME(				"command.arena.name",				"arena"),
	ARENA_DESCRIPTION(		"command.arena.description",		"Arena management commands"),
	
	ARENA_LIST_NAME(		"command.arenalist.name",			"list"),
	ARENA_LIST_DESCRIPTION(	"command.arenalist.description",	"List available arena types"),
	ARENA_LIST_USAGE(		"command.arenalist.usage",			"/{2}"),
	ARENA_LIST_EXAMPLE(		"command.arenalist.example",		""),
	ARENA_LIST_EXTRA(		"command.arenalist.extra",			""),
	
	ARENA_CREATE_NAME(		"command.arenacreate.name",			"create"),
	ARENA_CREATE_DESCRIPTION("command.arenacreate.description",	"Create a new arena type for games to use"),
	ARENA_CREATE_USAGE(		"command.arenacreate.usage",		"/{2} <arena>"),
	ARENA_CREATE_EXAMPLE(	"command.arenacreate.example",		"/{2} redPuddingArena"),
	ARENA_CREATE_EXTRA(		"command.arenacreate.extra",		"Look at the arena when using. Natural blocks are ignored when detecting structures"),
	ARENA_CREATED(			"command.areacreate.success",		"Arena created"),
	
	DELETE_NAME(			"command.arenadelete.name",			"delete"),
	DELETE_DESCRIPTION(		"command.arenadelete.description",	"Deletes an arena permanently"),
	DELETE_USAGE(			"command.arenadelete.usage",		"/{2} <arena>"),
	DELETE_EXAMPLE(			"command.arenadelete.example",		"/{2} {3}"),
	DELETE_EXTRA(			"command.arenadelete.extra",		"Only works if arena is not used by a game"),
	DELETE_SUCCESSFUL(		"command.arenadelete.success",		"Arena {3} successfully deleted"),
	DELETE_ARENA_USED(		"command.arenadelete.arena-used",	"Cannot delete arena {3} since it is being used by game {4}"),
	DELETE_TROUBLE(			"command.arenadelete.trouble",		"Trouble deleting file {3}"),
	
	EDIT_NAME(				"command.arenaedit.name",			"edit"),
	EDIT_DESCRIPTION(		"command.arenaedit.description",	"Edit a game's arena"),
	EDIT_USAGE(				"command.arenaedit.usage",			"/{2} <arena> <save|discard|ignore> (leave blank to start edit mode)"),
	EDIT_EXAMPLE(			"command.arenaedit.example",		"/{2} {3}"),
	EDIT_EXTRA(				"command.arenaedit.extra",			"Editting a game's arena effects {RED|all} games using the same arena"),
	EDIT_SAVE(				"command.arenaedit.save",			"save"),
	EDIT_DISCARD(			"command.arenaedit.discard",		"discard"),
	EDIT_IGNORE(			"command.arenaedit.ignore",			"ignore"),
	EDIT_STARTED(			"command.arenaedit.started",		"Editting arena {4} in game {3}"),
	EDIT_ALREADY_STARTED(	"command.arenaedit.already-started","You're already editting {3}"),
	EDIT_CANT_START(		"command.arenaedit.cant-start",		"Couldn't start edit mode in game {3}"),
	EDIT_CHANGES_SAVED(		"command.arenaedit.changes-saved",	"Changes saved"),
	EDIT_PROMPT_START(		"command.arenaedit.prompt-start", 	"Edit mode needs to be started first"),
	EDIT_CANGES_REMOVED(	"command.arenaedit.changes-removed","Changes removed"),
	EDIT_MODE_QUIT(			"command.arenaedit.changes-ignored","Edit mode quit"),
	
	FORCE_NAME(				"command.force.name",				"force"),
	FORCE_DESCRIPTION(		"command.force.description",		"Force actions on a game"),
	
	RESET_NAME(				"command.reset.name",				"reset"),
	RESET_DESCRIPTION(		"command.reset.description",		"Forcibly reset a game to its starting point"),
	RESET_USAGE(			"command.reset.usage",				"/{2} <game>"),
	RESET_EXAMPLE(			"command.reset.example",			"/{2} {3}"),
	RESET_EXTRA(			"command.reset.extra",				""),
	RESET_SUCCESS(			"command.reset.success",			"Game {3} reset"),
	RESET_SUCCESS_P(		"command.reset.success-players",	"Game {3} resetting"),
	
	START_NAME(				"command.start.name",				"start"),
	START_DESCRIPTION(		"command.start.description",		"Forcibly start a game"),
	START_USAGE(			"command.start.usage",				"/{2} <game>"),
	START_EXAMPLE(			"command.start.example",			"/{2} {3}"),
	START_EXTRA(			"command.start.extra",				""),
	GAME_ALREADY_STARTED(	"command.start.already-started",	"Game {3} already started"),
	GAME_START_SUCCESS(		"command.start.success",			"Game {3} starting..."),
	GAME_MORE_PLAYERS(		"command.start.more-players",		"There needs to be {4} players"),
	
	STOP_NAME(				"command.stop.name",				"stop"),
	STOP_DESCRIPTION(		"command.stop.description",			"Forcibly stop a game"),
	STOP_USAGE(				"command.stop.usage",				"/{2} <game>"),
	STOP_EXAMPLE(			"command.stop.example",				"/{2} {3}"),
	STOP_EXTRA(				"command.stop.extra",				""),
	STOP_NOT_STARTED(		"command.stop.not-started",			"Game {3} hasn't started"),
	STOP_SUCCESS(			"command.stop.success",				"Game {3} stopped"),
	
	SETARENA_NAME(			"command.setarena.name",			"arena"),
	SETARENA_DESCRIPTION(	"command.setarena.description",		"Change a game's arena"),
	SETARENA_USAGE(			"command.setarena.usage",			"/{2} <game> <arena>"),
	SETARENA_EXAMPLE(		"command.setarena.example",			"/{2} {3} {4}"),
	SETARENA_EXTRA(			"command.setarena.extra",			""),
	SETARENA_GIP(			"command.setarena.game-in-progress","Game {3} in progress. Cannot change arena"),
	SETARENA_SUCCESS(		"command.setarena.success",			"Game {3} arena's changed"),
	
	AUTOSTART_NAME(			"command.autostart.name",			"autostart"),
	AUTOSTART_DESCRIPTION(	"command.autostart.description",	"Set if the game should autostart"),
	AUTOSTART_USAGE(		"command.autostart.usage",			"/{2} <game> <true|false>"),
	AUTOSTART_EXAMPLE(		"command.autostart.example",		"/{2} {3} true"),
	AUTOSTART_EXTRA(		"command.autostart.extra",			""),
	AUTOSTART_ENABLED(		"command.autostart.enabled",		"Autostart enabled in game {3}"),
	AUTOSTART_DISABLED(		"command.autostart.disabled",		"Autostart disabled in game {3}"),
	
	STARTDELAY_NAME(		"command.start-delay.name",			"autostartdelay"),
	STARTDELAY_DESCRIPTION(	"command.start-delay.description",	"Change the delay on a game's automated start"),
	STARTDELAY_USAGE(		"command.start-delay.usage",		"/{2} <game> <seconds>"),
	STARTDELAY_EXAMPLE(		"command.start-delay.example",		"/{2} {3} 60"),
	STARTDELAY_EXTRA(		"command.start-delay.extra",		""),
	STARTDELAY_SET(			"command.start-delay.set",			"Autostart delay set to {4} seconds"),
	
	BOMBS_NAME(				"command.bombs.name",				"bombs"),
	BOMBS_DESCRIPTION(		"command.bombs.description",		"Sets players' initial bombs"),
	BOMBS_USAGE(			"command.bombs.usage",				"/{2} <game> <number>"),
	BOMBS_EXAMPLE(			"command.bombs.example",			"/{2} {3} 3"),
	BOMBS_EXTRA(			"command.bombs.extra",				""),
	BOMBS_SET(				"command.bombs.set",				"Bombs set to {4}"),
	
	FARE_NAME(				"command.fare.name",				"fare"),
	FARE_DESCRIPTION(		"command.fare.description",			"Change a game's fare"),
	FARE_USAGE(				"command.fare.usage",				"/{2} <game> <material|none> [amount]"),
	FARE_EXAMPLE(			"command.fare.example",				"/{2} {3} gold_ingot 5"),
	FARE_EXTRA(				"command.fare.extra",				""),
	FARE_NONE(				"command.fare.none",				"none"),
	FARE_REMOVED(			"command.fare.removed",				"Fare removed"),	
	FARE_SET(				"command.fare.set",					"Fare set to {4}"),
	
	HANDICAP_NAME(			"command.handicap.name",			"handicap"),
	HANDICAP_DESCRIPTION(	"command.handicap.description",		"Gives a hanicap/advantage to a player"),
	HANDICAP_USAGE(			"command.handicap.usage",			"/{2} <game> <player> <level>"),
	HANDICAP_EXAMPLE(		"command.handicap.example",			"/{2} {3} {0} -2"),
	HANDICAP_EXTRA(			"command.handicap.extra",			"Negitive levels give an advantage"),
	HANDICAP_HANDYCAPPED(	"command.handicap.handicapped",		"Handicap set to {5}"),
	HANDICAP_REMOVED(		"command.handicap.removed",			"Handicap removed"),
	HANDICAP_ADVANTAGED(	"command.handicap.advantaged",		"Advantage set to {5}"),
	
	LIVES_NAME(				"command.lives.name",				"lives"),
	LIVES_DESCRIPTION(		"command.lives.description",		"Sets players' initial lives"),
	LIVES_USAGE(			"command.lives.usage",				"/{2} <game> <number>"),
	LIVES_EXAMPLE(			"command.lives.example",			"/{2} {3} 3"),
	LIVES_EXTRA(			"command.lives.extra",				""),
	LIVES_SET(				"command.lives.set",				"Lives set to {4}"),
	
	MINPLAYERS_NAME(		"command.minplayers.name",			"minplayers"),
	MINPLAYERS_DESCRIPTION(	"command.minplayers.description",	"Sets the min players before game can start"),
	MINPLAYERS_USAGE(		"command.minplayers.usage",			"/{2} <game> <number>"),
	MINPLAYERS_EXAMPLE(		"command.minplayers.example",		"/{2} {3} 4"),
	MINPLAYERS_EXTRA(		"command.minplayers.extra",			""),
	MINPLAYERS_SET(			"command.minplayers.set",			"Min players set to {4}"),
	
	POWER_NAME(				"command.power.name",				"power"),
	POWER_DESCRIPTION(		"command.power.description",		"Sets players' initial power"),
	POWERS_USAGE(			"command.power.usage",				"/{2} <game> <number>"),
	POWER_EXAMPLE(			"command.power.example",			"/{2} {3} 3"),
	POWER_EXTRA(			"command.power.extra",				""),
	POWER_SET(				"command.power.set",				"Power set to {4}"),
	
	PRIZE_NAME(				"command.prize.name",				"prize"),
	PRIZE_DESCRIPTION(		"command.prize.description",		"Change a game's prize"),
	PRIZE_USAGE(			"command.prize.usage",				"/{2} <game> <material|pot|none> [amount]"),
	PRIZE_EXAMPLE(			"command.prize.example",			"/{2} {3} 3"),
	PRIZE_EXTRA(			"command.prize.extra",				""),
	PRIZE_POT(				"command.prize.pot",				"pot"),
	PRIZE_NONE(				"command.prize.none",				"none"),
	PRIZE_SET(				"command.prize.set",				"Prize set to {4}"),
	PRIZE_REMOVED(			"command.prize.removed",			"Prize removed"),
	PRIZE_POT_SET(			"command.prize.pot-set",			"Pot set"),
	
	SET_NAME(				"command.set.name",					"set"),
	SET_DESCRIPTION(		"command.set.description",			"Change a game's settings"),

	SUDDENDEATH_NAME(		"command.suddendeath.name",			"suddendeath"),
	SUDDENDEATH_DESCRIPTION("command.suddendeath.description",	"Sets when sudden death should happen"),
	SUDDENDEATH_USAGE(		"command.suddendeath.usage",		"/{2} <game> <seconds>"),
	SUDDENDEATH_EXAMPLE(	"command.suddendeath.example",		"/{2} {3} 60"),
	SUDDENDEATH_EXTRA(		"command.suddendeath.extra",		""),
	SUDDENDEATH_OFF(		"command.suddendeath.off",			"off"),
	SUDDENDEATH_SET(		"command.suddendeath.set",			"Sudden death removed from {3}"),
	SUDDENDEATH_REMOVED(	"command.suddendeath.removed",		"Sudden death set to {4} seconds"),
	
	TIMEOUT_NAME(			"command.timeout.name",				"timeout"),
	TIMEOUT_DESCRIPTION(	"command.timeout.description",		"Sets a maximum game time"),
	TIMEOUT_USAGE(			"command.timeout.usage",			"/{2} <game> <seconds>"),
	TIMEOUT_EXAMPLE(		"command.timeout.example",			"/{2} {3} 120"),
	TIMEOUT_EXTRA(			"command.timeout.extra",			""),
	TIMEOUT_OFF(			"command.timeout.off",				"off"),
	TIMEOUT_SET(			"command.timeout.set",				"Time out removed for game {3}"),
	TIMEOUT_REMOVED(		"command.timeout.removed",			"Time out set to {4} seconds"),
	
	CONVERT_NAME(			"command.convert.name",				"convert"),
	CONVERT_DESCRIPTION(	"command.convert.description",		"Converts the structure under the cursor into a Bomberman game"),
	CONVERT_USAGE(			"command.convert.usage",			"/{2} <game>"),
	CONVERT_EXAMPLE(		"command.convert.example",			"/{2} Bananas"),
	CONVERT_EXTRA(			"command.convert.extra",			"Natural blocks are ignored when detecting structures"),
	CONVERT_GAME_EXISTS(	"command.convert.game-exists",		"Game {3} already exists"),
	CONVERT_SUCCESS(		"command.convert.success",			"Game {3} created"),
	
	CREATE_NAME(			"command.create.name",				"create"),
	CREATE_DESCRIPTION(		"command.create.description",		"Builds a Bomberman game"),
	CREATE_USAGE(			"command.create.usage",				"/{2} <game> [arena]"),
	CREATE_EXAMPLE(			"command.create.example",			"/{2} Bananas {3}"),
	CREATE_EXTRA(			"command.create.extra",				""),
	CREATE_GAME_EXISTS(		"command.create.game-exists",		"Game {3} already exists"),
	CREATE_SUCCESS(			"command.create.success",			"Game {3} created"),
	CREATE_DEFAULTMISSING(	"command.create.default-missing",	"The default arena {3} is missing!"),
	
	DESTROY_NAME(			"command.destroy.name",				"destroy"),
	DESTROY_DESCRIPTION(	"command.destroy.description",		"Destroy a game and revert the land to its previous state"),
	DESTROY_USAGE(			"command.destroy.usage",			"/{2} <game>"),
	DESTROY_EXAMPLE(		"command.destroy.example",			"/{2} {3}"),
	DESTROY_EXTRA(			"command.destroy.extra",			""),
	DESTROY_SUCCESS(		"command.destroy.success",			"Game {3} destroyed"),
	
	GAME_NAME(				"command.game.name",				"game"),
	GAME_DESCRIPTION(		"command.game.description",			"All commands related to game configuation"),

	GAMELIST_NAME(			"command.gamelist.name",			"list"),
	GAMELIST_DESCRIPTION(	"command.gamelist.description",		"Shows all existing games"),
	GAMELIST_USAGE(			"command.gamelist.usage",			"/{2}"),
	GAMELIST_EXAMPLE(		"command.gamelist.example",			"/{2}"),
	GAMELIST_EXTRA(			"command.gamelist.extra",			""),
	GAMELIST_NO_GAMES(		"command.gamelist.no-games",		"No games"),
	GAMELIST_PLAYING(		"command.gamelist.playing",			"Playing"),
	GAMELIST_WAITING(		"command.gamelist.waiting",			"Waiting"),
	GAMELIST_GAME_FORMAT(	"command.gamelist.game-format",		""),
	
	IGNORE_NAME(			"command.ignore.name",				"ignore"),
	IGNORE_DESCRIPTION(		"command.ignore.description",		"Ignore all further messages from a game"),
	IGNORE_USAGE(			"command.ignore.usage",				"/{2} <game>"),
	IGNORE_EXAMPLE(			"command.ignore.example",			"/{2} {3}"),
	IGNORE_EXTRA(			"command.ignore.extra",				""),
	IGNORE_SUCCESS(			"command.ignore.success",			"Game {3} ignored"),
	IGNORE_NOT_WATCHED(		"command.ignore.not-watched",		"You where not observing {3}"),
	
	INFO_NAME(				"command.info.name",				"info"),
	INFO_DESCRIPTION(		"command.info.description",			"Show information about a game"),
	INFO_USAGE(				"command.info.usage",				"/{2} <game>"),
	INFO_EXAMPLE(			"command.info.example",				"/{2} {3}"),
	INFO_EXTRA(				"command.info.extra",				""),
	INFO_STATUS(			"command.info.status",				"Status"),
	INFO_IN_PROGRESS(		"command.info.in-progress",			"In progress"),
	INFO_WAITING(			"command.info.waiting",				"Waiting"),
	INFO_PLAYERS(			"command.info.players",				"Players"),
	INFO_MIN_PLAYERS(		"command.info.min-players",			"Min players"),
	INFO_MAX_PLAYERS(		"command.info.max-players",			"Max players"),
	INFO_INIT_BOMBS(		"command.info.initial-bombs",		"Init bombs"),
	INFO_INIT_LIVES(		"command.info.initial-lives",		"Init lives"),
	INFO_INIT_POWER(		"command.info.initial-power",		"Init power"),
	INFO_FARE(				"command.info.fare",				"Entry fee"),
	INFO_NO_FARE(			"command.info.no-fare",				"No fee"),
	INFO_PRIZE(				"command.info.prize",				"Prize"),
	INFO_NO_PRIZE(			"command.info.no-prize",			"No prize"),
	INFO_POT_AT(			"command.info.pot-at",				"Pot at {1}"),
	INFO_SUDDENDEATH(		"command.info.suddendeath",			"Sudden death"),
	INFO_OFF(				"command.info.sd-off",				"Off"),
	INFO_TIME(				"command.info.sd-time",				"{1} seconds"),
	INFO_TIMEOUT(			"command.info.timeout",				"Timeout"),
	
	JOIN_NAME(				"command.join.name",				"join"),
	JOIN_DESCRIPTION(		"command.join.description",			"Join a game"),
	JOIN_USAGE(				"command.join.usage",				"/{2} <game>"),
	JOIN_EXAMPLE(			"command.join.example",				"/{2} {3}"),
	JOIN_EXTRA(				"command.join.extra",				""),
	JOIN_GAME_STARTED(		"command.join.game-started",		"Game has already started"),
	
	LEAVE_NAME(				"command.leave.name",				"leave"),
	LEAVE_DESCRIPTION(		"command.leave.description",		"Leave the game"),
	LEAVE_USAGE(			"command.leave.usage",				"/{2}"),
	LEAVE_EXAMPLE(			"command.leave.example",			"/{2}"),
	LEAVE_EXTRA(			"command.leave.extra",				""),
	LEAVE_NOT_JOINED(		"command.leave.not-joined",			"You're not part of a game"),
	LEAVE_FAILED(			"command.leave.failed",				"Couldn't remove you"),
	
	PROTECT_NAME(			"command.protect.name",				"protect"),
	PROTECT_DESCRIPTION(	"command.protect.description",		"Protects the arena from griefing"),
	PROTECT_USAGE(			"command.protect.usage",			"/{2} <game> [protection-type] <on|off>"),
	PROTECT_EXAMPLE(		"command.protect.example",			"/{2} {3} explosion true"),
	PROTECT_EXTRA(			"command.protect.extra",			""),
	PROTECT_NOT_JOINED(		"command.protect.not-joined",		"You're not part of a game"),
	PROTECT_FAILED(			"command.protect.failed",			"Couldn't remove you"),
	PROTECT_ENABLED(		"command.protect.enabled",			"enabled"),
	PROTECT_PLACEING(		"command.protect.placing", 			"placing"),
	PROTECT_PVP(			"command.protect.pvp",				"pvp"),
	PROTECT_DESTROYING(		"command.protect.destroying",		"destroy"),
	PROTECT_DAMAGE( 		"command.protect.damage",			"damage"),
	PROTECT_FIRE( 			"command.protect.fire",				"fire"),
	PROTECT_EXPLOSIONS( 	"command.protect.explosion",		"explosion"),
	PROTECT_ON(				"command.protect.on",				"Game {3} enabled {4} protection"),
	PROTECT_OFF(			"command.protect.off",				"Game {3} removed {4} protection"),
	
	SCORES_NAME(			"command.scores.name",				"scores"),
	SCORES_DESCRIPTION(		"command.scores.description",		"Displays the games scores"),
	SCORES_USAGE(			"command.scores.usage",				"/{2} <game>"),
	SCORES_EXAMPLE(			"command.scores.example",			"/{2} {3}"),
	SCORES_EXTRA(			"command.scores.extra",				""),
	
	END("","");
	
	private final String path;
	private final String message;
	
	Text(String path, String message) {
		this.path = path;
		this.message = message;
	}
	
	public String getDefault() {
		return message;
	}
	
	public String getPath() {
		return path;
	}
	
	public Message getMessage(Language lang, CommandSender sender, Object ... objs) {
		if (lang == null)
			return new Message(sender, this.message, objs);
		else
			return new Message(sender, lang.translate(this), objs);
	}
	
	public Message getMessage(CommandSender sender, Object ... objs) {
		return getMessage(PlayerRep.getLanguage(sender), sender, objs);
	}
}
