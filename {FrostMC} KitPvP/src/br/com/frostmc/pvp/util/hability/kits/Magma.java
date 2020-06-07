package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Magma implements Listener {

	@EventHandler
	public void asd(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((KitAPI.hasKit(p, Kits.MAGMA.getName())) && ((e.getCause() == EntityDamageEvent.DamageCause.LAVA) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))) e.setCancelled(true);
		}
	}

	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if ((!(event.getEntity() instanceof Player)) || (!(event.getDamager() instanceof Player))) {
			return;
		}

		Player player = (Player) event.getDamager();
		Player player1 = (Player) event.getEntity();

		if (!KitAPI.hasKit(player, Kits.MAGMA.getName())) {
			return;
		}
		if ((Math.random() > 0.5D) && (Math.random() < 0.4D))
			player1.setFireTicks(150);
	}
}
