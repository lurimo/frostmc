package br.com.frostmc.hardcoregames.listeners;

import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.material.Dispenser;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;

public class SpectatorListener implements Listener {
	
	@EventHandler
	public void asd(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && FrostHG.getManager().getEspectadores().contains(event.getPlayer().getUniqueId())) {
			Block b = event.getClickedBlock();
			if (b.getState() instanceof DoubleChest || b.getState() instanceof Chest || b.getState() instanceof Hopper || b.getState() instanceof Dispenser
			|| b.getState() instanceof Furnace || b.getState() instanceof Beacon) {
			if (!AdminManager.isAdmin(event.getPlayer()))
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void asd(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId())) {
			if ((event.getMessage().startsWith("/kit"))) {
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void asd(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void asd(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player damager = (Player) event.getDamager();
		Player damaged = (Player) event.getEntity();
		if (AdminManager.isAdmin(damager) || AdminManager.isAdmin(damaged)) {
			event.setCancelled(false);
			return;
		}
		if (FrostHG.getManager().getEspectadores().contains(damager.getUniqueId()) || FrostHG.getManager().getEspectadores().contains(damaged.getUniqueId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (FrostHG.state != State.JOGO)
			return;
		
		if (e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if (FrostHG.getManager().getEspectadores().contains(p.getUniqueId()) || (AdminManager.isAdmin(p)))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void asd(BlockBreakEvent e) {
		if (FrostHG.state != State.JOGO)
			return;
		Player p = e.getPlayer();
		if (AdminManager.isAdmin(p)) {
			e.setCancelled(false);
			return;
		}
		if (FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void asd(BlockPlaceEvent e) {
		if (FrostHG.state != State.JOGO)
			return;
		Player p = e.getPlayer();
		if (AdminManager.isAdmin(p)) {
			e.setCancelled(false);
			return;
		}
		if (FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void asd(PlayerDropItemEvent e) {
		if (FrostHG.getManager().getEspectadores().contains(e.getPlayer().getUniqueId()) || (AdminManager.isAdmin(e.getPlayer()))) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void asd(PlayerPickupItemEvent e) {
		if (FrostHG.getManager().getEspectadores().contains(e.getPlayer().getUniqueId()) || (AdminManager.isAdmin(e.getPlayer())))
			e.setCancelled(true);
	}
	

}
