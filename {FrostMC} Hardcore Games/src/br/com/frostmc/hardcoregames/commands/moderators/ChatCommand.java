package br.com.frostmc.hardcoregames.commands.moderators;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.commands.BaseCommand;

public class ChatCommand extends BaseCommand {

	public ChatCommand() {
		super("chat", "monitore o chat", Arrays.asList("chats", "cc", "chatoff", "chaton", "chatclear"));
	}

	public static boolean chat = false;
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (!BukkitMain.getPermissions().isMod(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lCHAT §fUtilize o comando: /chat (on/off/clear)");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (ServerOptions.CHAT.isActive()) {
						player.sendMessage("§a§lCHAT §fO chat já está §a§lATIVADO");
						return true;
					}
					BukkitMain.getCore().getUtil().changeServerOption(ServerOptions.CHAT, true);
					sendWarning(player.getName() + " ativou o chat do servidor");
					player.sendMessage("§a§lCHAT §fVocê §a§lATIVOU §fo chat!");
					Bukkit.broadcastMessage("§a§lCHAT §fO chat foi §a§lATIVADO");
					return true;
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!ServerOptions.CHAT.isActive()) {
						player.sendMessage("§c§lCHAT §fO chat já está §c§lDESATIVADO");
						return true;
					}
					BukkitMain.getCore().getUtil().changeServerOption(ServerOptions.CHAT, false);
					sendWarning(player.getName() + " desativou o chat do servidor");
					player.sendMessage("§c§lCHAT §fVocê §c§lDESATIVOU §fo chat!");
					Bukkit.broadcastMessage("§c§lCHAT §fO chat foi §c§lDESATIVADO");
					return true;
				} else if (args[0].equalsIgnoreCase("clear")) {
					sendWarning(player.getName() + " limpou o chat do servidor");
					for (int i = 1;i <100; i++) {
						Bukkit.broadcastMessage(" ");
					}
					Bukkit.broadcastMessage("§b§lCHAT §fO chat foi §2§lLIMPO§f.");
					return true;
				}
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("§3§lCHAT §fUtilize o comando: /chat (on/off/clear)");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (ServerOptions.CHAT.isActive()) {
						player.sendMessage("§a§lCHAT §fO chat já está §a§lATIVADO");
						return true;
					}
					BukkitMain.getCore().getUtil().changeServerOption(ServerOptions.CHAT, true);
					sendWarning(player.getName() + " ativou o chat do servidor");
					player.sendMessage("§a§lCHAT §fVocê §a§lATIVOU §fo chat!");
					Bukkit.broadcastMessage("§a§lCHAT §fO chat foi §a§lATIVADO");
					return true;
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!ServerOptions.CHAT.isActive()) {
						player.sendMessage("§c§lCHAT §fO chat já está §c§lDESATIVADO");
						return true;
					}
					BukkitMain.getCore().getUtil().changeServerOption(ServerOptions.CHAT, false);
					sendWarning(player.getName() + " desativou o chat do servidor");
					player.sendMessage("§c§lCHAT §fVocê §c§lDESATIVOU §fo chat!");
					Bukkit.broadcastMessage("§c§lCHAT §fO chat foi §c§lDESATIVADO");
					return true;
				} else if (args[0].equalsIgnoreCase("clear")) {
					sendWarning(player.getName() + " limpou o chat do servidor");
					for (int i = 1;i <100; i++) {
						Bukkit.broadcastMessage(" ");
					}
					Bukkit.broadcastMessage("§b§lCHAT §fO chat foi §2§lLIMPO§f.");
					return true;
				}
			}
		}
		return false;
	}

}
