package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.protection.Protection;

public class Anchor implements Listener {

	@EventHandler
	public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (!(e.getDamager() instanceof Player)) {
			return;
		}

		final Player p = (Player) e.getEntity();
		Player a = (Player) e.getDamager();
		if (Protection.hasProtection(p))
			return;
		if (KitAPI.hasKit(p, Kits.ANCHOR.getName())) {
			p.setVelocity(new Vector());
			p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 4.0F, 4.0F);
			a.playSound(a.getLocation(), Sound.ANVIL_BREAK, 4.0F, 4.0F);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FrostPvP.getInstance(), new Runnable() {
				public void run() {
					p.setVelocity(new Vector());
				}
			}, 1L);
		}
		if (KitAPI.hasKit(a, Kits.ANCHOR.getName())) {
			a.playSound(a.getLocation(), Sound.ANVIL_BREAK, 4.0F, 4.0F);
			p.setVelocity(new Vector());
			p.playSound(a.getLocation(), Sound.ANVIL_BREAK, 4.0F, 4.0F);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FrostPvP.getInstance(), new Runnable() {
				public void run() {
					p.setVelocity(new Vector());
				}
			}, 1L);
		}

	}

}
