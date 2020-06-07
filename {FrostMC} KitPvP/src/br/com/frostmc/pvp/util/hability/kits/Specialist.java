package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Specialist implements Listener {
	

	@EventHandler
	public void asd(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if ((KitAPI.hasKit(p, Kits.SPECIALIST.getName()) && ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) && (p.getItemInHand().getType() == Material.BOOK))) {
			p.openEnchanting(p.getLocation(), true);
		}
	}

	@EventHandler
	public void asd(EntityDeathEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player killed = (Player) event.getEntity();
			if ((killed.getKiller() instanceof Player)) {
				Player player = event.getEntity().getKiller();
				if (KitAPI.hasKit(player, Kits.SPECIALIST.getName())) {
					player.setLevel(player.getLevel() + 1);
				}
			}
		}
	}

}
