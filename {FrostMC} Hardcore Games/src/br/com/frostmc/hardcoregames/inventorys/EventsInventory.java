package br.com.frostmc.hardcoregames.inventorys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
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
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class EventsInventory implements Listener {
	
	public Kits getDiary(Player player) {
		List<Kits> kits = new ArrayList<Kits>();
		for (Kits kit : Kits.values()) {
			if (!kit.equals(Kits.NENHUM) || !FrostHG.getManager().getKitAPI().hasKitPermission(player, kit)) {
				kits.add(kit);
			}
		}
		if (kits.size() > 0) {
			return (Kits) kits.get(new Random().nextInt(kits.size()));
		}
		return null;
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType().equals(Material.CHEST)) {
			if (player.getItemInHand().getItemMeta().hasDisplayName()) {
				if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.kitSelector)) {
					event.setCancelled(true);
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						KitsInventory.abrirMenu(player);
					}
				}
			}
		}
		if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId())) {
			if (player.getItemInHand().getType().equals(Material.BOOK)) {
				if (player.getItemInHand().getItemMeta().hasDisplayName()) {
					if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.spectatorSelector)) {
						event.setCancelled(true);
						if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							SpectatorInventory.openSpectateGUI(player, 6, new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
						}
					}
				}
			}
		}
		if (player.getItemInHand().getType().equals((Object) Material.ENDER_CHEST) && player.getItemInHand().getItemMeta().hasDisplayName() & player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ManagerInventory.diarySelector)) {
			event.setCancelled(true);
			player.updateInventory();
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (BukkitMain.getPermissions().isFrost(player)) {
					player.sendMessage("§c§lDIARY §fVocê já póssui todos os kits.");
					return;
				}
				AccountBukkit account = new AccountBukkit(player);
				if (!account.getDiaryHG().exists()) {
					Kits kit = getDiary(player);
					account.getDiaryHG().setKit(kit.name());
					account.getDiaryHG().setExpire(Longs.fromString("1d"));
					account.getDiaryHG().create();
					player.sendMessage("§a§lDIARY §fO seu kit diario é §a" + kit.getName());
					return;
				} else {
					player.sendMessage("§c§lDIARY §fVocê já utilizou o kit diario!");
					player.sendMessage("§c§lDIARY §fO seu kit diario é §a" + account.getDiaryHG().getKit() + " §fele irá expirar em " + Longs.messageSmall(account.getDiaryHG().getExpire()));
					return;
				}
			}
			return;
		}	
	}
	
	@EventHandler
	public void inventoryclick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		Inventory clicked = event.getClickedInventory();
		ItemStack currentItem = event.getCurrentItem();
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
				if (currentItem.getItemMeta().getDisplayName().equals("§7Loja de kits")) {
					StoreKitsInventory.abrirMenu(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aPróxima página")) {
					KitsInventory.proximaPagina(player, clicked);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§cPágina anterior")) {
					KitsInventory.paginaAnterior(player, clicked);
					return;
				} else if ((!currentItem.getItemMeta().getDisplayName().equals("§cNenhuma página")) && (!currentItem.getItemMeta().getDisplayName().equals("§aPróxima página")) && (!currentItem.getItemMeta().getDisplayName().equals("§cPágina anterior")) && (!event.getCurrentItem().getType().equals(Material.NETHER_STAR)) && (!event.getCurrentItem().getType().equals(Material.ARROW)) && (!event.getCurrentItem().getType().equals(Material.THIN_GLASS))) {
					String kit = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().trim().replace("§a", ""));
					player.chat("/kit " + kit.toLowerCase());
					player.closeInventory();
				}
			} else if (inventory.getTitle().equals(StoreKitsInventory.titleStoreKits)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§7Seus kits")) {
					KitsInventory.abrirMenu(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aPróxima página")) {
					StoreKitsInventory.proximaPagina(player, clicked);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§cPágina anterior")) {
					StoreKitsInventory.paginaAnterior(player, clicked);
					return;
				} else if ((!currentItem.getItemMeta().getDisplayName().equals("§cNenhuma página")) && (!currentItem.getItemMeta().getDisplayName().equals("§aPróxima página")) && (!currentItem.getItemMeta().getDisplayName().equals("§cPágina anterior")) && (!event.getCurrentItem().getType().equals(Material.NETHER_STAR)) && (!event.getCurrentItem().getType().equals(Material.REDSTONE)) && (!event.getCurrentItem().getType().equals(Material.ARROW)) && (!event.getCurrentItem().getType().equals(Material.THIN_GLASS))) {
					AccountBukkit account = new AccountBukkit(player);
					String kit = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().trim().replace("§c§l", ""));
	   				if ((!FrostHG.getManager().getKitAPI().hasKitPermission(player, Kits.valueOf(kit.toUpperCase())))) {
	   					if (account.getStatsGlobal().getFichas() < Kits.valueOf(kit.toUpperCase()).getPrice()) {
	   						player.sendMessage("§c§lSTORE §fVocê não póssui fichas suficientes para realizar essa compra!");
	   						return;
	   					}
	   					account.getStatsGlobal().removeFichas(Kits.valueOf(kit.toUpperCase()).getPrice());
	   					account.getStatsGlobal().save();

	   					account.getBuy_kit_hg().setKit(kit.toUpperCase());
	   					account.getBuy_kit_hg().create();
	   					
	   					player.sendMessage("§a§lSTORE §fParabéns você comprou o kit §a" + kit + " §fdivirta-se jogando com ele.");
	   				}
					player.closeInventory();
				}
			}
			if (inventory.getTitle().equals(SpectatorInventory.tittle)) {
				event.setCancelled(true);
				if (!FrostHG.getManager().getEspectadores().contains(player.getUniqueId()))
					return;
				if (currentItem.getItemMeta().getDisplayName().contains("Proxima")) {
					SpectatorInventory.nextPage(player);
				}
				if (currentItem.getItemMeta().getDisplayName().contains("Anterior")) {
					SpectatorInventory.previusPage(player);
				}
				if ((event.getRawSlot() >= 0) && (event.getRawSlot() < 53)) {
					String name = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
					Player p2 = Bukkit.getPlayerExact(name);
					if (p2 == null) {
						player.sendMessage("§c§lERRO §fO jogador '" + name + "' esta offline.");
						return;
					}
					player.teleport(p2);
					player.sendMessage("§3§lTELEPORT §fVocê se teleportou para §e" + p2.getDisplayName());
					SpectatorInventory.spectate.clear();
					SpectatorInventory.page = 1;
					SpectatorInventory.pagina = 1;
				}
			}
		}
	}

}
