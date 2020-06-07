package br.com.frostmc.hardcoregames.util.kit.hability;

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
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Phantom implements Listener {

	public String kitName = "Kit " + Kits.PHANTOM.getName();
	private HashMap<UUID, ItemStack[]> armors = new HashMap<>();

	@EventHandler
	protected void onPlayerInteract(PlayerInteractEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		final Player p = e.getPlayer();

		if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.PHANTOM)) {
			if (p.getItemInHand().getType() == Material.FEATHER) {
				if (!CooldownAPI.hasCooldown(p, kitName)) {
					 CooldownAPI.addCooldown(p, new Cooldown(kitName, 30L));

					p.setAllowFlight(true);
					armors.put(p.getUniqueId(), p.getInventory().getArmorContents());
					p.getInventory().setArmorContents(new ItemStack[] { Cor(Material.LEATHER_BOOTS, Color.WHITE), Cor(Material.LEATHER_LEGGINGS, Color.WHITE), Cor(Material.LEATHER_CHESTPLATE, Color.WHITE), Cor(Material.LEATHER_HELMET, Color.WHITE) });
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 10f, 5f);

					p.updateInventory();
					new BukkitRunnable() {
						int tempo = 5;

						public void run() {
							if (tempo > 0 && tempo != 1) {
								p.sendMessage("§3§lPHANTOM§ §fRestam §c" + tempo + " §fsegundos.");
							}
							if (tempo == 1) {
								p.sendMessage("§3§lPHANTOM §fRestam §c" + tempo + " §fsegundo.");
							}
							if (tempo == 0) {
								p.setAllowFlight(false);
								p.getInventory().setArmorContents((ItemStack[]) armors.get(p.getUniqueId()));
								armors.remove(p.getUniqueId());
								p.sendMessage("§3§lPHANTOM§ §fAcabou o tempo!");
								cancel();
							}
							--tempo;
						}
					}.runTaskTimer(FrostHG.getInstance(), 0, 20);
				} else {
					p.sendMessage(CooldownAPI.getMessage(p));
				}
			}
		}
	}

	@EventHandler
	protected void onPlayerThief(InventoryClickEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (!(e.getWhoClicked() instanceof Player))
			return;

		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() != null) {
			if (e.getCurrentItem().getType() == Material.LEATHER_BOOTS || e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE || e.getCurrentItem().getType() == Material.LEATHER_HELMET || e.getCurrentItem().getType() == Material.LEATHER_LEGGINGS) {
				if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.PHANTOM)) {
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
