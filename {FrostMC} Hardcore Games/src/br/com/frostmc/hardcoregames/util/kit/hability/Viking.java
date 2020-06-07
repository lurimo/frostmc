package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Viking implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (FrostHG.state!=State.JOGO)
			return;
		if (((event.getDamager() instanceof Player)) && (FrostHG.getManager().getKitAPI().hasKit((Player) event.getDamager(), Kits.VIKING))) {
			ItemStack item = ((Player) event.getDamager()).getItemInHand();
			if ((item != null) && (item.getType().name().contains("AXE"))) {
				event.setDamage(event.getDamage() + 3.0D);
			}
		}
	}

}
