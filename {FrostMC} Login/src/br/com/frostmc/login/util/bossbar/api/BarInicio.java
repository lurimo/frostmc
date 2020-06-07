package br.com.frostmc.login.util.bossbar.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.login.FrostLogin;

public class BarInicio implements Listener {

	protected static BarInicio instance;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(PlayerKickEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport(PlayerTeleportEvent e) {
		this.handlePlayerTeleport(e.getPlayer(), e.getFrom(), e.getTo());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent e) {
		this.handlePlayerTeleport(e.getPlayer(), e.getPlayer().getLocation(), e.getRespawnLocation());
	}

	protected void handlePlayerTeleport(Player player, Location from, Location to) {
		if (!BossBarAPI.hasBar(player)) { return; }
		final BossBar bar = BossBarAPI.getBossBar(player);
		bar.setVisible(false);
		new BukkitRunnable() {

			@Override
			public void run() {
				bar.setVisible(true);
			}
		}.runTaskLater(FrostLogin.getInstance(), 2);
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		final BossBar bar = BossBarAPI.getBossBar(e.getPlayer());
		if (bar != null) {
			new BukkitRunnable() {

				@Override
				public void run() {
					if (!e.getPlayer().isOnline()) { return; }
					bar.updateMovement();
				}
			}.runTaskLater(FrostLogin.getInstance(), 0);
		}
	}

}
