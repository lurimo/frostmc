package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Thor implements Listener{
	
	public String kitName = "Kit " + Kits.THOR.getName();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void Habilidade(PlayerInteractEvent e) {
		if ((e.getPlayer().getItemInHand().getType().equals(Material.WOOD_AXE)) && (e.getAction().name().contains("RIGHT")) && (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.THOR))) {
			if (!CooldownAPI.hasCooldown(e.getPlayer(), kitName)) {
				 Location loc = e.getPlayer().getTargetBlock(null, 30).getLocation();
				 if (!loc.getBlock().getType().equals(Material.AIR)) {
					  e.getPlayer().getWorld().strikeLightning(loc);
					  loc.getBlock().getRelative(BlockFace.UP).setType(Material.FIRE);
					  CooldownAPI.addCooldown(e.getPlayer(), new Cooldown(kitName, 6L));
				}
			} else {
				e.getPlayer().sendMessage(CooldownAPI.getMessage(e.getPlayer()));
			}
		}
	}
}
