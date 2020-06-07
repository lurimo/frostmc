package br.com.frostmc.hardcoregames.commands;

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
	
	public void sendError(CommandSender commandSender, String message) {
		getPlayer(commandSender).sendMessage("§c§lERROR §f" + message);
	}
	
	public void sendOfflinePlayerMessage(CommandSender commandSender, String player) {
		commandSender.sendMessage("§c§lOFFLINE §fO jogador " + player + " está offline.");
	}
	
	public void sendNumber(CommandSender commandSender, String message) {
		getPlayer(commandSender).sendMessage("§c§lNUMBER §f" + message);
	}
	
	public Integer getInteger(String string) {
		return Integer.valueOf(string);
	}
	
	protected int getInteger(CommandSender commandSender, String value, int min) {
		return getInteger(commandSender, value, min, Integer.MAX_VALUE);
	}

	public int getInteger(CommandSender commandSender, String value, int min, int max) {
		return getInteger(commandSender, value, min, max, false);
	}

	public int getInteger(CommandSender commandSender, String value, int min, int max, boolean Throws) {
		int i = min;
		try {
			i = Integer.valueOf(value).intValue();
		} catch (NumberFormatException ex) {
			if (Throws) {
				throw new NumberFormatException(String.format("%s is not a valid number", new Object[] { value }));
			}
		}
		if (i < min) {
			i = min;
		} else if (i > max) {
			i = max;
		}
		return i;
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

	public boolean isPlayer(CommandSender commandSender) {
		return commandSender instanceof Player;
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
