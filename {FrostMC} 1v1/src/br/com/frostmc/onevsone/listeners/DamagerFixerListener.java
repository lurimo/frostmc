package br.com.frostmc.onevsone.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class DamagerFixerListener implements Listener {
	
	@EventHandler
	public void asd(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
		        
				final ItemStack sword = p.getItemInHand();
				double damage = e.getDamage()-1.0;
				final double danoEspada = this.getDamage(sword.getType());
				boolean isMore = false;
				if (damage > 1.0) {
					isMore = true;
				}
				if (!sword.getEnchantments().isEmpty()) {
					if (sword.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) && this.isArthropod(e.getEntityType())) {
						damage -= 1.15 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
						damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
					}
					if (sword.containsEnchantment(Enchantment.DAMAGE_UNDEAD) && this.isUndead(e.getEntityType())) {
						damage -= 1.15 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
						damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
					}
					if (sword.containsEnchantment(Enchantment.DAMAGE_ALL)) {
						damage -= 1.15 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
						damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
					}
				}
				if (this.isCrital(p)) {
					damage -= danoEspada / 1.50;
					++damage;
				}
				if (isMore) {
					damage -= 1.00;
				}
				if (damage > 8.5) {
					damage = 7.5;
				}
				
				
				Material type = sword.getType();
				if (type != Material.STONE_SWORD && type != Material.WOOD_SWORD &&type != Material.DIAMOND_SWORD) {
					damage = 0.5D;
				}
				e.setDamage(damage);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean isCrital(final Player p) {
		return p.getFallDistance() > 0.0f && !p.isOnGround() && !p.hasPotionEffect(PotionEffectType.BLINDNESS);
	}

	private boolean isArthropod(final EntityType type) {
		switch (type) {
		case CAVE_SPIDER: {
			return true;
		}
		case SPIDER: {
			return true;
		}
		case SILVERFISH: {
			return true;
		}
		default: {
			return false;
		}
		}
	}

	private boolean isUndead(final EntityType type) {
		switch (type) {
		case SKELETON: {
			return true;
		}
		case ZOMBIE: {
			return true;
		}
		case WITHER_SKULL: {
			return true;
		}
		case PIG_ZOMBIE: {
			return true;
		}
		default: {
			return false;
		}
		}
	}

	private double getDamage(final Material type) {
		double damage = 1.0;
		if (type.toString().contains("DIAMOND_")) {
			damage = 5.0;
		} else if (type.toString().contains("IRON_")) {
			damage = 4.0;
		} else if (type.toString().contains("STONE_")) {
			damage = 3.0;
		} else if (type.toString().contains("WOOD_")) {
			damage = 2.5;
		} else if (type.toString().contains("GOLD_")) {
			damage = 3.0;
		}
		if (!type.toString().contains("_SWORD")) {
			--damage;
			if (!type.toString().contains("_AXE")) {
				--damage;
				if (!type.toString().contains("_PICKAXE")) {
					--damage;
					if (!type.toString().contains("_SPADE")) {
						damage = 1.0;
					}
				}
			}
		}
		return damage;
	}

}
