package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.GladUtils;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Gladiator implements Listener {
	
	public static HashMap<String, GladUtils> glads = new HashMap<>();
	public static HashMap<UUID, String> gladArena = new HashMap<>();
	public static List<String> arenaEmUso = new ArrayList<>();
	
	@EventHandler
	private void fall(EntityDamageEvent e) {
		if (e.isCancelled())
			return;
		if (FrostHG.state != State.JOGO)
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
		if (FrostHG.state != State.JOGO)
			return;
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player entity = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			
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
		if (FrostHG.state != State.JOGO)
			return;
	    Player p = e.getPlayer();
	    if ((p.getItemInHand().getType().equals(Material.IRON_FENCE)) && (FrostHG.getManager().getKitAPI().hasKit(p, Kits.GLADIATOR))) {
	    	 if (gladArena.containsKey(p.getUniqueId())) {
	        	 p.sendMessage("§3§lGLADIATOR §fVocê já esta em um gladiator.");
	        	 return;
	         }
	         Player p1 = (Player)e.getRightClicked();
	    	 if (gladArena.containsKey(p1.getUniqueId())) {
	        	 p.sendMessage("§3§lGLADIATOR §fO jogador já esta em um gladiator.");
	        	 return;
	         }
	         Location toGlad = getLocationForGlad(p.getLocation());
	         if (toGlad == null) {
	   		     p.sendMessage("§3§lGLADIATOR §fVocê não pode criar sua arena aqui.");
	        	 return;
	         }
	 		 int id = getArenaID();
			 if ((arenaEmUso.contains("arena" + id)) || (id == 0)) {
				  p.sendMessage("§3§lGLADIATOR §fVocê não pode criar sua arena aqui.");
				  return;
			 }
			 arenaEmUso.add("arena" + id);
	         gladArena.put(p.getUniqueId(), "arena" + id);
	         gladArena.put(p1.getUniqueId(), "arena" + id);
	         
	         removerBlocos(toGlad);
	         criarGladiator(toGlad);
	         
			 glads.put("arena" + id, new GladUtils(toGlad.getBlock(), id, p, p1));
	         
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
		     }.runTaskTimer(FrostHG.getInstance(), 0L, 20L);
	    }
	}
	
	public static Location getLocationForGlad(Location loc) {
		loc.setY(110);
	    boolean hasGladi = true;
	    while (hasGladi) {
	      hasGladi = false;
	      boolean stop = false;
	      for (double x = -8.0D; x <= 8.0D; x += 1.0D) {
	      for (double z = -8.0D; z <= 8.0D; z += 1.0D) {
	      for (double y = 0.0D; y <= 10.0D; y += 1.0D) {
	           Location l = new Location(loc.getWorld(), loc.getX() + x, 110.0D + y, loc.getZ() + z);
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
    	for (int bX = -10; bX <= 10; bX++)
        for (int bZ = -10; bZ <= 10; bZ++)
        for (int bY = -1; bY <= 12; bY++)
             if (bY == -1) {
                 cuboid.add(loc.clone().add(bX, bY, bZ));
             } else if ((bX == -10) || (bZ == -10) || (bX == 10) || (bZ == 10)) {
                 cuboid.add(loc.clone().add(bX, bY, bZ));
             }
         for (Location loc1 : cuboid) {
              loc1.getBlock().setType(Material.GLASS);
       	      loc1.getBlock().setData((byte) 0);
         }
	}
	
	public static void removerBlocos(Location loc) {
		List<Location> cuboid = new ArrayList<>();
        cuboid.clear();
    	for (int bX = -10; bX <= 10; bX++)
        for (int bZ = -10; bZ <= 10; bZ++)
        for (int bY = -1; bY <= 12; bY++)
             cuboid.add(loc.clone().add(bX, bY, bZ));
    	
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
	public void onPlace(BlockPlaceEvent e) {
    	if ((e.getBlock().getType().equals(Material.IRON_FENCE)) && (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.GLADIATOR)))
    		 e.setCancelled(true);
    }

}
