package br.com.frostmc.lobby.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;

public abstract class BaseCommand extends Command {
	
	public boolean enabled = true;

	public BaseCommand(String name) {
		super(name);
	}

	public BaseCommand(String name, String description) {
		super(name, description, "", new ArrayList<>());
	}

	public BaseCommand(String name, String description, List<String> aliases) {
		super(name, description, "", aliases);
	}

	public abstract boolean execute(CommandSender commandSender, String label, String[] args);

	public void sendOfflinePlayer(CommandSender commandSender, String command) {
		commandSender.sendMessage("§c§l" + command.toUpperCase() + " §fEsse jogador encontra-se offline.");
	}
	
	public void sendError(CommandSender commandSender, String message) {
		getPlayer(commandSender).sendMessage("§8[§c§lERRO§8] §f" + message);
	}
	
	public Integer getInteger(String string) {
		return Integer.valueOf(string);
	}
	
	public Player getPlayer(String args) {
		return Bukkit.getPlayer(args);
	}
	
	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer(String args) {
		return Bukkit.getOfflinePlayer(args);
	}
	
	public Player getPlayer(CommandSender sender) {
		return (Player)sender;
	}
	
	public boolean isOnline(Player target) {
		return target != null;
	}

	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	public boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean isUUID(String string) {
		try {
			UUID.fromString(string);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public String getArgs(String[] args, int starting) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = starting; i < args.length; i++) {
			stringBuilder.append(args[i] + " ");
		}
		return stringBuilder.toString();
	}

	@SuppressWarnings("deprecation")
	public void sendWarning(String warning) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (BukkitMain.getPermissions().isModPlus(player)) {
				player.sendMessage("§7§o[" + warning + "]");
			}
		}
	}
	

}
