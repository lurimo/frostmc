package br.com.frostmc.common.command.moderators.server;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class DayCommand extends BaseCommand {

	public DayCommand() {
		super("dia", "deixar o servidor de noite", Arrays.asList("day"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = (Player) commandSender;
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				for (World world : Bukkit.getWorlds()) {
					world.setTime(0);
				}
				sendWarning(player.getName() + " alterou o tempo do servidor para dia");
				player.sendMessage("�8[�6�lSERVER�8] �fVoc� alterou o tempo do servidor para: �adia");
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				for (World world : Bukkit.getWorlds()) {
					world.setTime(0);
				}
				sendWarning(player.getName() + " alterou o tempo do servidor para dia");
				player.sendMessage("�8[�6�lSERVER�8] �fVoc� alterou o tempo do servidor para: �adia");
				return true;
			}
		}
		return false;
	}

}
