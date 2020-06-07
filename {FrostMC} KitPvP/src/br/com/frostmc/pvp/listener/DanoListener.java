package br.com.frostmc.pvp.listener;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.pvp.util.combatlog.Combat;
import br.com.frostmc.pvp.util.combatlog.CombatLog;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;

public class DanoListener implements Listener {

	static HashMap<Material, Double> damageMaterial = new HashMap<>();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
	    if (!(e.getDamager() instanceof Player))
	         return;
	    
		if (e.isCancelled())
			return;
	    
	    Player d = (Player)e.getDamager();
	    double dano = 1.0;
		ItemStack itemStack = d.getItemInHand();
		if (itemStack != null) {
			dano = damageMaterial.get(itemStack.getType());
			if (itemStack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
				if (new Random().nextInt(100) <= 30) {
				    dano+= itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
				} else {
	        		dano += 1.0D;
				}
			}
		}
	    if (itemStack.getType().equals(Material.IRON_SWORD)) {
			if (new Random().nextInt(100) <= 30)
				dano+=1.0D;
	    } else if (itemStack.getType().equals(Material.DIAMOND_SWORD)){
			if (new Random().nextInt(100) <= 35)
				dano+=1.0D;
	    }
	    
		for (PotionEffect effect : d.getActivePotionEffects()) {
			 if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
			 	 dano += ((effect.getAmplifier() + 1) * 2);
			 } else if (effect.getType().equals(PotionEffectType.WEAKNESS)) {
				 dano -= (effect.getAmplifier() + 1);
			 }
		}
		
	    if (isCrital(d)) {
	        dano += 1.0D;
	    }
	   
		if (e.getEntity() instanceof Player) {
		  	Player p = (Player)e.getEntity();
			if (KitAPI.hasKit(p, Kits.TURTLE.getName()) && (p.isSneaking())) {
				e.setDamage(3.0D);
				makeCombatLog(d, p);
				return;
			}
			
			if (KitAPI.hasKit(d, Kits.CRITICAL.getName())) {
				Random r = new Random();
				int c = r.nextInt(100);
				if (c < 25) {
					e.setDamage(dano += 3.0D);
					p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 10);
				}
			}
		  	
			for (PotionEffect effect : p.getActivePotionEffects())
				 if (effect.getType().equals(PotionEffectType.WEAKNESS))
					 dano -= (effect.getAmplifier() + 1);
			
			makeCombatLog(d, p);
		}
		e.setDamage(dano);
	}
	
	public void makeCombatLog(Player bateu, Player hitado) {
		if (Combat.combatLogs.containsKey(hitado.getUniqueId())) {
			Combat.combatLogs.get(hitado.getUniqueId()).setLastHit(bateu.getUniqueId());
		} else {
			Combat.combatLogs.put(hitado.getUniqueId(), new CombatLog(hitado.getUniqueId(), bateu.getUniqueId()));
		}
	}
	
	@EventHandler
	public void fixDamages(EntityDamageEvent e) {
		if (e.isCancelled())
			return;
		
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.LAVA) {
		        e.setDamage(e.getDamage() - 2.0D);
			}
		}
	}
	
	public static void setup() {
		damageMaterial.put(Material.DIAMOND_SWORD, 5.0D);
		damageMaterial.put(Material.IRON_SWORD, 4.0D);
		damageMaterial.put(Material.STONE_SWORD, 4.0D);
		damageMaterial.put(Material.WOOD_SWORD, 2.0D);
		damageMaterial.put(Material.GOLD_SWORD, 3.0D);

		damageMaterial.put(Material.DIAMOND_AXE, 4.0D);
		damageMaterial.put(Material.IRON_AXE, 3.0D);
		damageMaterial.put(Material.STONE_AXE, 3.0D);
		damageMaterial.put(Material.WOOD_AXE, 3.0D);
		damageMaterial.put(Material.GOLD_AXE, 3.0D);

		damageMaterial.put(Material.DIAMOND_PICKAXE, 3.0D);
		damageMaterial.put(Material.IRON_PICKAXE, 2.0D);
		damageMaterial.put(Material.STONE_PICKAXE, 2.0D);
		damageMaterial.put(Material.WOOD_PICKAXE, 2.0D);
		damageMaterial.put(Material.GOLD_PICKAXE, 2.0D);

		for (Material materiais : Material.values()) {
			 if (damageMaterial.containsKey(materiais))
				 continue;
			 damageMaterial.put(materiais, 1.0D);
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean isCrital(Player p) {
	    return (p.getFallDistance() > 0.0F) && (!p.isOnGround()) && (new Random().nextInt(100) < 10) && (!p.hasPotionEffect(PotionEffectType.BLINDNESS));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PlayerDeathEvent event) {
		Player entity = (Player) event.getEntity();
		
		if (entity.getKiller() == null)
			return;
		Player killer = (Player) event.getEntity().getKiller();
		
		if (Combat.inCombat(killer.getUniqueId())) {
			Combat.combatLogs.remove(killer.getUniqueId());
			return;
		}
		
		if (Combat.inCombat(entity.getUniqueId())) {
			Combat.combatLogs.remove(entity.getUniqueId());
			return;
		}
	}
	
	@EventHandler
	public void asd(PlayerQuitEvent event) {
		Player entity = event.getPlayer();
		if (Combat.combatLogs.containsKey(entity.getUniqueId())) {
			Player killer = Bukkit.getPlayer(Combat.combatLogs.get(entity.getUniqueId()).getLastHit());
			
			Combat.combatLogs.remove(killer.getUniqueId());
			Combat.combatLogs.remove(entity.getUniqueId());
			
		}
	}
}