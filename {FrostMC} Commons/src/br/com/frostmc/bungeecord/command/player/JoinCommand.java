package br.com.frostmc.bungeecord.command.player;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import br.com.frostmc.core.data.mysql.bungeecord.server.Event;
import br.com.frostmc.core.util.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class JoinCommand extends ProxyCommand {

	public JoinCommand() {
		super("join", "entrar");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			
			Event event = new Event();
			
			if (args.length == 0) {
				if (!event.exists()) {
					player.sendMessage("");
					player.sendMessage("�8[�6�lEVENTOS�8]");
					player.sendMessage("");
					player.sendMessage("�fNenhum servidor est� hospedando um evento no momento.");
					player.sendMessage("�fSe quiser saber quando o pr�ximo evento ir� rolar, acesse �bhttps://www.redefrost.com.br/eventos�f ou acesse nosso Discord em �bdiscord.io/frostmc�f.");
					player.sendMessage("");
					return;
				}
				player.sendMessage(" ");
				player.sendMessage("�8[�c�lCONNECT�8] �fTentando se conectar ao servidor �f" + Strings.getServerIP("EVENTO") + "�f.");
				player.sendMessage(" ");
				player.connect(ProxyServer.getInstance().getServerInfo("evento"));	
				return;
			}
		} else {
			return;
		}
	}

}
