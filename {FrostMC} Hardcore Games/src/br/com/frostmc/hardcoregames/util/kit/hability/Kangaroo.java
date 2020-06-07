package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Kangaroo implements Listener {
	
	public String kitName = "Kit " + Kits.KANGAROO.getName();
	public HashMap<Player, Integer> Pulo = new HashMap<Player, Integer>();
	
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.KANGAROO)) {
			if (p.getItemInHand().getType() == Material.FIREWORK) {
				event.setCancelled(true);

				if (!CooldownAPI.hasCooldown(p, kitName)) {
					if (!(Pulo.containsKey(p))) {
						if (!(p.isSneaking())) {
							if (!((CraftPlayer) p).getHandle().onGround) {
								Pulo.put(p, 1);
								p.setVelocity(new Vector(p.getVelocity().getX(), 1, p.getVelocity().getZ()));
							} else {
								p.setVelocity(new Vector(p.getVelocity().getX(), 1, p.getVelocity().getZ()));
							}
						} else {
							if (!((CraftPlayer) p).getHandle().onGround) {
								p.setVelocity(p.getLocation().getDirection().multiply(1.5D));
								p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
								Pulo.put(p, 1);
							} else {
								p.setVelocity(p.getLocation().getDirection().multiply(1.5D));
								p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
							}
						}
					}
				} else {
					p.sendMessage(CooldownAPI.getMessage(p));
				}
			}
		}
	}

	@EventHandler
	public void landed(PlayerMoveEvent e) {
		if (!(e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)) {
			if (Pulo.containsKey(e.getPlayer())) {
				Pulo.remove(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void gotdamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.KANGAROO)) {
				if (e.getCause() == DamageCause.ENTITY_ATTACK) {
					if (!(e.getDamager() instanceof Player)) {
						return;
					}
					if (!CooldownAPI.hasCooldown(p, kitName)) {
						CooldownAPI.addCooldown(p, new Cooldown(kitName, 5L));
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void entitydamage(EntityDamageEvent e) {
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (FrostHG.state!= State.JOGO)
					return;
				if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.KANGAROO)) {
					if (e.getDamage() > 7) {
						e.setDamage(7.0);
					} else {
						e.setDamage(e.getDamage());
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public boolean isOnGround(Player p) {
		Location l = p.getLocation();
		l = l.add(0, -1, 0);
		return l.getBlock().getState().getTypeId() != 0;
	}

}
