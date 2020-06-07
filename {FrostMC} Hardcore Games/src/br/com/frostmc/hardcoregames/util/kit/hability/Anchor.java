package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Anchor implements Listener {

	@EventHandler
	private void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (!(e.getEntity() instanceof Player))
			return;
		if (!(e.getDamager() instanceof Player))
			return;

		final Player player = (Player) e.getEntity();
		final Player damager = (Player) e.getDamager();

		if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.ANCHOR)) {
			player.setVelocity(new Vector());

			new BukkitRunnable() {
				public void run() {
					player.setVelocity(new Vector());
					player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
				}
			}.runTaskLater(FrostHG.getInstance(), 1);

			damager.setVelocity(new Vector());

			new BukkitRunnable() {
				public void run() {
					damager.setVelocity(new Vector());
					damager.playSound(damager.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
				}
			}.runTaskLater(FrostHG.getInstance(), 1);
		}

		if (FrostHG.getManager().getKitAPI().hasKit(damager, Kits.ANCHOR)) {
			damager.setVelocity(new Vector());

			new BukkitRunnable() {
				public void run() {
					damager.setVelocity(new Vector());
					damager.playSound(damager.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
				}
			}.runTaskLater(FrostHG.getInstance(), 1);

			player.setVelocity(new Vector());

			new BukkitRunnable() {
				public void run() {
					player.setVelocity(new Vector());
					player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1.0f, 1.0f);
				}
			}.runTaskLater(FrostHG.getInstance(), 1);
		}
	}

}
