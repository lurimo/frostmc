package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Thor implements Listener {

	public String kitName = "Kit " + Kits.THOR.getName();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ThorKit(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((p.getItemInHand().getType() == Material.WOOD_AXE) && (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (KitAPI.hasKit(p, Kits.THOR.getName()))) {
			if(CooldownAPI.hasCooldown(p, kitName)) {
				p.sendMessage(CooldownAPI.getMessage(p));
				return;
			}
			CooldownAPI.addCooldown(p, new Cooldown(kitName, 7L));
			Location loc = p.getTargetBlock(null, 70).getLocation();
			p.getWorld().strikeLightning(loc);
			e.setCancelled(true);
			p.damage(0.0D);
		}
	}

}
