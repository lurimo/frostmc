package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Fireman implements Listener{

	@EventHandler
	public void onFireman(EntityDamageEvent event) {
		if (FrostHG.state!=State.JOGO) {
			return;
		}
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}
		Player fireman = (Player) entity;
		if (!FrostHG.getManager().getKitAPI().hasKit(fireman, Kits.FIREMAN)) {
			return;
		}
		EntityDamageEvent.DamageCause fire = event.getCause();
		if ((fire == EntityDamageEvent.DamageCause.FIRE) || (fire == EntityDamageEvent.DamageCause.LAVA) || (fire == EntityDamageEvent.DamageCause.FIRE_TICK) || (fire == EntityDamageEvent.DamageCause.LIGHTNING)) {
			event.setCancelled(true);
		}
	}
}
