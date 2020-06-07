package br.com.frostmc.hardcoregames.commands.moderators;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.commands.BaseCommand;

public class DamageCommand extends BaseCommand implements Listener {

	public DamageCommand() {
		super("dano", "Change the option of damage in the server.", Arrays.asList("damage", "pvp"));
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
				player.sendMessage("§3§lDAMAGE §fUtilize o comando: /dano (global/pvp) (on/off)");
				return true;
			}

			if (args[0].equalsIgnoreCase("global")) {
				if (args[1].equalsIgnoreCase("on")) {
					if (ServerOptions.DAMAGE.isActive()) {
						player.sendMessage("§3§lDAMAGE §fO dano global já está §a§lATIVADO");
						return true;
					}
					ServerOptions.DAMAGE.setActive(true);
					sendWarning(player.getName() + " ativou o dano");
					Bukkit.broadcastMessage("§3§lDAMAGE §fO dano global foi §a§lATIVADO");
					return true;
				} else if (args[1].equalsIgnoreCase("off")) {
					if (!ServerOptions.DAMAGE.isActive()) {
						player.sendMessage("§3§lDAMAGE §fO dano global já está §c§lDESATIVADO");
						return true;
					}
					ServerOptions.DAMAGE.setActive(false);
					sendWarning(player.getName() + " desativou o dano");
					Bukkit.broadcastMessage("§3§lDAMAGE §fO dano global foi §c§lDESATIVADO");
					return true;
				}
				return true;
			} else if (args[0].equalsIgnoreCase("pvp")) {
				if (args[1].equalsIgnoreCase("on")) {
					if (ServerOptions.PVP.isActive()) {
						player.sendMessage("§3§lDAMAGE §fO pvp já está §a§lATIVADO");
						return true;
					}
					ServerOptions.PVP.setActive(true);
					sendWarning(player.getName() + " ativou o pvp");
					Bukkit.broadcastMessage("§3§lDAMAGE §fO pvp foi §a§lATIVADO");
					return true;
				} else if (args[1].equalsIgnoreCase("off")) {
					if (!ServerOptions.PVP.isActive()) {
						player.sendMessage("§3§lDAMAGE §fO pvp já está §c§lDESATIVADO");
						return true;
					}
					ServerOptions.PVP.setActive(false);
					sendWarning(player.getName() + " desativou o pvp");
					Bukkit.broadcastMessage("§3§lDAMAGE §fO pvp foi §c§lDESATIVADO");
					return true;
				}
				return true;
			} else {
				player.sendMessage("§3§lDAMAGE §fUtilize o comando: /dano (global/pvp) (on/off)");
			}
		} else {
			return true;
		}
		return false;
	}

}
