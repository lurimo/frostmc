package br.com.frostmc.pvp.util.hability.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.hability.kits.glad.GladUtils;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class Gladiator implements Listener {

	public static HashMap<String, GladUtils> glads = new HashMap<>();
	public static HashMap<UUID, String> gladArena = new HashMap<>();
	public static HashMap<Player, String> gladFighting = new HashMap<>();
	public static List<String> arenaEmUso = new ArrayList<>();
	
	@EventHandler
	private void fall(EntityDamageEvent e) {
		if (e.isCancelled())
			return;
		if(e.getEntity() instanceof Player) {
			if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
				Player entity = (Player) e.getEntity();
				if (gladArena.containsKey(entity.getUniqueId())) {
					Damageable d = entity;	
					double hp = d.getHealth() - e.getDamage();
					if (hp <= 6.0D) {
						e.setCancelled(true);
					} else {
						e.setDamage(e.getDamage() - 2.0D);
					}
				}
			}
		}
	}
	
	@EventHandler
	private void x1(EntityDamageByEntityEvent e) {
		if (e.isCancelled())
			return;
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player entity = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			
			if (!WarpsAPI.isInWarp(entity, Warps.ARENA) && !WarpsAPI.isInWarp(damager, Warps.ARENA)) {
				return;
			}
			
			if (gladArena.containsKey(entity.getUniqueId())) {
				if (!gladArena.containsKey(damager.getUniqueId())) {
					e.setCancelled(true);
					return;
				}
				String arena = gladArena.get(entity.getUniqueId());
				String arena2 = gladArena.get(damager.getUniqueId());
				if (!arena.equals(arena2)) {
					e.setCancelled(true);
					return;
				}
			}
			
			if (gladArena.containsKey(damager.getUniqueId())) {
				if (!gladArena.containsKey(entity.getUniqueId())) {
					e.setCancelled(true);
					return;
				}
				String arena = gladArena.get(entity.getUniqueId());
				String arena2 = gladArena.get(damager.getUniqueId());
				if (!arena.equals(arena2)) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	private void Habilidade(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player))
			return;
	    Player p = e.getPlayer();
	    if (WarpsAPI.isInWarp(p, Warps.SPAWN)) {
			return;
		}
	    if ((p.getItemInHand().getType().equals(Material.IRON_FENCE)) && (KitAPI.hasKit(p, Kits.GLADIATOR.getName()))) {
	    	 if (gladArena.containsKey(p.getUniqueId())) {
	        	 p.sendMessage("�3�lGLADIATOR �fVoc� j� esta em um gladiator.");
	        	 return;
	         }
	         Player p1 = (Player)e.getRightClicked();
	         if (!WarpsAPI.isInWarp(p1, Warps.ARENA)) {
	        	 return;
	    	 }
	    	 if (gladArena.containsKey(p1.getUniqueId())) {
	        	 p.sendMessage("�3�lGLADIATOR �fO jogador j� esta em um gladiator.");
	        	 return;
	         }
	         Location toGlad = getLocationForGlad(p.getLocation());
	         if (toGlad == null) {
	   		     p.sendMessage("�3�lGLADIATOR �fVoc� n�o pode criar sua arena aqui.");
	        	 return;
	         } 
	 		 int id = getArenaID();
			 if ((arenaEmUso.contains("arena" + id)) || (id == 0)) {
				  p.sendMessage("�3�lGLADIATOR �fVoc� n�o pode criar sua arena aqui.");
				  return;
			 }
			 arenaEmUso.add("arena" + id);
	         gladArena.put(p.getUniqueId(), "arena" + id);
	         gladArena.put(p1.getUniqueId(), "arena" + id);
	         
	         removerBlocos(toGlad);
	         criarGladiator(toGlad);
	         
			 glads.put("arena" + id, new GladUtils(toGlad.getBlock(), id, p, p1));
	         gladFighting.put(p1, p.getName());
	         gladFighting.put(p, p1.getName());
	 	     Location l1 = new Location(toGlad.getWorld(), toGlad.getX() + 6.5D, toGlad.getY() + 1.500, toGlad.getZ() + 6.5D);
		     l1.setYaw(135.0F);
		     Location l2 = new Location(toGlad.getWorld(), toGlad.getX() - 5.5D, toGlad.getY() + 1.500, toGlad.getZ() - 5.5D);
		     l2.setYaw(315.0F);
		     p1.setNoDamageTicks(40);
		     p.setNoDamageTicks(40);
		     p.setFallDistance(0);
		     p1.setFallDistance(0);
		     p.teleport(l1);
		     p1.teleport(l2);
		     p1.setNoDamageTicks(40);
		     p.setNoDamageTicks(40);
		     p.setFallDistance(0);
		     p1.setFallDistance(0);
		     new BukkitRunnable() {
		     public void run() {
		    	 if (glads.containsKey("arena" + id)) {
		    		 GladUtils glad = glads.get("arena" + id);
		    		 if (glad.isCancelar()) {
		    			 gladArena.remove(p.getUniqueId());
		    			 gladArena.remove(p1.getUniqueId());
		    			 arenaEmUso.remove("arena" + id);
		    			 glads.remove("arena" + id);
		    			 cancel();
		    			 return;
		    		 }
		    		 glad.check();
		    	 }
		     }
		     }.runTaskTimer(FrostPvP.getInstance(), 0L, 20L);
	    }
	}
	
	public static Location getLocationForGlad(Location loc) {
		loc.setY(180);
	    boolean hasGladi = true;
	    while (hasGladi) {
	      hasGladi = false;
	      boolean stop = false;
	      for (double x = -8.0D; x <= 8.0D; x += 1.0D) {
	      for (double z = -8.0D; z <= 8.0D; z += 1.0D) {
	      for (double y = 0.0D; y <= 10.0D; y += 1.0D) {
	           Location l = new Location(loc.getWorld(), loc.getX() + x, y, loc.getZ() + z);
	           if (l.getBlock().getType() != Material.AIR) {
	               hasGladi = true;
	               loc = new Location(loc.getWorld(), loc.getX() + 20.0D, 110.0D, loc.getZ());
	       	 	   if (loc.getBlockX() >= 450 || loc.getBlockX() <= -450 ||loc.getBlockZ() >= 450 || loc.getBlockZ() <= -450)
		               loc = new Location(loc.getWorld(), loc.getX() - 20.0D, 110.0D, loc.getZ() - 20.0D);
	       	 	       if (loc.getBlock().getType() != Material.AIR || loc.getBlockX() >= 450 || loc.getBlockX() <= -450 ||loc.getBlockZ() >= 450 || loc.getBlockZ() <= -450)
	       	 	    	   loc = null;
	               stop = true;
	           }
	           if (stop)
	               break;
	      }
	      if (stop)
	          break;
	      }
	      if (stop)
	          break;
	      }
	    }
		return loc;
	}
	
	public static int getArenaID() {
		int arenaID = 0;
		for (int i = 1; i <= 55; i++) {
		     if (!arenaEmUso.contains("arena" + i)) {
				 arenaID = i;
				 break;
		     }
		}
		return arenaID;
	}
	
	@SuppressWarnings("deprecation")
	public static void criarGladiator(Location loc) {
		List<Location> cuboid = new ArrayList<>();
        cuboid.clear();
        int bY;
		for (int bX = -10; bX <= 10; bX++) {
			for (int bZ = -10; bZ <= 10; bZ++) {
				for (bY = -1; bY <= 10; bY++) {
					if (bY == 10) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if (bY == -1) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					}
				}
			}
		}
		
		for (Location loc1 : cuboid) {
            loc1.getBlock().setType(Material.GLASS);
            loc1.getBlock().setData((byte) 0);
		}
	}
	
	public static void removerBlocos(Location loc) {
		List<Location> cuboid = new ArrayList<>();
		cuboid.clear();
		int bY;
		for (int bX = -10; bX <= 10; bX++) {
			for (int bZ = -10; bZ <= 10; bZ++) {
				for (bY = -1; bY <= 10; bY++) {
					if (bY == 10) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if (bY == -1) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					}
				}
			}
		}
		for (Location loc1 : cuboid) 
   		 loc1.getBlock().setType(Material.AIR);
	}
	  
    @EventHandler
	public void Break(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.GLASS))
	    if (gladArena.containsKey(e.getPlayer().getUniqueId()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onSpongePlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (KitAPI.hasKit(p, Kits.GLADIATOR.getName()) && gladArena.containsKey(p.getUniqueId())) {
			if (p.getItemInHand().getType() != Material.COBBLESTONE) {
				return;
			}
			int spongesleft = p.getItemInHand().getAmount();
			e.setCancelled(true);
			Location placed = e.getBlock().getLocation();
			org.bukkit.World w = placed.getWorld();
			double x = placed.getX();
			double y = placed.getY();
			double z = placed.getZ();
			final Location sponge = new Location(w, x, y, z);
			final Material block = e.getBlockReplacedState().getType();
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FrostPvP.plugin, new Runnable() {
				public void run() {
					sponge.getBlock().setType(Material.COBBLESTONE);
				}
			}, 1L);
			if (p.getItemInHand().getAmount() == 1) {
				p.setItemInHand(new ItemStack(Material.AIR));
			}
			p.getItemInHand().setAmount(spongesleft - 1);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FrostPvP.plugin, new Runnable() {
				public void run() {
					sponge.getBlock().setType(block);
				}
			}, 250L);
		}
	}

	@EventHandler
	public void processocommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (gladArena.containsKey(p.getUniqueId())) {
			if (!e.getMessage().toLowerCase().startsWith("/report") && !e.getMessage().toLowerCase().startsWith("/sc") && !e.getMessage().toLowerCase().startsWith("/score") && !e.getMessage().toLowerCase().startsWith("/tag") && !e.getMessage().toLowerCase().startsWith("/ban")) {
				if (!BukkitMain.getPermissions().isMod(p)) {
					e.setCancelled(true);
					p.sendMessage("�3�lGLADIATOR �fEsse comando esta bloqueado durante o gladiator.");
					return;
				}
			}
		}
	}

}
