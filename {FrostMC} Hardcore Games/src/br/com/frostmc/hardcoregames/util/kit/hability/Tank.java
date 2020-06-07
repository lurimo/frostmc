package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Tank implements Listener {

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (FrostHG.state!=State.JOGO)
			return;
		if (((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) && ((event.getEntity() instanceof Player)) && (FrostHG.getManager().getKitAPI().hasKit((Player)event.getEntity(), Kits.TANK))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onKilled(PlayerDeathEvent event) {
		if (FrostHG.state!=State.JOGO)
			return;
		if ((event.getEntity() instanceof Player)) {
			Player player = event.getEntity();
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.TANK)) {
				player.getWorld().createExplosion(player.getLocation(), 2.0F);
			}
		}
	}

}
