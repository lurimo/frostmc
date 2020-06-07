package br.com.frostmc.bungeecord.command.staff;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GroupsetCommand extends ProxyCommand {

	public GroupsetCommand() {
		super("groupset", "setgroup", "group");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee accountPlayer = new AccountBungee(player.getName());
			if (!hasGroupPermission(player, GroupsType.ADMIN)) {
				player.sendMessage(Strings.getPermission());
				return;
			}
			if (args.length == 0 || args.length == 1) {
				player.sendMessage("�fUtilize o comando: �a/groupset (player) (group) (time)");
				return;
			}
			if (args.length == 2) {
				GroupsType groupsType  = GroupsType.MEMBRO;
				try {
					groupsType = GroupsType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					sendError(commandSender, "Esse grupo n�o foi encontrado.");
					return;
				}
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				AccountBungee accountTarget = null;
				if (target == null) {
					accountTarget = new AccountBungee(args[0]);
				} else {
					accountTarget = new AccountBungee(target.getName());
				}
				accountTarget.getGroups().setGroupServer(accountTarget, accountPlayer, groupsType, TemporaryType.PERMANENT, 0L);
				return;
			}
			if (args.length == 3) {
				GroupsType groupsType  = GroupsType.MEMBRO;
				try {
					groupsType = GroupsType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					sendError(commandSender, "Esse tipo de grupo n�o foi encontrado.");
					return;
				}
				
				long time = 0L;
				try {
					time = Longs.fromString(args[2]);
				} catch(Exception exception) {
					sendError(commandSender, "Esse tipo de tempo n�o foi encontrado. Utilize: 1s, 1m, 1h, 1d.");
					time = 0L;
					return;
				}
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				AccountBungee accountTarget = null;
				if (target == null) {
					accountTarget = new AccountBungee(args[0]);
				} else {
					accountTarget = new AccountBungee(target.getName());
				}
				accountTarget.getGroups().setGroupServer(accountTarget, accountPlayer, groupsType, TemporaryType.PERMANENT, time);
				return;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0 || args.length == 1) {
				player.sendMessage("�3�lGROUP �fUtilize o comando: /groupset (player) (group) (time)");
				return;
			}
			if (args.length == 2) {
				GroupsType groupsType  = GroupsType.MEMBRO;
				try {
					groupsType = GroupsType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					sendError(commandSender, "Esse tipo de grupo n�o foi encontrado.");
					return;
				}
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				AccountBungee accountTarget = null;
				if (target == null) {
					accountTarget = new AccountBungee(args[0]);
				} else {
					accountTarget = new AccountBungee(target.getName());
				}
				accountTarget.getGroups().setGroupServer(accountTarget, null, groupsType, TemporaryType.PERMANENT, 0L);
				return;
			}
			if (args.length == 3) {
				GroupsType groupsType  = GroupsType.MEMBRO;
				try {
					groupsType = GroupsType.valueOf(args[1].toUpperCase());
				} catch (Exception exception) {
					sendError(commandSender, "Esse tipo de grupo n�o foi encontrado.");
					return;
				}
				
				long time = 0L;
				try {
					time = Longs.fromString(args[2]);
				} catch(Exception exception) {
					sendError(commandSender, "Esse tipo de tempo n�o foi encontrado. Utilize: 1s, 1m, 1h, 1d.");
					time = 0L;
					return;
				}
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				AccountBungee accountTarget = null;
				if (target == null) {
					accountTarget = new AccountBungee(args[0]);
				} else {
					accountTarget = new AccountBungee(target.getName());
				}
				accountTarget.getGroups().setGroupServer(accountTarget, null, groupsType, TemporaryType.PERMANENT, time);
				return;
			}
			return;
		}
	}

}
