package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Cannibal implements Listener {

	@EventHandler
	private void Dano(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			Player p = (Player) e.getDamager();
			if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.CANNIBAL)) {
				if (new Random().nextInt(100) <= 35) {
					LivingEntity d = (LivingEntity) e.getEntity();
					d.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 80, 1), true);
					int fome = p.getFoodLevel();
					fome++;
					if (fome <= 20) {
						p.setFoodLevel(fome);
					}
				}
			}
		}
	}
}