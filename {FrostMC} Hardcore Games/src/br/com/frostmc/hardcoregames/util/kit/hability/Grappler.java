package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import br.com.frostmc.common.util.cooldown.Cooldown;
import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import net.minecraft.server.v1_7_R4.EntityFishingHook;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntitySnowball;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;

public class Grappler implements Listener {
	
	public String kitName = "Kit " + Kits.GRAPPLER.getName();
	private Map<Player, Cordinha> hooks = new HashMap<>();
	private HashMap<Player, Long> leftClickGrappler = new HashMap<Player, Long>();
	private HashMap<Player, Long> rightClickGrappler = new HashMap<Player, Long>();
	
	@EventHandler
	private void removerCordaAoTrocarSlot(PlayerItemHeldEvent e) {
		if (hooks.containsKey(e.getPlayer())) {
	 	    ((Cordinha)hooks.get(e.getPlayer())).remove();
	 	    hooks.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	private void GrapplerFallNerf(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
	 	      return;
		
		Player player = (Player)e.getEntity();
		if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
			return;
		
	 	if ((hooks.containsKey(player)) && (((Cordinha)hooks.get(player)).isHooked()) && (e.getDamage() > 5.0D))
	 	     e.setDamage(0.0D);   
	}
	
	@EventHandler
	public void asd(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.GRAPPLER)) {
				if (e.getCause() == DamageCause.ENTITY_ATTACK) {
					if (!(e.getDamager() instanceof Player)) {
						return;
					}
					if (!CooldownAPI.hasCooldown(p, kitName)) {
						CooldownAPI.addCooldown(p, new Cooldown(kitName, 5L));
					}
					return;
				}
			}
		}
	}
	 	  
	@EventHandler
	private void removeCorda1(PlayerMoveEvent e) {
		if ((hooks.containsKey(e.getPlayer())) && (!e.getPlayer().getItemInHand().getType().equals(Material.LEASH))) {
	 	    ((Cordinha)hooks.get(e.getPlayer())).remove();
	 	     hooks.remove(e.getPlayer());
		}
	}
	 	  
	@EventHandler
	private void onLeash(PlayerLeashEntityEvent e) {
		if (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.GRAPPLER)) {
	 		e.setCancelled(true);
	 		e.getPlayer().updateInventory();
	 	}
	}
	
	@EventHandler
	private void usar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
	 	if ((p.getItemInHand().getType().equals(Material.LEASH)) && (FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.GRAPPLER))) {
	 	     e.setCancelled(true);
	 	    if (CooldownAPI.hasCooldown(p, kitName)) {
				 p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 5), true);
				 p.sendMessage(CooldownAPI.getMessage(p));
	 	    	 return;
	 	     }
	 	     if ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
				  if (leftClickGrappler.containsKey(p) && leftClickGrappler.get(p) > System.currentTimeMillis())
					  return;
				  
	 	          if (hooks.containsKey(p)) 
	 	            ((Cordinha)hooks.get(p)).remove();
	 	          
	 	          Cordinha nmsHook = new Cordinha(p.getWorld(), ((CraftPlayer)p).getHandle());
	 	          nmsHook.spawn(p.getEyeLocation().add(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY(), p.getLocation().getDirection().getZ()));
	 	          nmsHook.move(p.getLocation().getDirection().getX() * 5.0D, p.getLocation().getDirection().getY() * 5.0D, p.getLocation().getDirection().getZ() * 5.0D);
	 	          hooks.put(p, nmsHook);
	 			  leftClickGrappler.put(p, System.currentTimeMillis() + 250L);
	 	     } else {
	 	    	 if (!hooks.containsKey(p))
	 	             return;
	 	    	 
			     if (rightClickGrappler.containsKey(p) && rightClickGrappler.get(p) > System.currentTimeMillis())
					 return;
	 	    	 
	 	         if (!((Cordinha)hooks.get(p)).isHooked()) 
	 	             return;
	 	          
	 	         rightClickGrappler.put(p, System.currentTimeMillis() + 150L);
	 	         double d = ((Cordinha)hooks.get(p)).getBukkitEntity().getLocation().distance(p.getLocation());
	 	         double t = d;
	 	         double v_x = (1.0D + 0.07000000000000001D * t) * (((Cordinha)hooks.get(p)).getBukkitEntity().getLocation().getX() - p.getLocation().getX()) / t;
	 	         double v_y = (1.0D + 0.03D * t) * (((Cordinha)hooks.get(p)).getBukkitEntity().getLocation().getY() - p.getLocation().getY()) / t;
	 	         double v_z = (1.0D + 0.07000000000000001D * t) * (((Cordinha)hooks.get(p)).getBukkitEntity().getLocation().getZ() - p.getLocation().getZ()) / t;
	 	         Vector v = p.getVelocity();
	 	         v.setX(v_x);
	 	         v.setY(v_y);
	 	         v.setZ(v_z);
	 	         p.setVelocity(v);
	 	         p.playSound(p.getLocation(), Sound.STEP_GRAVEL, 10.0F, 10.0F);
	 	     }
	 	}
	}
	
    public class Cordinha extends EntityFishingHook {

    	private Snowball sb;
        private EntitySnowball controller;
        public EntityHuman owner;
        public Entity hooked;
        public boolean lastControllerDead, isHooked;
      
        public Cordinha(org.bukkit.World world, EntityHuman entityhuman) {
        	super(((CraftWorld)world).getHandle(), entityhuman);
            this.owner = entityhuman;
        }
      
        protected void c() {}
      
        public void h() {
        	
        if ((!this.lastControllerDead) && (this.controller.dead))
             ((Player)this.owner.getBukkitEntity()).sendMessage("§3§lGRAPPLER §fSua corda prendeu em algo.");
        
        this.lastControllerDead = this.controller.dead;
        for (Entity entity : this.controller.world.getWorld().getEntities()) {
             if (!(entity instanceof LivingEntity)) {
            	  continue;
             }	 
             if ((!(entity instanceof Firework)) && (entity.getEntityId() != getBukkitEntity().getEntityId()) && 
             (entity.getEntityId() != this.owner.getBukkitEntity().getEntityId()) && 
             (entity.getEntityId() != this.controller.getBukkitEntity().getEntityId()) && (
             (entity.getLocation().distance(this.controller.getBukkitEntity().getLocation()) < 2.0D) || (((entity instanceof Player)) && 
             (((Player)entity).getEyeLocation().distance(this.controller.getBukkitEntity().getLocation()) < 2.0D)))) {
              this.controller.die();
              this.hooked = entity;
              this.isHooked = true;
              this.locX = entity.getLocation().getX();
              this.locY = entity.getLocation().getY();
              this.locZ = entity.getLocation().getZ();
              this.motX = 0.0D;
              this.motY = 0.04D;
              this.motZ = 0.0D;
             }
        }
        try {
          this.locX = this.hooked.getLocation().getX();
          this.locY = this.hooked.getLocation().getY();
          this.locZ = this.hooked.getLocation().getZ();
          this.motX = 0.0D;
          this.motY = 0.04D;
          this.motZ = 0.0D;
          this.isHooked = true;
        } catch (Exception e) {
        	if (this.controller.dead)
                this.isHooked = true;
            this.locX = this.controller.locX;
            this.locY = this.controller.locY;
            this.locZ = this.controller.locZ;
        }
        }
      
        public void die() {}
      
        public void remove() {
        	super.die();
        }
      
        @SuppressWarnings("deprecation")
        public void spawn(Location location) {
        	this.sb = ((Snowball)this.owner.getBukkitEntity().launchProjectile(Snowball.class));
            this.sb.setVelocity(this.sb.getVelocity().multiply(2.25D));
            this.controller = ((CraftSnowball)this.sb).getHandle();
        
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.controller.getId() });
        
            for (Player p : Bukkit.getOnlinePlayers()) 
                 ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

            ((CraftWorld)location.getWorld()).getHandle().addEntity(this);
        }
      
        public boolean isHooked() {
        	return this.isHooked;
        }
      
        public void setHookedEntity(Entity damaged) {
        	this.hooked = damaged;
        }
    }

}
