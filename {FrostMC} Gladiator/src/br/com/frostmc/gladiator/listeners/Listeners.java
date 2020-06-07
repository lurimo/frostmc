package br.com.frostmc.gladiator.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.battle1v1.GladiatorManager;
import br.com.frostmc.gladiator.commands.staffer.BuildCommand;
import br.com.frostmc.gladiator.commands.staffer.BuildCommand.BuildModes;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.warp.WarpManager;

public class Listeners implements Listener {
	
	@EventHandler
	public void asd(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		for (int i = 1;i <100; i++) {
			player.sendMessage(" ");
		}
		player.sendMessage("§6§lSERVER §fVocê foi conectado com sucesso.");
		FrostGladiator.scoreboard.add(player.getUniqueId());
		BuildCommand.buildModes.put(player.getUniqueId(), BuildModes.OFF);
		WarpManager.send(player);
	}
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		FrostGladiator.scoreboard.remove(player.getUniqueId());
		Scoreboarding.removeScoreboard(player);
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (!GladiatorManager.fighting.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (!GladiatorManager.fighting.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	
	 @EventHandler
	 public void asd(ItemSpawnEvent e){
		 Bukkit.getScheduler().scheduleSyncDelayedTask(FrostGladiator.getInstance(), new Runnable() {
			 public void run() {
				 e.getEntity().remove();
			}
		}, 100L);
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
	public void asd(CreatureSpawnEvent e) {
		if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void soup(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType().equals((Object) Material.BOAT)) {
			e.setCancelled(true);
			return;
		}
		Damageable hp = p;
		if (hp.getHealth() != 20.0D) {
			if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && (p.getItemInHand().getTypeId() == 282)) {
				p.setHealth(hp.getHealth() + 7 > hp.getMaxHealth() ? hp.getMaxHealth() : hp.getHealth() + 7);
				p.getItemInHand().setType(Material.BOWL);
			}
		}
	}
	
	@EventHandler
	public void motd(ServerListPingEvent event) {
		event.setMotd("online");
	}
	
}
