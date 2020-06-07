package br.com.frostmc.bungeecord.command.player;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.AccountBungee;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.GroupsType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LobbyCommand extends ProxyCommand {
	
	public LobbyCommand() {
		super("lobby", "", "hub");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if(isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			AccountBungee account = new AccountBungee(player.getName());
			if (args.length == 0) {
				if(player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("login"))) {
					if (!account.getGroups().hasGroupPermission(GroupsType.ADMIN)) {
						player.sendMessage("§8[§c§lERRO§8] §fAntes de acessar os outros servidores de nossa rede, você deve passar pelo Lobby.");
						return;
					}
				}
				if(player.getServer().getInfo().equals(ProxyServer.getInstance().getServerInfo("lobby"))) {
					player.sendMessage("§8[§c§lERRO§8] §fVocê já está conectado a este servidor.");
					return;
				} else {
					player.sendMessage(" ");
					player.sendMessage("§8[§c§lCONNECT§8] §fTentando se conectar ao servidor §e" + Strings.getServerIP("lobby") + "§f.");
					player.sendMessage(" ");
					player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
				}
			}
		} else {
			return;
		}
	}

}
