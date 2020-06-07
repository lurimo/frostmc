package br.com.frostmc.pvp.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.pvp.inventory.player.KitsInventory;
import br.com.frostmc.pvp.inventory.player.StoreKitsInventory;
import br.com.frostmc.pvp.inventory.player.WarpInventory;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.hability.Kits;
import br.com.frostmc.pvp.util.warp.WarpManager;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class EventsInventory implements Listener {

	public Kits getDiary(Player player) {
		List<Kits> kits = new ArrayList<Kits>();
		for (Kits kit : Kits.values()) {
			if (!kit.equals(Kits.NENHUM) && !KitAPI.hasKitPermission(player, kit)) {
				kits.add(kit);
			}
		}
		if (kits.size() > 0) {
			return (Kits) kits.get(new Random().nextInt(kits.size()));
		}
		return null;
	}
	
	@EventHandler 
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getItem() == null)
			return;
		if (player.getItemInHand() == null)
			return;
		if (WarpsAPI.isInWarp(player, Warps.SPAWN)) {
			if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.kitSelector)) {
				event.setCancelled(true);
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					KitsInventory.abrirMenu(player);
				}
				return;
			}
			if (player.getItemInHand().getType().equals((Object) Material.PAPER) && player.getItemInHand().getItemMeta().hasDisplayName() & player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.warpSelector)) {
				event.setCancelled(true);
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					WarpInventory.giveInventory(player);
				}
				return;
			}
			if (player.getItemInHand().getType().equals((Object) Material.ENDER_CHEST) && player.getItemInHand().getItemMeta().hasDisplayName() & player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.diarySelector)) {
				event.setCancelled(true);
				player.updateInventory();
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (BukkitMain.getPermissions().isFrost(player)) {
						player.sendMessage("�c�lERRO �7Voc� j� p�ssui todos os kits dispon�veis atualmente. Mais kits est�o em desenvolvimento.");
						return;
					}
					AccountBukkit account = new AccountBukkit(player);
					if (!account.getDiaryPvP().exists()) {
						Kits kit = getDiary(player);
						account.getDiaryPvP().setKit(kit.name());
						account.getDiaryPvP().setExpire(Longs.fromString("1d"));
						account.getDiaryPvP().create();
						player.sendMessage("");
						player.sendMessage("�a�lKIT �7O seu kit diario � �a" + kit.getName());
						player.sendMessage("");
						return;
					} else {
						player.sendMessage("�c�lERRO �7Voc� j� utilizou seu kit di�rio.");
						player.sendMessage("�a�lKIT �7O seu kit diario � �a" + account.getDiaryPvP().getKit() + " �7ele ir� expirar em " + Longs.messageSmall(account.getDiaryPvP().getExpire()));
						return;
					}
				}
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickead(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		ItemStack currentItem = event.getCurrentItem();
		Inventory clicked = event.getClickedInventory();
		Entity whoClicked = event.getWhoClicked();
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) whoClicked;
			if (currentItem == null) {
				return;
			}
			if (!currentItem.hasItemMeta()) {
				return;
			}
			if (inventory.getTitle().equals(KitsInventory.titleSeusKits)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("�7Loja de kits")) {
					StoreKitsInventory.abrirMenu(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("�aPr�xima p�gina")) {
					KitsInventory.proximaPagina(player, clicked);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("�cP�gina anterior")) {
					KitsInventory.paginaAnterior(player, clicked);
					return;
				} else if ((!currentItem.getItemMeta().getDisplayName().equals("�cNenhuma p�gina")) && (!currentItem.getItemMeta().getDisplayName().equals("�aPr�xima p�gina")) && (!currentItem.getItemMeta().getDisplayName().equals("�cP�gina anterior")) && (!event.getCurrentItem().getType().equals(Material.NETHER_STAR)) && (!event.getCurrentItem().getType().equals(Material.ARROW)) && (!event.getCurrentItem().getType().equals(Material.THIN_GLASS))) {
					String kit = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().trim().replace("�a�l", ""));
					player.chat("/kit " + kit.toLowerCase());
					player.closeInventory();
				}
			} else if (inventory.getTitle().equals(StoreKitsInventory.titleStoreKits)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("�7Seus kits")) {
					KitsInventory.abrirMenu(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("�aPr�xima p�gina")) {
					StoreKitsInventory.proximaPagina(player, clicked);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("�cP�gina anterior")) {
					StoreKitsInventory.paginaAnterior(player, clicked);
					return;
				} else if ((!currentItem.getItemMeta().getDisplayName().equals("�cNenhuma p�gina")) && (!currentItem.getItemMeta().getDisplayName().equals("�aPr�xima p�gina")) && (!currentItem.getItemMeta().getDisplayName().equals("�cP�gina anterior")) && (!event.getCurrentItem().getType().equals(Material.NETHER_STAR)) && (!event.getCurrentItem().getType().equals(Material.REDSTONE)) && (!event.getCurrentItem().getType().equals(Material.ARROW)) && (!event.getCurrentItem().getType().equals(Material.THIN_GLASS))) {
					AccountBukkit account = new AccountBukkit(player);
					String kit = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().trim().replace("�c�l", ""));
	   				if ((!KitAPI.hasKitPermission(player, Kits.valueOf(kit.toUpperCase())))) {
	   					if (account.getStatsGlobal().getFichas() < Kits.valueOf(kit.toUpperCase()).getPrice()) {
	   						player.sendMessage("�c�lERRO �fVoc� n�o possui fichas suficientes para realizar essa compra.");
	   						return;
	   					}
	   					account.getStatsGlobal().removeFichas(Kits.valueOf(kit.toUpperCase()).getPrice());
	   					account.getStatsGlobal().save();

	   					account.getBuy_kit_pvp().setKit(kit.toUpperCase());
	   					account.getBuy_kit_pvp().create();
	   					
	   					player.sendMessage("�a�lSTORE �7Parab�ns voc� comprou o kit �a" + kit + " �7divirta-se jogando com ele.");
	   				}
					player.closeInventory();
				}
			} else if (inventory.getTitle().equals(WarpInventory.title)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("�7Kits")) {
					KitsInventory.abrirMenu(player);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("�aWarps")) {
					event.setCancelled(true);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("�f ")) {
					event.setCancelled(true);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("�fWarp �e�lFPS")) {
					player.closeInventory();
					WarpManager.send(player, Warps.FPS);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("�fWarp �a�l1V1")) {
					player.closeInventory();
					WarpManager.send(player, Warps.ONEVSONE);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("�fWarp �c�lLava")) {
					player.closeInventory();
					WarpManager.send(player, Warps.LAVACHALLENGE);
					return;
				}
			}
		}
	}
	
}
