package br.com.frostmc.hardcoregames.inventorys;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.frostmc.hardcoregames.FrostHG;

public class SpectatorInventory implements Listener {
	
	public static int pagina = 1;
	public static int page = 1;
	public static int paginaNumbers;
	public static Inventory spectate;
	public static String tittle = "§8§nJogadores vivos";
	
	public static void nextPage(Player p) {
		if (paginaNumbers >= pagina + 1) {
			pagina += 1;
			openSpectateGUI(p, 6, new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
		}
	}

	public static void previusPage(Player p) {
		if (pagina > 0) {
			openSpectateGUI(p, 6, new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
		}
	}

	private static void setPages(Inventory inv) {
		if (pagina > 1) {
			inv.setItem(53, getGreen("§cPagina anterior"));
		}
		if (pagina != paginaNumbers) {
			inv.setItem(53, getGreen("§aProxima pagina"));
		}
	}
	
	private static ItemStack getGreen(String name) {
		ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static void openSpectateGUI(Player p, int rows, ItemStack item) {
		ItemStack is = item;
		SkullMeta im = (SkullMeta) is.getItemMeta();
		spectate = Bukkit.createInventory(p, rows * 9, tittle);
		int slot = 0;

		int size = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			size++;
			paginaNumbers = (size / 54 + 1);
			setPages(spectate);
			if ((page < pagina) && (slot == 53)) {
				slot = 0;
				page += 1;
				for (int j = 0; j < 53; j++) {
					spectate.setItem(j, null);
				}
			}
			if (slot >= 53) {
				break;
			}
			if ((player != p) && (FrostHG.getManager().getJogadores().contains(player.getUniqueId()))) {
				im.setDisplayName("§a" + player.getName());
				ArrayList<String> lore = new ArrayList<>();
				lore.add("");
				lore.add("§fKills: §a" + FrostHG.getManager().kills.get(player.getUniqueId()));
				lore.add("§fKit: §a" + FrostHG.getManager().getKitAPI().getKit(player));
				lore.add("");
				lore.add("§aClique para teleportar!");
				lore.add("");
				im.setLore(lore);
				im.setOwner(player.getName());
				is.setItemMeta(im);
				spectate.setItem(slot, is);
				slot++;
				lore.clear();
			}
		}
		p.openInventory(spectate);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (!e.getInventory().getTitle().equalsIgnoreCase(tittle)) {
			return;
		}
		if (pagina > 1) {
			pagina = 1;
			page = 1;
		}
	}
	
}
