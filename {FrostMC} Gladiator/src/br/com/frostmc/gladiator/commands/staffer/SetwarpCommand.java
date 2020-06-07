package br.com.frostmc.gladiator.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.gladiator.commands.BaseCommand;
import br.com.frostmc.gladiator.util.warp.WarpsAPI;
import br.com.frostmc.gladiator.util.warp.WarpsAPI.Warps;

public class SetwarpCommand extends BaseCommand {

	public SetwarpCommand() {
		super("setwarp", "set to location", Arrays.asList("warpset"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isAdmin(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lSETWARP §fUtilize o comando: /set (position)");
				return true;
			}
			if (args.length == 1) {
				try {
					Warps warps = Warps.valueOf(args[0].toUpperCase());
					WarpsAPI.setWarpLocation(player, warps);
					player.sendMessage("§a§lSET §fVocê §a§lSETOU §fo spawn da 1v1.");
				} catch (Exception exception) {
					player.sendMessage("§c§lSET §fA warp citada não foi encontrada!");
					return true;
				}
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
