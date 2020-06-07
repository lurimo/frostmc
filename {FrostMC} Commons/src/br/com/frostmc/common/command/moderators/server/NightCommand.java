package br.com.frostmc.common.command.moderators.server;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class NightCommand extends BaseCommand {
	
	public NightCommand() {
		super("noite", "deixar o servidor de noite", Arrays.asList("night"));
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
					world.setTime(19000);
				}
				sendWarning(player.getName() + " alterou o tempo do servidor para noite");
				player.sendMessage("§8[§6§lSERVER§8] §fVocê alterou o tempo do servidor para: §dnoite");
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				for (World world : Bukkit.getWorlds()) {
					world.setTime(19000);
				}
				sendWarning(player.getName() + " alterou o tempo do servidor para noite");
				player.sendMessage("§8[§6§lSERVER§8] §fVocê alterou o tempo do servidor para: §dnoite");
				return true;
			}
		}
		return false;
	}

}
