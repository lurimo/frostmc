package br.com.frostmc.common.command.moderators.moderate;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class GamemodeCommand extends BaseCommand {

	public GamemodeCommand() {
		super("gamemode", "Change your gamemode.", Arrays.asList("gm"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);

			if (!BukkitMain.getPermissions().isMod(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}

			if (args.length == 0) {
				commandSender.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta. Utilize: /gamemode (mode) (player)");
			} else if (args.length == 1) {
				String mode = args[0];

				GameMode gamemode = getGamemode(mode);

				if (gamemode == null) {
					player.sendMessage("§8[§c§lERRO§8] §fO modo inserido é inválido.");
					return false;
				}

				if (player.getGameMode() == gamemode) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê já está no modo §b" + player.getGameMode().name().toLowerCase() + "§f.");
				} else {
					player.setGameMode(gamemode);
					player.sendMessage("§f§8[§b§lGAMEMODE§8] §fVocê alterou o seu modo de jogo para §b" + player.getGameMode().name().toLowerCase() + "§f.");
					sendWarning("O staffer " + player.getName() + " trocou o modo de jogo para " + gamemode.name().toLowerCase() + ".");
				}
				return true;
			} else if (args.length == 2) {
				String mode = args[0];
				Player target = Bukkit.getPlayer(args[1]);

				if (target == null) {
					player.sendMessage("§8[§c§lERRO§8] §fO player em questão não foi encontrado.");
					return true;
				}

				GameMode gamemode = getGamemode(mode);

				if (gamemode == null) {
					player.sendMessage("§8[§c§lERRO§8] §fO modo inserido é inválido.");
					return false;
				}

				target.setGameMode(gamemode);
				player.sendMessage("§f§8[§b§lGAMEMODE§8] §fVocê alterou o modo de jogo de §a" + target.getName() + " §f para §b" + target.getGameMode());
				sendWarning("O staffer " + player.getName() + " alterou o modo de jogo " + gamemode.name().toLowerCase() + " para o player " + target.getName() + ".");
			} else {
				commandSender.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta. Utilize: /gamemode (mode) (player)");
			}
		} else {
			return true;
		}
		return false;
	}

	public GameMode getGamemode(String mode) {
		GameMode gamemode = null;
		if (mode.equalsIgnoreCase("0") || mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("survival")) {
			gamemode = GameMode.SURVIVAL;
		} else if (mode.equalsIgnoreCase("1") || mode.equalsIgnoreCase("c") || mode.equalsIgnoreCase("criativo")) {
			gamemode = GameMode.CREATIVE;
		}
		return gamemode;
	}

}
