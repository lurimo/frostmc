package br.com.frostmc.login.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import br.com.frostmc.login.FrostLogin;
import br.com.frostmc.login.scoreboard.Scoreboarding;
import br.com.frostmc.login.util.admin.AdminManager;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {	
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		FrostLogin.checkToLogin.remove(player.getUniqueId());
		FrostLogin.scoreboard.remove(player.getUniqueId());
		Scoreboarding.removeScoreboard(player);
		AdminManager.setPlayerQuit(player);
	}
	
	@EventHandler
	public void asd(PlayerKickEvent event) {
		Player player = event.getPlayer();
		FrostLogin.checkToLogin.remove(player.getUniqueId());
		FrostLogin.scoreboard.remove(player.getUniqueId());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PluginDisableEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			FrostLogin.checkToLogin.remove(player.getUniqueId());
			FrostLogin.scoreboard.remove(player.getUniqueId());
		}
	}
}
