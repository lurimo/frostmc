package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Achilles implements Listener {

	@EventHandler(priority=EventPriority.HIGH)
	public void onAchilles(EntityDamageByEntityEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player p = (Player) e.getDamager();
			final Player t = (Player) e.getEntity();
			
			ItemStack item = p.getItemInHand();
			if (item == null) return;
			if (FrostHG.getManager().getKitAPI().hasKit(t, Kits.ACHILLES)) {
				if (item.getType().toString().contains("WOOD")) {
					e.setDamage(e.getDamage() + 5.0D);
				} else {
					e.setDamage(e.getDamage() - 4.0D);
				}
			}
		}
	}

}
