package br.com.frostmc.bungeecord.command.player;

import java.util.Map;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.server.Event;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ConnectCommand extends ProxyCommand {
	
	public ConnectCommand() {
		super("connect", "", "server", "go");
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = (ProxiedPlayer) commandSender;
			Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
			if (args.length == 0) {
				TextComponent serverList = new TextComponent(ProxyServer.getInstance().getTranslation("server_list", new Object[0]));
				serverList.setColor(ChatColor.BLUE);
				player.sendMessage(" ");
				player.sendMessage("§8[§c§lCONNECT§8] §fUtilize o comando: /connect <server>");
				player.sendMessage(" ");
				
			} else {
				ServerInfo server = (ServerInfo) servers.get(args[0]);
				if (server == null) {
					player.sendMessage("§8[§c§lERRO§8] §fO servidor §c" + args[0].toLowerCase() + "§f não existe em nossa rede.");
					return;
				}
				if (server.getName().startsWith("SS")) {
					if (!hasGroupPermission(player, GroupsType.YOUTUBERPLUS)) {
						player.sendMessage("§8[§c§lERRO§8] §fVocê não possui permissão para ir a este servidor, o mesmo é de acesso restrito a equipe.");
						return;
					}
				}
				if (server.getName().startsWith("LOGIN")) {
					if (!hasGroupPermission(player, GroupsType.YOUTUBERPLUS)) {
						player.sendMessage("§8[§c§lERRO§8] §fVocê não possui permissão para ir a este servidor, oo mesmo é de acesso restrito a equipe.");
						return;
					}
				}
				if (server.getName().startsWith("EVENTO")) {
					Event event = new Event();
					if (event.exists()) {
						player.sendMessage("");
						player.sendMessage("§8[§6§lEVENTOS§8]");
						player.sendMessage("");
						player.sendMessage("§fNenhum servidor está hospedando um evento no momento.");
						player.sendMessage("§fSe quiser saber quando o próximo evento irá rolar, acesse §bhttps://www.redefrost.com.br/eventos§f ou acesse nosso Discord em §bdiscord.io/frostmc§f.");
						player.sendMessage("");
						return;
					}
				}
				if (server == null) {
					player.sendMessage("§8[§c§lERRO§8] §fEste servidor não existe em nossa rede.");
				} else if (player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo(server.getName()))) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê já está conectado a este servidor.");
					return;
				} else if (!server.canAccess(player)) {
					player.sendMessage(Strings.getPermission());
				} else {
					player.sendMessage(" ");
					player.sendMessage("§8[§c§lCONNECT§8] §fTentando se conectar ao servidor §e" + Strings.getServerIP(server.getName()) + "§f.");
					player.sendMessage(" ");
					player.connect(server);				
				}
			}
		} else {
			return;
		}
	}
	
}
