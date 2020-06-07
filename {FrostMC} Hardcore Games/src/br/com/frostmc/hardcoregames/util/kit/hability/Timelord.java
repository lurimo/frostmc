package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Timelord implements Listener {
	
	public String kitName = "Kit " + Kits.TIMELORD.getName();
	public HashMap<UUID, String> interact = new HashMap<UUID, String>();
	
	@EventHandler
	public void onPlayerInteractLordOfTime(PlayerInteractEvent e) {
		ItemStack i = e.getItem();
		if (i == null)
			return;
		if (e.getAction().name().contains("RIGHT")) {
			if (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.TIMELORD)) {
				if (i.getType().equals(Material.WATCH)) {
					e.setCancelled(true);
					if (FrostHG.state!=State.JOGO) {
						return;
					}
					if (CooldownAPI.hasCooldown(e.getPlayer(), kitName)) {
						e.setCancelled(true);
						e.getPlayer().sendMessage(CooldownAPI.getMessage(e.getPlayer()));
						return;
					}
					 CooldownAPI.addCooldown(e.getPlayer(), new Cooldown(kitName, 35L));

					for (Entity entity : e.getPlayer().getNearbyEntities(20, 20, 20)) {
						if (!(entity instanceof Player)) {
							continue;
						}
						Player ee = (Player) entity;
						if (!FrostHG.getManager().getEspectadores().contains(ee.getUniqueId()) && !AdminManager.isAdmin(ee)) {
							interact.put(ee.getPlayer().getUniqueId(), e.getPlayer().getName());
							ee.sendMessage("§b§lTIMELORD §fO seu tempo foi congelado.");
							ee.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15*20, 9999999));
							ee.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 15*20, 9999999));
							ee.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15*20, 9999999));
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractLord(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (interact.get(player.getUniqueId()) != null) {
			event.setCancelled(true);
			new BukkitRunnable() {
				public void run() {
					interact.remove(player.getUniqueId());
					event.setCancelled(false);
				}
			}.runTaskLater(FrostHG.getInstance(), 200L);
		} else {
			event.setCancelled(false);
		}
	}

}
