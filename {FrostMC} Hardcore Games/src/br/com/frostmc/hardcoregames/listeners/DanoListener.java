package br.com.frostmc.hardcoregames.listeners;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.combatlog.Combat;
import br.com.frostmc.hardcoregames.util.combatlog.CombatLog;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import br.com.frostmc.hardcoregames.util.kit.hability.Madman;

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
			if ((FrostHG.getManager().getKitAPI().hasKit(p, Kits.TURTLE)) && (p.isSneaking())) {
				e.setDamage(2.0D);
				makeCombatLog(d, p);
				return;
			}
		  	
		  	if (FrostHG.getManager().getKitAPI().hasKit(p, Kits.BOXER)) {
		  		dano-= 1.0D;
		  	}
		  	
			for (PotionEffect effect : p.getActivePotionEffects())
				 if (effect.getType().equals(PotionEffectType.WEAKNESS))
					 dano -= (effect.getAmplifier() + 1);
			
			if (Madman.efeitoMadman.containsKey(p.getUniqueId())) {
				e.setDamage(dano + ((dano / 100.0D) * Madman.efeitoMadman.get(p.getUniqueId())));
			}
			
			makeCombatLog(d, p);
		}
		e.setDamage(dano);
	}
	
	void makeCombatLog(Player bateu, Player hitado) {
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
		        e.setDamage(e.getDamage() - 1.0D);
			}
		}
	}
	
	public static void setup() {
		damageMaterial.put(Material.DIAMOND_SWORD, 4.0D);
		damageMaterial.put(Material.IRON_SWORD, 3.0D);
		damageMaterial.put(Material.STONE_SWORD, 3.0D);
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
}
