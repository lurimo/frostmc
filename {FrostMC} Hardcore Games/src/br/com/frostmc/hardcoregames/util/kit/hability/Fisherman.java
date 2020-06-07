package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Fisherman implements Listener{

	@EventHandler
	public void onFisherman(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if (!FrostHG.getManager().getKitAPI().hasKit(p, Kits.FISHERMAN)) {
			return;
		}
		if (e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
			Entity c = e.getCaught();
			World w = p.getLocation().getWorld();
			double x = p.getLocation().getBlockX() + 0.5D;
			double y = p.getLocation().getBlockY();
			double z = p.getLocation().getBlockZ() + 0.5D;
			float yaw = c.getLocation().getYaw();
			float pitch = c.getLocation().getPitch();
			Location loc = new Location(w, x, y, z, yaw, pitch);
			c.teleport(loc);
			p.getItemInHand().setDurability((short) -27008);
		}
	}

}
