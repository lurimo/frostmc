package br.com.frostmc.bungeecord.command.punish;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.BanType;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PunishCommand extends ProxyCommand{

	public PunishCommand() {
		super("punish", "", "tempmute", "mute", "tempban", "ban", "p", "unban", "unmute");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee accountPlayer = new AccountBungee(player.getName());
			if (!hasGroupPermission(player, GroupsType.TRIAL)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			
			if (args.length < 2) {
				player.sendMessage("§fUtilize o comando: §c/punish (player) (type) (time) (reason)");
				return;
			}
			
			AccountBungee accountTarget = null;
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			if (target == null) {
				accountTarget = new AccountBungee(args[0]);
			} else {
				accountTarget = new AccountBungee(target.getName());
			}
			
			if (args[1].equalsIgnoreCase("pardon") ||args[1].equalsIgnoreCase("unban") || args[1].equalsIgnoreCase("unmute")) {
				if (hasGroupPermission(player, GroupsType.ADMIN)) {
					accountTarget.getPunishs().delete();
					player.sendMessage("§aVocê removeu todas punições de §f" + args[0] + "§acom sucesso.");
					return;
				} else {
					player.sendMessage(Strings.getPermission());
				}
			}
			
			String reason = null;
			if (args[1].equalsIgnoreCase("kick")) {
				reason = getArgs(args, 2);
				target.disconnect(Strings.getServer() + "\n" + "\n" + "§fVocê foi desconectado do servidor!" + "\n" + "\n" + "§fExpulso por §a" + player.getName() + " §fpelo motivo §e" + reason + "\n" + "\n" + "§fA §e§lPUNIÇÃO §ffoi injusta? Denuncie o autor do caso." + "\n" + "\n" + "§3" + Strings.getWebstore());
				return;
			} else {
				reason = getArgs(args, 3);
			}
			
			BanType banType = BanType.BANNED;
			try {
				if (args[1].equalsIgnoreCase("ban")) {
					banType = BanType.BANNED;
				} else if (args[1].equalsIgnoreCase("mute")) {
					banType = BanType.MUTATED;
				} else {
					banType = BanType.valueOf(args[1].toUpperCase());
				}
			} catch (Exception exception) {
				sendError(commandSender, "Este tipo de punição não foi encontrada. Use: §aban§7, §akick§7 ou §amute§7.");
				return;
			}
			
			if (args[2].equals("p")) {
				accountTarget.getPunishs().setPunishServer(accountTarget, accountPlayer, banType, TemporaryType.PERMANENT, reason, 0L);
				return;
			} else if (args[2].equals("t")) {
				player.sendMessage("§fUtilize o comando: §c/punish (player) (type) (time) (reason)");
			}
			
			long time;
			try {
				time = Longs.fromString(args[2].toUpperCase());
			} catch (Exception e) {
				sendError(commandSender, "Este tipo de tempo não foi encontrado. Use: §a30d§7 ou §ap§7 para punir permanentemente");
				return;
			}
			accountTarget.getPunishs().setPunishServer(accountTarget, accountPlayer, banType, TemporaryType.TEMPORARY, reason, time);
			return;
		} else {
			CommandSender player = commandSender;
			if (args.length < 2) {
				player.sendMessage("§fUtilize o comando: §c/punish (player) (type) (time) (reason)");
				return;
			}
			
			AccountBungee accountTarget = null;
			ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
			if (target == null) {
				accountTarget = new AccountBungee(args[0]);
			} else {
				accountTarget = new AccountBungee(target.getName());
			}
			
			if (args[1].equalsIgnoreCase("pardon") ||args[1].equalsIgnoreCase("unban") || args[1].equalsIgnoreCase("unmute")) {
				accountTarget.getPunishs().delete();
				player.sendMessage("§aVocê removeu todas punições de §f" + args[0] + "§acom sucesso.");
				return;
			}
			
			String reason = null;
			if (args[0].equalsIgnoreCase("kick")) {
				reason = getArgs(args, 2);
				target.disconnect(Strings.getServer() + "\n" + "\n" + "§fVocê foi desconectado do servidor!" + "\n" + "\n" + "§fExpulso por §a" + player.getName() + " §fpelo motivo §e" + reason + "\n" + "\n" + "§fA §e§lPUNIÇÃO §ffoi injusta? Denuncie o autor do caso." + "\n" + "\n" + "§3" + Strings.getWebstore());
				return;
			} else {
				reason = getArgs(args, 3);
			}
			
			BanType banType = BanType.BANNED;
			try {
				if (args[1].equalsIgnoreCase("ban")) {
					banType = BanType.BANNED;
				} else if (args[1].equalsIgnoreCase("mute")) {
					banType = BanType.MUTATED;
				} else {
					banType = BanType.valueOf(args[1].toUpperCase());
				}
			} catch (Exception exception) {
				sendError(commandSender, "Este tipo de punição não foi encontrada. Use: §aban§7, §akick§7 ou §amute§7.");
				return;
			}
			
			if (args[2].contains("p")) {
				accountTarget.getPunishs().setPunishServer(accountTarget, null, banType, TemporaryType.PERMANENT, reason, 0L);
				return;
			}
			
			long time;
			try {
				time = Longs.fromString(args[2].toUpperCase());
			} catch (Exception e) {
				sendError(commandSender, "Este tipo de tempo não foi encontrado. Use: §a30d§7 ou §ap§7 para punir permanentemente");
				return;
			}
			accountTarget.getPunishs().setPunishServer(accountTarget, null, banType, TemporaryType.TEMPORARY, reason, time);
			return;
		}
	}

}
