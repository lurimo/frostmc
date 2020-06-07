package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class Fisherman implements Listener {
	
	@EventHandler
	public void asd(PlayerFishEvent e) {
		Player p = e.getPlayer();
		Entity caught = e.getCaught();
		Block block = e.getHook().getLocation().getBlock();
		if(caught != null && caught != block && KitAPI.hasKit(p, Kits.FISHERMAN.getName())) {
			caught.teleport(p.getPlayer().getLocation());
			p.getItemInHand().setDurability((short) -27008);
		}
	}
}
