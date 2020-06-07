package br.com.frostmc.common.command.we;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;

public class PosOneCommand extends BaseCommand {

	public PosOneCommand() {
		super("posone", "Select the first location.", Arrays.asList("pos1"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);

			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				return true;
			}

			if (args.length != 0) {
				commandSender.sendMessage("§3§lWORLDEDIT §fUtilize o comando: /pos1");
				return false;
			}

			Location location = player.getLocation();
			BukkitMain.getWorldEdit().setFirstPosition(player.getUniqueId(), location);
			player.sendMessage("§a§lWORLDEDIT §fA primeira localização foi setada (" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ").");
		} else {
			return true;
		}
		return true;
	}

}
