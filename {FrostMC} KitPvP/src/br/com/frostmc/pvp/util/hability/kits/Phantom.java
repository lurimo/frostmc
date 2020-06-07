package br.com.frostmc.pvp.util.hability.kits;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Phantom implements Listener {
	
	private HashMap<UUID, ItemStack[]> armors = new HashMap<>();

	public String kitName = "Kit " + Kits.PHANTOM.getName();
	
	@EventHandler
	protected void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (KitAPI.hasKit(p, Kits.PHANTOM.getName())) {
			if (p.getItemInHand().getType() == Material.FEATHER) {
				if (!CooldownAPI.hasCooldown(p, kitName)) {
					CooldownAPI.addCooldown(p, new Cooldown(kitName, 30L));

					p.setAllowFlight(true);
					armors.put(p.getUniqueId(), p.getInventory().getArmorContents());
					p.getInventory().setArmorContents(new ItemStack[] { Cor(Material.LEATHER_BOOTS, Color.AQUA), Cor(Material.LEATHER_LEGGINGS, Color.AQUA), Cor(Material.LEATHER_CHESTPLATE, Color.AQUA), Cor(Material.LEATHER_HELMET, Color.AQUA) });
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10f, 5f);

					p.updateInventory();
					new BukkitRunnable() {
						int tempo = 5;

						public void run() {
							if (tempo > 0 && tempo != 1) {
								p.sendMessage("§3§lPHANTOM §fRestam §c" + tempo + " §fsegundos.");
							}
							if (tempo == 1) {
								p.sendMessage("§3§lPHANTOM §fRestam §c" + tempo + " §fsegundo.");
							}
							if (tempo == 0) {
								p.setAllowFlight(false);
								p.getInventory().setArmorContents((ItemStack[]) armors.get(p.getUniqueId()));
								armors.remove(p.getUniqueId());
								p.sendMessage("§3§lPHANTOM §fAcabou o tempo!");
								cancel();
							}
							--tempo;
						}
					}.runTaskTimer(FrostPvP.getInstance(), 0, 20);
				} else {
					p.sendMessage(CooldownAPI.getMessage(p));
				}
			}
		}
	}

	@EventHandler
	protected void onPlayerThief(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;

		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() != null) {
			if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS
					|| e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE
					|| e.getCurrentItem().getType() == Material.LEATHER_HELMET
					|| e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS) {
				if (KitAPI.hasKit(p, Kits.PHANTOM.getName())) {
					if (CooldownAPI.hasCooldown(p, kitName)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	private ItemStack Cor(Material mat, Color cor) {
		ItemStack armor = new ItemStack(mat);
		LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
		meta.setColor(cor);
		armor.setItemMeta(meta);
		return armor;
	}
}
