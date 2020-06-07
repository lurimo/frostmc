package br.com.frostmc.bungeecord.command.player;

import br.com.frostmc.bungeecord.command.ProxyCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PingCommand extends ProxyCommand {

	public PingCommand() {
		super("ping", "", "latency");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender commandSender, String[] args) {
		if (isPlayer(commandSender)) {
			ProxiedPlayer player = getPlayer(commandSender);
			if (args.length == 0) {
				player.sendMessage("�8[�a�lPING�8] �fSeu ping � de �a" + (player.getPing() - 2) + "ms.");
				return;
			}
			if (args.length == 1) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null) {
					sendOfflinePlayerMessage(player, args[0]);
					return;
				}
				player.sendMessage("�8[�a�lPING�8] �fO ping de " + target.getName() + " � de �a" + (target.getPing() - 2) + " ms");
				return;
			}
		} else {
			CommandSender player = commandSender;
			if (args.length == 0) {
				player.sendMessage("�cUtilizar este comando no console � in�til.");
				return;
			}
			if (args.length == 1) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null) {
					sendOfflinePlayerMessage(player, args[0]);
					return;
				}
				player.sendMessage("�8[�a�lPING�8] �fO ping de �a" + target.getName() + " �f� de �a" + (target.getPing() - 2) + " ms");
				return;
			}
		}
	}

}