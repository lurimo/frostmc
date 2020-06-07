package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Stomper implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	private void onPlayerFallStomper(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player player = (Player) e.getEntity();
		if (e.getCause() != DamageCause.FALL)
			return;
		if (KitAPI.hasKit(player, Kits.STOMPER.getName())) {
			for (Entity entity : player.getNearbyEntities(4.0D, 2.0D, 4.0D)) {
				if (entity instanceof Player) {
					Player players = (Player) entity;
					if (KitAPI.hasKit(players, Kits.NENHUM.getName()))
						return;
					if (players == player) continue;
						if (players.isSneaking() || KitAPI.hasKit(players, Kits.ANTISTOMPER.getName())) {
							players.damage(0.1D, player);
							players.damage(1.0D);
						} else {
							players.damage(0.1D, player);
							players.damage(player.getFallDistance() - 8.1F);
						}
				}
			}
			Location player_location = player.getLocation();
			int radius = 4;
			for (int i = 0; i < 6; i++) {
				for (double x = -radius; x <= radius; x = x + 1.0D) {
					for (double z = -radius; z <= radius; z = z + 1.0D) {
						Location effect_location = new Location(player_location.getWorld(), player_location.getX() + x, player_location.getY(), player_location.getZ() + z);
						effect_location.getWorld().playEffect(effect_location, Effect.WITCH_MAGIC, 500);
					}
				}
			}
			if (e.getDamage() > 4.0D) {
				e.setDamage(4.0D);
			}
		}
	}
	
}