package br.com.frostmc.common.command.moderators.moderate;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.util.Strings;

public class ChatCommand extends BaseCommand {

	public ChatCommand() {
		super("chat", "", Arrays.asList(""));
	}

	public enum ChatModes {
		ON, OFF, CLEAR;
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isYoutuberPlus(commandSender)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta Utilize o comando: /chat (mode)");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (ServerOptions.CHAT.isActive()) {
						player.sendMessage("§8[§3§lCHAT§8] §fO chat já está ativado.");
						return true;
					}
					ServerOptions.CHAT.setActive(true);
					player.sendMessage("§8[§3§lCHAT§8] §fVocê ativou o chat do servidor.");
					return true;
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!ServerOptions.CHAT.isActive()) {
						player.sendMessage("§8[§3§lCHAT§8] §fO chat já está desativado.");
						return true;
					}
					ServerOptions.CHAT.setActive(false);
					player.sendMessage("§8[§3§lCHAT§8] §fVocê desativou o chat do servidor.");
					return true;
				} else if (args[0].equalsIgnoreCase("clear")) {
					for (int i = 1;i <100; i++) {
						Bukkit.broadcastMessage(" ");
					}
					player.sendMessage("§8[§3§lCHAT§8] §fO chat do servidor foi limpo.");
					return true;
				}
				return true;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("§8[§c§lERRO§8] §fVocê utilizou o comando de maneira incorreta Utilize o comando: /chat (mode)");
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					if (ServerOptions.CHAT.isActive()) {
						player.sendMessage("§8[§3§lCHAT§8] §fO chat já está ativado.");
						return true;
					}
					ServerOptions.CHAT.setActive(true);
					player.sendMessage("§8[§3§lCHAT§8] §fVocê ativou o chat do servidor.");
					return true;
				} else if (args[0].equalsIgnoreCase("off")) {
					if (!ServerOptions.CHAT.isActive()) {
						player.sendMessage("§8[§3§lCHAT§8] §fO chat já está desativado.");
						return true;
					}
					ServerOptions.CHAT.setActive(false);
					player.sendMessage("§8[§3§lCHAT§8] §fVocê desativou o chat do servidor.");
					return true;
				} else if (args[0].equalsIgnoreCase("clear")) {
					for (int i = 1;i <100; i++) {
						Bukkit.broadcastMessage(" ");
					}
					player.sendMessage("§8[§3§lCHAT§8] §fO chat do servidor foi limpo.");
					return true;
				}
				return true;
			}
		}
		return false;
	}

}
