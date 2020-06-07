package br.com.frostmc.login.commands.staffer;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.login.commands.BaseCommand;
import br.com.frostmc.login.util.warp.WarpsAPI;
import br.com.frostmc.login.util.warp.WarpsAPI.Warps;

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
				player.sendMessage("[§8§c§lADMINISTRACAO§8] §fUtilize o comando: §c/set <spawn>");
				return true;
			}
			if (args.length == 1) {
				try {
					Warps warps = Warps.valueOf(args[0].toUpperCase());
					WarpsAPI.setWarpLocation(player, warps);
					player.sendMessage("[§8§c§lADMINISTRACAO§8] §fVocê setou a warp §cspawn§f com §asucesso§f.");
				} catch (Exception exception) {
					player.sendMessage("[§8§c§lADMINISTRACAO§8] §fA warp citada não foi encontrada.");
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
