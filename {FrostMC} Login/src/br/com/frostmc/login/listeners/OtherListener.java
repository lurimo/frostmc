package br.com.frostmc.login.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.frostmc.common.util.event.SchedulerEvent;
import br.com.frostmc.common.util.event.SchedulerEvent.SchedulerType;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.login.FrostLogin;
import br.com.frostmc.login.scoreboard.Scoreboarding;

@SuppressWarnings("deprecation")
public class OtherListener implements Listener {
	
	@EventHandler
	public void onUpdate(SchedulerEvent event) {
        if (event.getCurrentTick() % 2 > 0)
            return;
		if (Bukkit.getOnlinePlayers().length <= 0)
			return;
		if (event.getType() == SchedulerType.TICK) {
			Scoreboarding.next();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!FrostLogin.scoreboard.contains(player.getUniqueId())) {
					return;
				} else {
					Scoreboarding.updateTittle(player);
				}
			}
		}
	}
	
	@EventHandler
	public void asd(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void asd(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void asd(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void asd(ItemSpawnEvent e) {
		e.getEntity().remove();
	}
	
	@EventHandler
	public void asd(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void asd(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void asd(PlayerChatEvent event) {
		Player player = event.getPlayer();
		if (FrostLogin.checkToLogin.containsKey(player.getUniqueId())) {
			if (!FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.LOGGED)) {
				event.setCancelled(true);
				return;
			} else {
				event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void asd(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (FrostLogin.checkToLogin.containsKey(player.getUniqueId())) {
			if (!FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.LOGGED)) {
				event.setCancelled(true);
				return;
			} else {
				event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void asd(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage();
		if (FrostLogin.checkToLogin.containsKey(player.getUniqueId())) {
			if (!FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.LOGGED)) {
				if (command.startsWith("/login") || command.startsWith("/register")) {
					event.setCancelled(false);
				} else {
					player.sendMessage("§8[§c§lERRO§8] §fVocê não pode executar comandos no servidor de login.");
					event.setCancelled(true);
				}
			}
		}
	}

}
