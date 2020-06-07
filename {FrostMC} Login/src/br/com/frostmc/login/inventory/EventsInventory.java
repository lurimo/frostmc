package br.com.frostmc.login.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.TeleportServer;
import br.com.frostmc.core.util.enums.AuthType;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.login.FrostLogin;
import br.com.frostmc.login.inventory.player.ConexõesInventory;

public class EventsInventory implements Listener {

	@EventHandler
	public void asd(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(event.getRightClicked() instanceof Zombie) {
			event.setCancelled(true);
			Zombie zombie = (Zombie)event.getRightClicked();
			if(zombie.getCustomName().equalsIgnoreCase("§1")) {
				if (FrostLogin.checkToLogin.containsKey(player.getUniqueId())) {
					if (FrostLogin.checkToLogin.get(player.getUniqueId()).equals(AuthType.LOGGED)) {
						TeleportServer.connectPlayer(player, ServersType.LOBBY);
					}
				}
			}
		}
	}
	
	@EventHandler 
	public void asd(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType().equals((Object) Material.COMPASS) && player.getItemInHand().getItemMeta().hasDisplayName() & player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3Conectar - §7Clique para abrir")) {
			event.setCancelled(true);
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ConexõesInventory.giveInventory(player);
			}
			return;
		}
	}
	
	@EventHandler
	public void asd(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
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
			if (inventory.getTitle().equals(ConexõesInventory.title)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§aLobby")) {
					player.closeInventory();
					TeleportServer.connectPlayer(player, ServersType.LOBBY);
					return;
				}
			}
		}
	}
	
}
