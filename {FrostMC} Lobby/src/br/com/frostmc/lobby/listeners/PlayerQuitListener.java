package br.com.frostmc.lobby.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.commands.player.FlyCommand;
import br.com.frostmc.lobby.inventory.EventsInventory;
import br.com.frostmc.lobby.inventory.player.ParticleInventory;
import br.com.frostmc.lobby.scoreboard.Scoreboarding;
import br.com.frostmc.lobby.util.admin.AdminManager;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {	
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		FrostLobby.scoreboard.remove(player.getUniqueId());
		Scoreboarding.removeScoreboard(player);
		if (ParticleInventory.effect.containsKey(player.getUniqueId())) {
			ParticleInventory.effect.remove(player.getUniqueId());
			EventsInventory.cancelTask();
		}
		if (FlyCommand.usingFlight.contains(player.getUniqueId())) {
			FlyCommand.usingFlight.remove(player.getUniqueId());
		}
		AdminManager.setPlayerQuit(player);
	}
}
