package br.com.frostmc.lobby.listeners;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import br.com.frostmc.lobby.commands.player.FlyCommand;

public class GamesListener implements Listener {

	public static ArrayList<String> duble = new ArrayList<String>();

	@EventHandler
	public void run(PlayerToggleFlightEvent e) {
		final Player p = e.getPlayer();
		if (FlyCommand.usingFlight.contains(p.getUniqueId()))
			return;
		if (!duble.contains(p.getName()))
			if ((p.getGameMode() != GameMode.CREATIVE)) {
				e.setCancelled(true);

				p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 5.0F, 5.0F);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);

				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);

				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);

				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);
				p.getWorld().playEffect(p.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), Effect.FIREWORKS_SPARK, 200,
						200);

				p.setAllowFlight(false);
				p.setFlying(false);
				p.setVelocity(p.getLocation().getDirection().multiply(2.0D).setY(1.0D));
			}
	}

	@EventHandler
	public void run(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!duble.contains(p.getName()) && (e.getPlayer().getGameMode() != GameMode.CREATIVE)
				&& (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			p.setAllowFlight(true);
		}
	}
}
