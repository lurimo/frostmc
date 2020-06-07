package br.com.frostmc.common.command.we;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;

public class PosTwoCommand extends BaseCommand {

	public PosTwoCommand() {
		super("postwo", "Select the second location.", Arrays.asList("pos2"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);

			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				return true;
			}

			if (args.length != 0) {
				commandSender.sendMessage("§3§lWORLDEDIT §fUtilize o comando: /pos2");
				return false;
			}

			Location location = player.getLocation();
			BukkitMain.getWorldEdit().setSecondPosition(player.getUniqueId(), location);
			player.sendMessage("§a§lWORLDEDIT §fA segunda localização foi setada (" + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + ").");
		} else {
			return true;
		}
		return true;
	}
}
