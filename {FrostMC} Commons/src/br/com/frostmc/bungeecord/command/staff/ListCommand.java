package br.com.frostmc.bungeecord.command.staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ListCommand extends ProxyCommand {

	public ListCommand() {
		super("list", "", "glist");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
			if (!hasGroupPermission(proxiedPlayer, GroupsType.ADMIN)) {
				proxiedPlayer.sendMessage(Strings.getPermission());
				return;
			}
		}

		for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
			if (server.canAccess(commandSender)) {
				List<String> players = new ArrayList<>();
				for (ProxiedPlayer player : server.getPlayers()) {
					players.add(player.getDisplayName());
				}
				Collections.sort(players, String.CASE_INSENSITIVE_ORDER);
				TextComponent teleport = new TextComponent("§a[" + server.getName() + "] §e(" + server.getPlayers().size() + "): §f" + Util.format(players, "§r, "));
                teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClique para entrar no servidor §a" + server.getName()).create()));
                teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/connect " + server.getName()));
                commandSender.sendMessage(teleport);
			}
		}
		commandSender.sendMessage("§fTotal de jogadores online: " + ProxyServer.getInstance().getOnlineCount());

	}

}
