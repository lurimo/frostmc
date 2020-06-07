package br.com.frostmc.pvp.util.hability.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.protection.Protection;

public class Hulk implements Listener {

	public String kitName = "Kit " + Kits.HULK.getName();
	
	@EventHandler
	public void hulk(PlayerInteractEntityEvent event) {
		if (!(event.getRightClicked() instanceof Player)) {
			return;
		}
		final Player p = event.getPlayer();
		final Player r = (Player) event.getRightClicked();
		if (Protection.hasProtection(r))
			return;
		if ((p.getItemInHand().getType() == Material.AIR) && (KitAPI.hasKit(p, Kits.HULK.getName())) && (!CooldownAPI.hasCooldown(p, kitName) && (p.getPassenger() == null) 	&& (r.getPassenger() == null))) {
			p.setPassenger(r);
			CooldownAPI.addCooldown(p, new Cooldown(kitName, 15L));
			return;
		}
		if ((p.getItemInHand().getType() == Material.AIR) && (KitAPI.hasKit(p, Kits.HULK.getName())) && (CooldownAPI.hasCooldown(p, kitName) && (p.getPassenger() == null) && (r.getPassenger() == null))) {
			if(CooldownAPI.hasCooldown(p, kitName)) {
				p.sendMessage(CooldownAPI.getMessage(p));
				return;
			} else {
				p.setPassenger(r);
				CooldownAPI.addCooldown(p, new Cooldown(kitName, 15L));
				return;
			}
		}
	}

	@EventHandler
	public static void playerInteract(PlayerInteractEvent event) {
		if (!event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			return;
		}
		Player player = event.getPlayer();
		if ((player.getPassenger() == null) || (!(player.getPassenger() instanceof Player))) {
			return;
		}
		Player pass = (Player) player.getPassenger();
		player.eject();
		pass.damage(0.0D, player);
		pass.setVelocity(new Vector(pass.getVelocity().getX(), 1.0D, pass.getVelocity().getZ()));
	}

}
