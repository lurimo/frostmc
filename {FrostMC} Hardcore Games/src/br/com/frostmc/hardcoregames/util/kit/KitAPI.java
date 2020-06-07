package br.com.frostmc.hardcoregames.util.kit;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.hability.Madman;

public class KitAPI {

	public static HashMap<UUID, String> Kit = new HashMap<>();

	public void setKit(Player player, Kits kit) {
		Kit.put(player.getUniqueId(), kit.getName());
		player.sendMessage("§b§lKIT §fO kit §a" + getKit(player) +" §ffoi selecionado.");
		if (kit != null) {
			check(player);
		}
	}
	
	public static String getKit(Player player) {
		return Kit.get(player.getUniqueId());
	}
	
	public void check(Player p) {
		if (hasKit(p, Kits.MADMAN)) {
			if (!Madman.madmans.contains(p.getUniqueId())) {
				Madman.madmans.add(p.getUniqueId());
			}
		}
	}
	
	public boolean hasKit(Player player, Kits kit) {
		Kits playerkit = (Kits) Kits.valueOf(Kit.get(player.getUniqueId()).toUpperCase());
		return kit.equals(playerkit);
	}
	
	public boolean isItem(Player player, ItemStack item, String name) {
		if (player.getInventory().getItemInHand().equals(item))
			if (player.getInventory().getItemInHand().hasItemMeta())
				if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(name))
					return true;
		return false;
	}
	
	public void kitSet(Player p, Kits kit) {
		if (FrostHG.state == State.INICIANDO) {
			setKit(p, kit);
		} else if (FrostHG.state == State.INVENCIVEL) {
			if (BukkitMain.getPermissions().isLight(p)) {
				if (!isUsingKits(p, kit)) {
					setKit(p, kit);
					giveKit(p, kit);
				} else {
					p.sendMessage("§b§lKIT §fVocê não pode selecionar mais nenhum kit.");
				}
			}
		} else if (FrostHG.state == State.JOGO) {
			if (!hasKit(p, Kits.NENHUM)) {
				p.sendMessage("§b§lKIT §fVocê não pode selecionar mais nenhum kit.");
			}
		}
	}

	public boolean isUsingKits(Player player, Kits kits) {
		return Kit.get(player.getUniqueId()).equals(kits.getName());
	}
	
	public void removeKit(Player player) {
		Kit.put(player.getUniqueId(), Kits.NENHUM.getName());
	}
	
	public void giveKit(Player player, Kits kits) {
		PlayerInventory inv = player.getInventory();
		
		ItemStack miner = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta rminer = miner.getItemMeta();
		rminer.setDisplayName("§bKit Miner");
		miner.setItemMeta(rminer);
		miner.addEnchantment(Enchantment.DIG_SPEED, 1);
		miner.addEnchantment(Enchantment.DURABILITY, 3);

		if (!kits.equals(Kits.NENHUM) && !kits.equals(Kits.ACHILLES) && !kits.equals(Kits.BOXER) && !kits.equals(Kits.CULTIVATOR) && !kits.equals(Kits.FORGER) && !kits.equals(Kits.HULK) && !kits.equals(Kits.MADMAN) && !kits.equals(Kits.MAGMA) && !kits.equals(Kits.NINJA) && !kits.equals(Kits.REAPER) && !kits.equals(Kits.SNAIL) && !kits.equals(Kits.STOMPER) && !kits.equals(Kits.CANNIBAL) && !kits.equals(Kits.SURPRISE) && !kits.equals(Kits.TANK) && !kits.equals(Kits.TOWER) && !kits.equals(Kits.TURTLE) && !kits.equals(Kits.VIKING) && !kits.equals(Kits.VIPER) && !kits.equals(Kits.WORM)) {
			if(kits.equals(Kits.MINER)) {
				if (!inv.contains(miner)) {
					inv.addItem(miner);
				}
			} else if(kits.equals(Kits.URGAL)) {
				if (!inv.contains(new ItemBuilder().setMaterial(Material.POTION).setNome("§bKit Urgal").setDurabilidade(8201).setQuantia(3).finalizar())) {
					inv.addItem(new ItemBuilder().setMaterial(Material.POTION).setNome("§bKit Urgal").setDurabilidade(8201).setQuantia(3).finalizar());
				}
			} else if(kits.equals(Kits.SCOUT)) {
				if (!inv.contains(new ItemBuilder().setNome("§bKit Scout").setMaterial(Material.POTION).setDurabilidade(16418).setQuantia(2).finalizar())) {
					inv.addItem(new ItemBuilder().setNome("§bKit Scout").setMaterial(Material.POTION).setDurabilidade(16418).setQuantia(2).finalizar());
				}
			} else if(kits.equals(Kits.LAUNCHER)) {
				if (!inv.contains(new ItemBuilder().setNome("§bKit Launcher").setMaterial(Material.SPONGE).setQuantia(16).finalizar())) {
					inv.addItem(new ItemBuilder().setNome("§bKit Launcher").setMaterial(Material.SPONGE).setQuantia(16).finalizar());
				}
			} else if(kits.equals(Kits.DEMOMAN)) {
				if (!inv.contains(new ItemBuilder().setMaterial(Material.STONE_PLATE).setQuantia(5).finalizar())) {
					inv.addItem(new ItemBuilder().setMaterial(Material.STONE_PLATE).setQuantia(5).finalizar());
				}
				if (!inv.contains(new ItemBuilder().setMaterial(Material.GRAVEL).setQuantia(5).finalizar())) {
					inv.addItem(new ItemBuilder().setMaterial(Material.GRAVEL).setQuantia(5).finalizar());
				}
			} else if (kits.equals(Kits.valueOf(KitAPI.getKit(player).toUpperCase()))) {
				if (!inv.contains(new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(kits.getMaterial()).finalizar())) {
					inv.addItem(new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(kits.getMaterial()).finalizar());
				}
			}
		}
		if (kits != null) {
			check(player);
		}
		player.updateInventory();
	}
	
	public static boolean hasKitPermission(Player player, Kits kit) {
		AccountBukkit account = new AccountBukkit(player);
		if (player.hasPermission(kit.getTagPermission().getPermissionGroup()) || account.getBuy_kit_pvp().checkKit(kit.name())  || account.getDiaryHG().checkKit(kit.name())) {
			return true;
		} else {
			return false;
		}
	}
	
}
