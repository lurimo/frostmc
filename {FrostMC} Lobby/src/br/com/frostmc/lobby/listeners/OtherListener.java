package br.com.frostmc.lobby.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.scoreboard.Scoreboarding;
import br.com.frostmc.lobby.util.Util;
import br.com.frostmc.lobby.util.bossbar.BarAPI;

public class OtherListener implements Listener {
	
	private int ticks = 3;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUpdate(SchedulerEvent event) {
        if (event.getCurrentTick() % 2 > 0)
            return;
		if (Bukkit.getOnlinePlayers().length <= 0)
			return;
		if (event.getType() == SchedulerType.TICK) {
			Scoreboarding.next();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!FrostLobby.scoreboard.contains(player.getUniqueId())) {
					return;
				} else {
					Scoreboarding.updateTittle(player);
				}
			}
			if (BarAPI.players.isEmpty())
				return;
			--ticks;
			if (ticks <= 0) {
				ticks = 3;
				for (UUID uuid : BarAPI.players.keySet()) {
					Player player = Bukkit.getPlayer(uuid);
					if (player == null) {
						continue;
					}
					Util.sendPacket(player, BarAPI.players.get(uuid).getTeleportPacket(BarAPI.getDragonLocation(player)));
				}
			}
		} else if (event.getType() == SchedulerType.MINUTE) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!FrostLobby.scoreboard.contains(player.getUniqueId())) {
					return;
				} else {
					Scoreboarding.updateCash(player);
					Scoreboarding.updatePosition(player);
				}
			}
		}
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if (e.toWeatherState())
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}

}
