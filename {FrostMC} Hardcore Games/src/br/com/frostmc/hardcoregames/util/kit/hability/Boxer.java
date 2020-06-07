package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Boxer {
	
	@EventHandler
	public void onHitBoxer(EntityDamageByEntityEvent e) {
		if (FrostHG.state!=State.JOGO)
			return;
		if ((e.getDamager() instanceof Player)) {
			Player p = (Player) e.getDamager();
			if ((FrostHG.getManager().getKitAPI().hasKit(p, Kits.BOXER)) && (p.getItemInHand().getType() == Material.AIR)) {
				e.setDamage(e.getDamage() + 2.0D);
			}
		}
	}

}
