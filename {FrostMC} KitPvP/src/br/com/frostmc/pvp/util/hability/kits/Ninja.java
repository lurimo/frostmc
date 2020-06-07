package br.com.frostmc.pvp.util.hability.kits;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class Ninja implements Listener {
	
	public static HashMap<Player, Player> a = new HashMap<>();
	public static HashMap<Player, Long> b = new HashMap<>();
	public static FrostPvP plugin;

	public String kitName = "Kit " + Kits.NINJA.getName();
	
	@EventHandler
	public void a(EntityDamageByEntityEvent paramEntityDamageByEntityEvent) {
		if (((paramEntityDamageByEntityEvent.getDamager() instanceof Player)) && ((paramEntityDamageByEntityEvent.getEntity() instanceof Player))) {
			final Player localPlayer1 = (Player) paramEntityDamageByEntityEvent.getDamager();
			Player localPlayer2 = (Player) paramEntityDamageByEntityEvent.getEntity();
			if (KitAPI.hasKit(localPlayer1, Kits.NINJA.getName())) {
				a.put(localPlayer1, localPlayer2);

			}
		}
	}

	@EventHandler
	public void a(PlayerToggleSneakEvent paramPlayerToggleSneakEvent) {
		Player localPlayer1 = paramPlayerToggleSneakEvent.getPlayer();
		if ((paramPlayerToggleSneakEvent.isSneaking()) && (KitAPI.hasKit(localPlayer1, Kits.NINJA.getName())) && (CooldownAPI.hasCooldown(localPlayer1, kitName))) {
			if (CooldownAPI.hasCooldown(localPlayer1, kitName)) {
				localPlayer1.sendMessage(CooldownAPI.getMessage(localPlayer1));
				return;
			} else {
				if ((paramPlayerToggleSneakEvent.isSneaking()) && (KitAPI.hasKit(localPlayer1, Kits.NINJA.getName())) && (a.containsKey(localPlayer1))) {
					Player localPlayer2 = (Player) a.get(localPlayer1);
					if ((localPlayer2 != null) && (!localPlayer2.isDead())) {
						if (Gladiator.gladArena.containsKey(localPlayer2.getUniqueId())) {
							localPlayer1.sendMessage("§3§lNINJA §fO último jogador hitado está em um Gladiato no momento.");
							return;
						}
						if (Gladiator.gladArena.containsKey(localPlayer1.getUniqueId())) {
							localPlayer1.sendMessage("§3§lNINJA §fVocê não pode utilizar o kit Ninja em Gladiator.");
							return;
						}
						@SuppressWarnings("unused")
						String str = null;
						if (b.get(localPlayer1) != null) {
							long l = ((Long) b.get(localPlayer1)).longValue() - System.currentTimeMillis();
							DecimalFormat localDecimalFormat = new DecimalFormat("##");
							int i = (int) l / 1000;
							str = localDecimalFormat.format(i);
						}
					}
					if ((b.get(localPlayer1) == null)
							|| (((Long) b.get(localPlayer1)).longValue() < System.currentTimeMillis())) {
						if (localPlayer1.getLocation().distance(localPlayer2.getLocation()) < 85.0D) {
							localPlayer1.teleport(localPlayer2.getLocation());
							CooldownAPI.addCooldown(localPlayer1, new Cooldown(kitName, 6L));
							b.put(localPlayer1, Long.valueOf(System.currentTimeMillis() + 10000L));
						} else {
							localPlayer1.sendMessage("§3§lNINJA §fO último jogador hitado está muito longe.");
						}
					}
				}
			return;
			}
		}
		if ((paramPlayerToggleSneakEvent.isSneaking()) && (KitAPI.hasKit(localPlayer1, Kits.NINJA.getName())) && (a.containsKey(localPlayer1))) {
			Player localPlayer2 = (Player) a.get(localPlayer1);
			if ((localPlayer2 != null) && (!localPlayer2.isDead())) {
				if (WarpsAPI.isInWarp(localPlayer2, Warps.SPAWN))
					return;
				if (Gladiator.gladArena.containsKey(localPlayer2.getUniqueId())) {
					localPlayer1.sendMessage("§3§lNINJA §fO último jogador hitado está em um Gladiator no momento.");
					return;
				}
				if (Gladiator.gladArena.containsKey(localPlayer1.getUniqueId())) {
					localPlayer1.sendMessage("§3§lNINJA §fVocê não pode utilizar o kit Ninja em Gladiator.");
					return;
				}
				@SuppressWarnings("unused")
				String str = null;
				if (b.get(localPlayer1) != null) {
					long l = ((Long) b.get(localPlayer1)).longValue() - System.currentTimeMillis();
					DecimalFormat localDecimalFormat = new DecimalFormat("##");
					int i = (int) l / 1000;
					str = localDecimalFormat.format(i);
				}
			}
			if ((b.get(localPlayer1) == null) || (((Long) b.get(localPlayer1)).longValue() < System.currentTimeMillis())) {
				if (localPlayer1.getLocation().distance(localPlayer2.getLocation()) < 85.0D) {
					localPlayer1.teleport(localPlayer2.getLocation());
					CooldownAPI.addCooldown(localPlayer1, new Cooldown(kitName, 6L));
					b.put(localPlayer1, Long.valueOf(System.currentTimeMillis() + 10000L));
				} else {
					localPlayer1.sendMessage("§3§lNINJA §fO último jogador hitado está muito longe.");
				}
			}
		}
	}
}