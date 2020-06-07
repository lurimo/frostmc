package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Snail implements Listener {
	
	@EventHandler
	public void onEntityDamageViper(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			if (FrostHG.state != State.JOGO)
				return;
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.SNAIL)) {
				if (new Random().nextInt(100) < 33) {
					target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 0), true);
				}
			}
		}
	}

}
