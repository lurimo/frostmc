package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Reaper implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (FrostHG.state!=State.JOGO)
			return;
		if ((!event.isCancelled()) && ((event.getDamager() instanceof Player))) {
			Player player = (Player)event.getDamager();
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.REAPER)) {
				if ((event.getEntity() instanceof LivingEntity)) {
					((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6 * 20, 0), true);
				}
			}
		}
	}

}
