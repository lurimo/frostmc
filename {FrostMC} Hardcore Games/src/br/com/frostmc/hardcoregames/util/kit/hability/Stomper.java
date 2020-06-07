package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Stomper implements Listener {
	
	@EventHandler
	private void stomperFall(EntityDamageEvent event) {
		if (event.isCancelled())
			return;
		
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if ((event.getCause() == EntityDamageEvent.DamageCause.FALL) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.STOMPER) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.TOWER)))) {
				 if (event.getDamage() <= 4.0) {
					 event.setDamage(event.getDamage() - 1.0D);
					 return;
				 }
			
				 List<Entity> entity = p.getNearbyEntities(6.0D, 2.0D, 6.0D);
				 for (Entity en : entity) {
					 if  (en instanceof Player) {
						  Player stompados = (Player) en;
						  if ((FrostHG.getManager().getEspectadores().contains(stompados.getUniqueId())) || (!stompados.getGameMode().equals(GameMode.SURVIVAL)))
							   return;
						 
						  if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.STOMPER)) {
							  if (stompados.isSneaking()) {
								  stompados.damage(0.5, p);
								  stompados.damage(3.5D);
							  } else {
								 stompados.damage(0.5, p);
								 stompados.damage(p.getFallDistance() - 8.5F);
								 stompados.playSound(stompados.getLocation(), Sound.ANVIL_BREAK, 1.0F, 1.0F);
							  }
						  } else if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.TOWER)) {
							  if (stompados.isSneaking()) {
								  stompados.damage(0.5, p);
								  stompados.damage(3.5D);
							  } else {
								 stompados.damage(0.5, p);
								 stompados.damage(p.getFallDistance() - 4.5F);
							  }
						  }
					 }
				 }
				 if (event.getDamage() > 4.0D) {
					 event.setDamage(4.0D);
				 }
			}
		}
	}

}
