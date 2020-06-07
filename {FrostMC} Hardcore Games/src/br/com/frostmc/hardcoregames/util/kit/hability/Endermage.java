package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Endermage implements Listener {
	
	public String kitName = "Kit " + Kits.ENDERMAGE.getName();
	
	@EventHandler
	private void Habilidade(PlayerInteractEvent e) {
		if (FrostHG.state == State.INICIANDO)
			return;
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
		    if ((e.getPlayer().getItemInHand().getType().equals(Material.NETHER_BRICK_ITEM)) && (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.ENDERMAGE))) {
				 if (e.getClickedBlock().getType() != Material.ENDER_PORTAL_FRAME) {
					 Player p = e.getPlayer();
				     if (!CooldownAPI.hasCooldown(p, kitName)) {
					     final Block b = e.getClickedBlock();
					     final BlockState bs = b.getState();
					     if (b.getType().equals(Material.BEDROCK)) {
						     p.sendMessage("§3§lENDERMAGE §fVocê não pode puxar neste bloco.");
						     return;
					     }
					     if (p.getLocation().getBlockY() > 128) {
						     p.sendMessage("§3§lENDERMAGE §fVocê não pode puxar nesta altura.");
						     return;
					     }
					     b.setType(Material.ENDER_PORTAL_FRAME);
					     CooldownAPI.addCooldown(p, new Cooldown(kitName, 5L));
				         new BukkitRunnable() {
					     int segundos = 5;
					     Location portal2 = b.getLocation().clone().add(0.5, 1.5, 0.5);
					     public void run() {
					    	 ArrayList<Player> players = getNearbyPlayers(p, portal2);
						     if (!p.isOnline() || calculateDistance(p.getLocation(), portal2) > 50) {
							     if (!p.isOnline())
								     cancel();
							     resetBlock(b, bs);
						     }
							 if (!players.isEmpty()) {
								 for (Player pl : players) {
									  pl.setFallDistance(0);
									  pl.setNoDamageTicks(110);
									  pl.teleport(portal2);
									  p.sendMessage("§c§lENDERMAGE §fVocê puxou: " + pl.getName());
									  pl.sendMessage("§c§lENDERMAGE §fVocê foi puxado pelo " + p.getName());
									  pl.sendMessage("§c§lENDERMAGE §fVocê esta invencível por 5 segundos");
								 }
								 p.setFallDistance(0);
								 p.setNoDamageTicks(110);
								 p.teleport(portal2);
								 p.sendMessage("§c§lENDERMAGE §fVocê esta invencível por 5 segundos");
								 resetBlock(b, bs);
								 cancel();
							 }
							 if (segundos == 0) {
								 resetBlock(b, bs);
								 cancel();
							 }
							 segundos--;
					     }
				         }.runTaskTimer(FrostHG.getInstance(), 0L, 20L);
				     }
				 }
		    }
		}
	}
	
	private void resetBlock(Block b, BlockState bs) {
		 new BukkitRunnable() {
		 @SuppressWarnings("deprecation")
		public void run() {
			 b.setType(bs.getType());
		     b.setData(bs.getBlock().getData());
		 }
		 }.runTaskLater(FrostHG.getInstance(), 200L);
	}
	
	private ArrayList<Player> getNearbyPlayers(Player p2, Location portal) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player p : FrostHG.getManager().getAliveGamers()) {
			 if ((p.equals(p2)) || (!isEnderable(portal, p.getLocation())) || (FrostHG.getManager().getKitAPI().hasKit(p, Kits.ENDERMAGE))
			 || (FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) || (AdminManager.isAdmin(p)) || (p.getLocation().getBlockY() > 128))
				 continue;
			 
			players.add(p);
		}
		return players;
	}

	private boolean isEnderable(Location portal, Location player) {
		return (Math.abs(portal.getX() - player.getX()) < 2.0D) && (Math.abs(portal.getZ() - player.getZ()) < 2.0D)
		&& (Math.abs(portal.getY() - player.getY()) > 2.0D);
	}
	
	public int calculateDistance(Location a, Location b) {
		int distance = 0, x1 = a.getBlockX(), x2 = b.getBlockX(), z1 = a.getBlockZ(), z2 = b.getBlockZ();
		if (x1 != x2) {
		if (x1 < x2) {
			for (int i = x1; i <= x2 - 1; i++)
				 distance++;
		} else {
			for (int i = x2; i <= x1 - 1; i++)
				 distance++;
		}
		}
		if (z1 != z2) {
		if (z1 < z2) {
			for (int i = z1; i <= z2 - 1; i++) 
				 distance++;
		} else {
			for (int i = z2; i <= z1 - 1; i++) 
				distance++;	
		}
		}
		return distance;
	}

}
