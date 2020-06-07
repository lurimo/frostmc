package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Forger implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (FrostHG.state==State.INICIANDO)
			return;
		ItemStack currentItem = event.getCurrentItem();
		Player player = (Player)event.getWhoClicked();
		if ((currentItem != null) && (currentItem.getType() != Material.AIR) && (FrostHG.getManager().getKitAPI().hasKit(player, Kits.FORGER))) {
			int coalAmount = 0;
			Inventory inv = event.getView().getBottomInventory();
			for (ItemStack item : inv.getContents()) {
				if ((item != null) && (item.getType() == Material.COAL)) {
					coalAmount += item.getAmount();
				}
			}
			if (coalAmount == 0) {
				return;
			}
			int hadCoal = coalAmount;
			if (currentItem.getType() == Material.COAL) {
				for (int slot = 0; slot < inv.getSize(); slot++) {
					ItemStack item = inv.getItem(slot);
					if ((item != null) && (item.getType().name().contains("ORE"))) {
						while ((item.getAmount() > 0) && (coalAmount > 0) && ((item.getType() == Material.IRON_ORE) || (item.getType() == Material.GOLD_ORE))) {
							item.setAmount(item.getAmount() - 1);
							coalAmount--;
							if (item.getType() == Material.IRON_ORE) {
								player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
							} else if (item.getType() == Material.GOLD_ORE) {
								player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
							}
						}
						if (item.getAmount() == 0) {
							inv.setItem(slot, new ItemStack(0));
						}
					}
				}
			} else if (currentItem.getType().name().contains("ORE")) {
				while ((currentItem.getAmount() > 0) && (coalAmount > 0) && ((currentItem.getType() == Material.IRON_ORE) || (currentItem.getType() == Material.GOLD_ORE))) {
					currentItem.setAmount(currentItem.getAmount() - 1);
					coalAmount--;
					if (currentItem.getType() == Material.IRON_ORE) {
						player.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
					} else if (currentItem.getType() == Material.GOLD_ORE) {
						player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
					}
				}
				if (currentItem.getAmount() == 0) {
					event.setCurrentItem(new ItemStack(0));
				}
			}
			if (coalAmount != hadCoal) {
				for (int slot = 0; slot < inv.getSize(); slot++) {
					ItemStack item = inv.getItem(slot);
					if ((item != null) && (item.getType() == Material.COAL)) {
						while ((coalAmount < hadCoal) && (item.getAmount() > 0)) {
							item.setAmount(item.getAmount() - 1);
							coalAmount++;
						}
						if (item.getAmount() == 0) {
							inv.setItem(slot, new ItemStack(0));
						}
					}
				}
			}
		}
	}

}
