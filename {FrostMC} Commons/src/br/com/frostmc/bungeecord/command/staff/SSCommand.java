package br.com.frostmc.bungeecord.command.staff;

import java.util.HashSet;
import java.util.Set;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.minecraft.util.com.google.common.collect.ImmutableSet;

public class SSCommand extends ProxyCommand {

	public SSCommand() {
		super("ss", "screenshare");
	}
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
		} else {
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) commandSender;

		if (!hasGroupPermission(player, GroupsType.MODGC)) {
			player.sendMessage(Strings.getPermission());
			return;
		}

		ServerInfo target = ProxyServer.getInstance().getServerInfo("SS-01".toUpperCase());
		ServerInfo lobby = ProxyServer.getInstance().getServerInfo("LOBBY-1".toUpperCase());

		if (args.length == 0) {
			showHelp(commandSender);
		} else {
			ProxiedPlayer playerTarget = ProxyServer.getInstance().getPlayer(args[0]);
			if (target == null) {
				commandSender.sendMessage(new TextComponent("§3§lSCREENSHARE §fO servidor de screenshare está §c§lOFFLINE"));
				return;
			}
			if (playerTarget != null) {
				if (playerTarget.getServer().getInfo().getName().toUpperCase().startsWith("SS")) {
					playerTarget.sendMessage(new TextComponent("§a§lLIBERADO §fVocÃª foi §a§lLIBERADO§f da §b§lSCREENSHARE"));
					player.sendMessage(new TextComponent("§a§lLIBERADO §fVocÃª foi §a§lCONCLUIU§f a §b§lSCREENSHARE"));
					player.connect(lobby);
					playerTarget.connect(lobby);
				} else {
					summon(player, playerTarget, target);
				}
			} else {
				commandSender.sendMessage(new TextComponent("§3§lSCREENSHARE §fO player está §c§lOFFLINE"));
			}
		}
	}

	public void showHelp(CommandSender commandSender) {
		commandSender.sendMessage(new TextComponent("§3§lSCREENSHARE §fUse: /ss (player)"));
	}

	@SuppressWarnings("deprecation")
	private void summon(ProxiedPlayer player, ProxiedPlayer puxado, ServerInfo target) {
		if ((player.getServer() != null) && (!player.getServer().getInfo().equals(target))) {
			puxado.connect(target);
			player.connect(target);

			for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
				if (hasGroupPermission(pp, GroupsType.MODGC)) {
					pp.sendMessage(new TextComponent("§a§lSCREENSHARE §f" + puxado.getName() + "(" + puxado.getUUID().toString() + ") agora está em screenshare, via requisição de §a§l" + player.getName()));
				}
			}
		}
	}

	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		if ((args.length > 2) || (args.length == 0)) {
			return ImmutableSet.of();
		}
		Set<String> match = new HashSet<>();
		if (args.length == 1) {
			String search = args[0].toLowerCase();
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				if (player.getName().toLowerCase().startsWith(search)) {
					match.add(player.getName());
				}
			}
		}
		return match;
	}

}
