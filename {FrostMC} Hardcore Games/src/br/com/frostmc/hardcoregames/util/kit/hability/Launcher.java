package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.google.common.collect.Lists;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;

public class Launcher implements Listener {
	
	private List<UUID> launcher = Lists.newArrayList();
	
	@EventHandler
	private void onPlayerMoveLauncher(PlayerMoveEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		Player player = e.getPlayer();
		Location player_location = player.getLocation();
		
		if (player_location.add(0, -0.08, 0).getBlock().getType() == Material.SPONGE) {
			player.setVelocity(new Vector(0, 7, 0));
			if (!launcher.contains(player.getUniqueId())) {
				launcher.add(player.getUniqueId());
			}
		}
	}
	
	@EventHandler
	private void onPlayerDamageLauncher(EntityDamageEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player)e.getEntity();
		if (e.getCause() == DamageCause.FALL && launcher.contains(player.getUniqueId())) {
			launcher.remove(player.getUniqueId());
			e.setCancelled(true);
		}
	}

}
