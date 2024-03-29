package br.com.frostmc.bungeecord.listener;

import br.com.frostmc.bungeecord.command.player.ReportCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void asd(PlayerDisconnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		PlayerLoginListener.checkGroup.remove(player.getName());
		if (ReportCommand.reports.contains(player.getName())) {
			ReportCommand.reports.remove(player.getName());
			ReportCommand.reportVitima.remove(player.getName());
			ReportCommand.reportReason.remove(player.getName());
			ReportCommand.reportDate.remove(player.getName());
			ReportCommand.reportServer.remove(player.getName());
			ReportCommand.reportStaffer.remove(player.getName());
		}
	}
	
}
