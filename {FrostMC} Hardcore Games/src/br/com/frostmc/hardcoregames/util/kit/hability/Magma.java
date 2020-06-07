package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Magma implements Listener {
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (FrostHG.state!=State.JOGO)
			return;
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((FrostHG.getManager().getKitAPI().hasKit(p, Kits.MAGMA)) && ((e.getCause() == EntityDamageEvent.DamageCause.LAVA) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE) || (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))) e.setCancelled(true);
		}
	}

	@EventHandler
	public void snailEventd(EntityDamageByEntityEvent event) {
		if (FrostHG.state!=State.JOGO)
			return;
		if ((!(event.getEntity() instanceof Player)) || (!(event.getDamager() instanceof Player))) {
			return;
		}

		Player player = (Player) event.getDamager();
		Player player1 = (Player) event.getEntity();

		if (!FrostHG.getManager().getKitAPI().hasKit(player, Kits.MAGMA)) {
			return;
		}
		if ((Math.random() > 0.5D) && (Math.random() < 0.4D))
			player1.setFireTicks(150);
	}

}
