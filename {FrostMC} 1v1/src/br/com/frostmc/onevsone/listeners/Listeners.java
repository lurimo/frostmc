package br.com.frostmc.onevsone.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.frostmc.onevsone.Frost1v1;
import br.com.frostmc.onevsone.commands.staffer.BuildCommand;
import br.com.frostmc.onevsone.commands.staffer.BuildCommand.BuildModes;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;
import br.com.frostmc.onevsone.util.MessagesType;
import br.com.frostmc.onevsone.util.warp.WarpManager;

public class Listeners implements Listener {

	@EventHandler
	public void asd(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		for (int i = 1;i <100; i++) {
			player.sendMessage(" ");
		}
		MessagesType.sendTitleMessage(player, "�b�lFrost�f�l1v1");
		MessagesType.sendSubTitleMessage(player, "�fDesafie seus oponentes!");
		MessagesType.sendActionBarMessage(player, "�fConectado com sucesso.");
		player.sendMessage("�8[�b�lFROST�F�LMC�8] �fVoc� foi conectado com sucesso.");
		Frost1v1.scoreboard.add(player.getUniqueId());
		BuildCommand.buildModes.put(player.getUniqueId(), BuildModes.ON);
		WarpManager.send(player);
	}
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		Frost1v1.scoreboard.remove(player.getUniqueId());
		Scoreboarding.removeScoreboard(player);
	}
	
	@EventHandler
	public void asd(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void asd(ItemSpawnEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Frost1v1.getPlugin(), new Runnable() {
			public void run() {
				e.getEntity().remove();
				e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation(), Effect.SMOKE, 10);
			}
		}, 10L);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void asd(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getType() != Material.BOWL && e.getItemDrop().getItemStack().getType() != Material.MUSHROOM_SOUP && e.getItemDrop().getItemStack().getType() != Material.getMaterial(39) && e.getItemDrop().getItemStack().getType() != Material.getMaterial(40)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player)) {
			Player d = (Player) e.getDamager();
			if ((d.getItemInHand().getType() == Material.DIAMOND_SWORD) || (d.getItemInHand().getType() == Material.STONE_SWORD) || (d.getItemInHand().getType() == Material.WOOD_SWORD) || (d.getItemInHand().getType() == Material.STONE_SWORD) || (d.getItemInHand().getType() == Material.IRON_SWORD) || (d.getItemInHand().getType() == Material.GOLD_SWORD) || (d.getItemInHand().getType() == Material.FISHING_ROD) || (d.getItemInHand().getType() == Material.DIAMOND_AXE) || (d.getItemInHand().getType() == Material.GOLD_AXE) || (d.getItemInHand().getType() == Material.STONE_AXE) || (d.getItemInHand().getType() == Material.WOOD_AXE) || (d.getItemInHand().getType() == Material.IRON_AXE)) {
				d.getItemInHand().setDurability((short) 0);
				d.updateInventory();
			}
		}
	}
	
	@EventHandler
	public void asd(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void asd(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void asd(CreatureSpawnEvent e) {
		if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void soup(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Damageable hp = p;
		if (hp.getHealth() != 20.0D) {
			if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) && (p.getItemInHand().getTypeId() == 282)) {
				p.setHealth(hp.getHealth() + 7 > hp.getMaxHealth() ? hp.getMaxHealth() : hp.getHealth() + 7);
				p.getItemInHand().setType(Material.BOWL);
			}
		}
	}
	
	@EventHandler
	public void motd(ServerListPingEvent event) {
		event.setMotd("online");
	}

}
