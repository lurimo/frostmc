package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Scout implements Listener {

	@EventHandler
	public void onDamgeScout(EntityDamageEvent e) {
		if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) && ((e.getEntity() instanceof Player)) && (((Player) e.getEntity()).hasPotionEffect(PotionEffectType.SPEED))) {
			Player player = (Player)e.getEntity();
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.SCOUT))
				e.setCancelled(true);
		}
	}
	
	public static void checkPotion(Player player) {
		new BukkitRunnable() {
			int timer = 0;
			@Override
			public void run() {
				if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.SCOUT)) {
					if (timer % 60 == 0) {
						player.getInventory().addItem(new ItemBuilder().setNome("§bKit Scout").setMaterial(Material.POTION).setDurabilidade(16418).setQuantia(2).finalizar());
					}
					timer++;
				} else
					cancel();
				
			}
		}.runTaskTimer(FrostHG.getInstance(), 0L, 20L);
	}

}
