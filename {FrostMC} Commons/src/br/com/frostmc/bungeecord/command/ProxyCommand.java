package br.com.frostmc.bungeecord.command;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


import com.google.common.collect.ImmutableSet;

import br.com.frostmc.bungeecord.listener.PlayerLoginListener;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

@SuppressWarnings("deprecation")
public abstract class ProxyCommand extends Command implements TabExecutor {

	public static final String ERROR = "§cERRO§7: ";
	public static final String OFFLINE = "§c§lOFFLINE §f";

	public ProxyCommand(String name) {
		super(name);
	}

	public ProxyCommand(String name, String... aliases) {
		super(name, null, aliases);
	}

	public abstract void execute(CommandSender commandSender, String[] args);
	
	public boolean hasGroupPermission(ProxiedPlayer player, GroupsType group) {
		if (!PlayerLoginListener.checkGroup.containsKey(player.getName())) {
			return false;
		}
		GroupsType playerGroup = PlayerLoginListener.checkGroup.get(player.getName());
		return playerGroup.ordinal() >= group.ordinal();
	}
	
	public void sendError(CommandSender commandSender, String message) {
		getPlayer(commandSender).sendMessage(ERROR + message);
	}
	
	public void sendOfflinePlayerMessage(CommandSender commandSender, String player) {
		commandSender.sendMessage(OFFLINE + "O jogador " + player + " está offline.");
	}
	
	public Integer getInteger(String string) {
		return Integer.valueOf(string);
	}

	public ProxiedPlayer getPlayer(CommandSender sender) {
		return (ProxiedPlayer)sender;
	}
	
	public boolean isOnline(ProxiedPlayer target) {
		return target != null;
	}

	public boolean isPlayer(CommandSender commandSender) {
		return commandSender instanceof ProxiedPlayer;
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

	public void sendWarning(String warning) {
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
			if (hasGroupPermission(player, GroupsType.MODPLUS)) {
				player.sendMessage("§7§o[" + warning + "]");
			}
		}
	}

	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		if (args.length == 0) {
			return ImmutableSet.of();
		}
		Set<String> match = new HashSet<>();
		String search = args[0].toLowerCase();
		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
			if (player.getName().toLowerCase().startsWith(search)) {
				match.add(player.getName());
			}
		}
		return match;
	}
}