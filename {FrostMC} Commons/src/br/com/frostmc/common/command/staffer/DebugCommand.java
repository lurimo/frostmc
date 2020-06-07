package br.com.frostmc.common.command.staffer;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.core.util.Strings;

public class DebugCommand extends BaseCommand {

	public DebugCommand() {
		super("debug", "cheque o plugin", Arrays.asList("statsserver", "plugin", "pluginmanager"));
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
				System.gc();
				double total = Runtime.getRuntime().maxMemory();
				double free = Runtime.getRuntime().freeMemory();
				double used = total - free;
				double divisor = 1.073741824E9D;
				double usedPercentage = used / total * 100.0D;
				DecimalFormat format = new DecimalFormat("#.###");
				player.sendMessage("§f§l§m---------------------------");
				player.sendMessage("§fInformações sobre o servidor:");
				player.sendMessage("§f§l§m---------------------------");
				player.sendMessage("§fQuantidade de plugins: §3" + Bukkit.getPluginManager().getPlugins().length);
				player.sendMessage("§fProcessadores disponíveis: §3" + Runtime.getRuntime().availableProcessors());
				player.sendMessage("§fMemória total: §3" + format.format(total / divisor) + "GB");
				player.sendMessage("§fMemória disponível: §3" + format.format(usedPercentage) + "GB");
				player.sendMessage("§fMemória em uso: §3" + format.format(free / divisor) + "%");
				player.sendMessage("§f§l§m---------------------------");
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				System.gc();
				double total = Runtime.getRuntime().maxMemory();
				double free = Runtime.getRuntime().freeMemory();
				double used = total - free;
				double divisor = 1.073741824E9D;
				double usedPercentage = used / total * 100.0D;
				DecimalFormat format = new DecimalFormat("#.###");
				player.sendMessage("§f§l§m---------------------------");
				player.sendMessage("§fInformações sobre o servidor:");
				player.sendMessage("§f§l§m---------------------------");
				player.sendMessage("§fQuantidade de plugins: §3" + Bukkit.getPluginManager().getPlugins().length);
				player.sendMessage("§fProcessadores disponíveis: §3" + Runtime.getRuntime().availableProcessors());
				player.sendMessage("§fMemória total: §3" + format.format(total / divisor) + "GB");
				player.sendMessage("§fMemória disponível: §3" + format.format(usedPercentage) + "GB");
				player.sendMessage("§fMemória em uso: §3" + format.format(free / divisor) + "%");
				player.sendMessage("§f§l§m---------------------------");
				return true;
			}
		}
		return false;
	}
	
	

}
