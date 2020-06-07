package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Worm implements Listener {
	
	@EventHandler
	public void onDamage(BlockDamageEvent e) {
		if ((FrostHG.state!=State.JOGO))
			return;
		
		if (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.TOWER) || FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.WORM)) {
			if (e.getBlock().getType() == Material.DIRT) {
				e.setInstaBreak(true);
				Damageable d = e.getPlayer();
				if (d.getHealth() < d.getMaxHealth()) {
					double vida = d.getHealth() + 2.0D;
					if (vida > d.getMaxHealth()) {
						vida = d.getMaxHealth();
					}
					e.getPlayer().setHealth(vida);
				} else if (e.getPlayer().getFoodLevel() < 20) {
					e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 1);
				}
			}
		}
	}

}
