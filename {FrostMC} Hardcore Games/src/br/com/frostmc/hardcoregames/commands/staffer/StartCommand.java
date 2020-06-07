package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.util.State;

public class StartCommand extends BaseCommand {

	public StartCommand() {
		super("start", "started the game", Arrays.asList("iniciar"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				if (FrostHG.state != State.INICIANDO) {
					player.sendMessage("§6§lTORNEIO §fA partida precisa está no estágio iniciando.");
					return true;
				}
				FrostHG.getManager().startInvencivel();
				player.sendMessage("§6§lTORNEIO §fVocê §a§lINICOU§f a partida.");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
