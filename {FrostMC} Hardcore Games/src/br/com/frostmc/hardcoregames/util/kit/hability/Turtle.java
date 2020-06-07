package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Turtle implements Listener{

	@EventHandler
	public void onTurtleReceiveDamage(EntityDamageEvent e) {
		Entity entidade = e.getEntity();
		if ((entidade instanceof Player)) {
			Player p = (Player) entidade;
			if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.TURTLE)) {
				if (p.isSneaking()) {
					e.setDamage(1.0D);
					entidade.getWorld().playEffect(entidade.getLocation(), Effect.MOBSPAWNER_FLAMES, 3);
				} else {
					p.damage(e.getDamage());
				}
			}
		}
	}

	@EventHandler
	public void onTurtleGiveDamage(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		if ((damager instanceof Player)) {
			Player p2 = (Player) damager;
			if (FrostHG.getManager().getKitAPI().hasKit(p2, Kits.TURTLE)) {
				if ((p2.isSneaking())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
