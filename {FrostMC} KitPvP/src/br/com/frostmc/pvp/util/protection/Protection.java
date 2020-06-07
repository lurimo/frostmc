package br.com.frostmc.pvp.util.protection;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class Protection implements Listener {

	public static ArrayList<Player> invencible = new ArrayList<>();

	public static boolean hasProtection(Player player) {
		return invencible.contains(player);
	}

	public static void setProtection(Player player) {
		if (!hasProtection(player)) {
			invencible.add(player);
			player.sendMessage("�8[�e�lPROTE��O�8] �fVoc� �aganhou�f a prote��o da warp �a" + WarpsAPI.getWarp(player) + "�f.");
		}
	}

	public static void removeProtection(Player player) {
		if (hasProtection(player)) {
			invencible.remove(player);
			player.sendMessage("�8[�e�lPROTE��O�8] �fVoc� �cperdeu�f a prote��o da warp �a" + WarpsAPI.getWarp(player) + "�f.");
		}
	}

	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (hasProtection(player)) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if ((e.getCause().equals(EntityDamageEvent.DamageCause.FALL))) {
				if (hasProtection(p)) {
					e.setCancelled(true);
				}
			} else if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
				if (WarpsAPI.isInWarp(p, Warps.ARENA)) {
					p.damage(20.0D);
					return;
				}
			}
			if (p.getInventory().getChestplate() != null) {
				p.getInventory().getChestplate().getItemMeta().spigot().setUnbreakable(true);
			}
			if (p.getInventory().getBoots() != null) {
				p.getInventory().getBoots().getItemMeta().spigot().setUnbreakable(true);
			}
			if (p.getInventory().getLeggings() != null) {
				p.getInventory().getLeggings().getItemMeta().spigot().setUnbreakable(true);
			}
			if (p.getInventory().getHelmet() != null) {
				p.getInventory().getHelmet().getItemMeta().spigot().setUnbreakable(true);
			}
		}
	}

}
