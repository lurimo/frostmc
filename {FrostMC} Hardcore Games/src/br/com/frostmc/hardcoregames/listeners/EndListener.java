package br.com.frostmc.hardcoregames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;

public class EndListener implements Listener {
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if (FrostHG.state == State.FINAL) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (FrostHG.state == State.FINAL) {
			e.setCancelled(true);
		}
	}

}
