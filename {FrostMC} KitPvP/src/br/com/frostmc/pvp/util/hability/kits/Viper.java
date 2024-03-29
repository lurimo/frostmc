package br.com.frostmc.pvp.util.hability.kits;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Viper implements Listener {

	@EventHandler
	public void onPosion(EntityDamageByEntityEvent e) {
		if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof Player))) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (KitAPI.hasKit(d, Kits.VIPER.getName())) {
				Random r = new Random();
				int rand = r.nextInt(100);
				if (rand >= 67) p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 0));
			}
		}
	}
}
