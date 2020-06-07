package br.com.frostmc.pvp.listener;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.MessagesType;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.command.staffer.BuildCommand;
import br.com.frostmc.pvp.command.staffer.BuildCommand.BuildModes;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.hability.kits.Gladiator;
import br.com.frostmc.pvp.util.warp.WarpManager;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class PrincipalsListener implements Listener {
	
	@EventHandler
	public void asd(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		for (int i = 1;i <100; i++) {
			player.sendMessage(" ");
		}
		MessagesType.sendTitleMessage(player, "§b§lFROST §f§lPVP", "§fSeja bem-vindo(a), §a" + player.getName());
		player.sendMessage("§6§lSERVER §fVocê foi conectado com sucesso.");
		BuildCommand.buildModes.put(player.getUniqueId(), BuildModes.OFF);
		FrostPvP.scoreboard.add(player.getUniqueId());
		WarpManager.send(player, Warps.SPAWN);
	}
	
	@EventHandler
    protected void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        
        Player player = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        
        if (KitAPI.hasKit(player, Kits.NENHUM.getName())|| KitAPI.hasKit(damager, Kits.NENHUM.getName())) {
        	return;
        }

        Scoreboard scoreboard = damager.getScoreboard();

        if (scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null) return;
        final Objective below = scoreboard.registerNewObjective("lucas", "god");

        String kit = "§fKit: §3" + KitAPI.getKit(player);
        below.setDisplayName(kit);
        below.setDisplaySlot(DisplaySlot.BELOW_NAME);
        
        new BukkitRunnable() {
			public void run() {
				scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
				below.unregister();
			}
        }.runTaskLater(FrostPvP.getPlugin(), 20L);
        
    }
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player player = event.getPlayer();
		FrostPvP.scoreboard.remove(player.getUniqueId());
		Scoreboarding.removeScoreboard(player);
	}
	
	@EventHandler
	public void asd(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		if (!player.getItemInHand().getType().equals((Object) Material.MUSHROOM_SOUP) && !player.getItemInHand().getType().equals((Object) Material.BOWL) && !player.getItemInHand().getType().equals((Object) Material.BROWN_MUSHROOM) && !player.getItemInHand().getType().equals((Object) Material.RED_MUSHROOM)) {
			event.setCancelled(true);
			return;
		} else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler
	public void asd(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (Gladiator.gladArena.containsKey(p.getUniqueId())) {
			if (e.getBlock().getType() != Material.COBBLESTONE) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void asd(ItemSpawnEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(FrostPvP.getPlugin(), new Runnable() {
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
	public void asd(EntityDamageByEntityEvent e) {
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
	public void asd(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	
	public ArrayList<UUID> delay = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType().equals(Material.MUSHROOM_SOUP) && event.getAction().name().contains("RIGHT")) {
			event.setCancelled(true);

			if (player.getHealth() != player.getMaxHealth()) {
				player.setHealth((player.getHealth() + 7.0 > player.getMaxHealth()) ? player.getMaxHealth() : (player.getHealth() + 7.0));
				player.getItemInHand().setType(Material.BOWL);
			}
		} else if ((event.hasItem()) && (event.getItem().getType() == Material.COMPASS) && (event.getAction() != Action.PHYSICAL)) {
			event.setCancelled(true);
			if (delay.contains(player.getUniqueId())) {
				return;
			}
			Player alvo = getRandomPlayer(player);
			if (alvo == null) {
				player.sendMessage("§e§lBÚSSOLA §fNenhum jogador encontrado, bússola direcionada ao spawn.");
				player.setCompassTarget(new Location(Bukkit.getWorld("world"), WarpsAPI.getInConfigLocationIntX("spawn"), WarpsAPI.getInConfigLocationIntY("spawn"), WarpsAPI.getInConfigLocationIntZ("spawn")));
			} else {
				player.sendMessage("§e§lBÚSSOLA §fBússola direcionada para: §a" + alvo.getName());
				player.setCompassTarget(alvo.getLocation());
			}
			delay.add(player.getUniqueId());
			new BukkitRunnable() {
				public void run() {
					delay.remove(player.getUniqueId());
				}
			}.runTaskLater(BukkitMain.getInstance(), 20L);
		}
	}
	
	@SuppressWarnings("deprecation")
	public Player getRandomPlayer(Player p) {
		Player target = null;
		for (Player playerTarget : Bukkit.getOnlinePlayers()) {
			if (playerTarget != null) {
				if (!playerTarget.equals(p)) {
					if (playerTarget.getLocation().distance(p.getLocation()) >= 15.0D) {
						if (target == null) {
							target = playerTarget;
						} else {
							double distanciaAtual = target.getLocation().distance(p.getLocation());
							double novaDistancia = playerTarget.getLocation().distance(p.getLocation());
							if (distanciaAtual > novaDistancia)
								target = playerTarget;
						}
					}
				}
			}
		}
		return target;
	}
	
	@EventHandler
	public void motd(ServerListPingEvent event) {
		event.setMotd("online");
	}

}
