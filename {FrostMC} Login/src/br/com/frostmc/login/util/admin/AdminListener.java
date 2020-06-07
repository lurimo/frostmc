package br.com.frostmc.login.util.admin;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.fake.FakeManager;

public class AdminListener implements Listener {

	public ArrayList<UUID> delayVanish = new ArrayList<>();
	
	@EventHandler
	private void onCancelBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (AdminManager.isAdmin(player) && !BukkitMain.getPermissions().isTrial(player))
			event.setCancelled(true);
	}

	@EventHandler
	private void onInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player) {
			Player player = event.getPlayer();

			if (AdminManager.isAdmin(player)) {
				Player clicked = (Player) event.getRightClicked();
				ItemStack item = player.getInventory().getItemInHand();

				if (item.getType().equals(Material.AIR)) {
					player.performCommand("invsee " + clicked.getName());
				}
			}
		}
	}

	@EventHandler
	private void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getPlayer().getInventory().getItemInHand();
		if (item == null)
			return;
		if (item.getType().equals(Material.MAGMA_CREAM)) {
			if (AdminManager.isAdmin(player)) {
				if (delayVanish.contains(player.getUniqueId())) {
					player.sendMessage("§8[§c§lERRO§8] §fAguarde para sair/entrar do admin.");
					return;
				}
				delayVanish.add(player.getUniqueId());
				Vanish.makeVisible(player);
				Vanish.updateVanished();
				player.sendMessage("§8[§c§lADMIN§8] §fAgora você está visível para §7§lMEMBROS");
				new BukkitRunnable() {
					public void run() {
						delayVanish.remove(player.getUniqueId());
						Vanish.makeVanished(player);
						Vanish.updateVanished();
						player.sendMessage("§8[§c§lADMIN§8] §fAgora você está invisível para " + AdminManager.getInvisible(player) + "");
					}
				}.runTaskLater(BukkitMain.getInstance(), 10L);
			}
			return;
		}
	}
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof Player)) {
			return;
		}
		Player player = event.getPlayer();
		Player target = (Player) event.getRightClicked();
		int ping = ((CraftPlayer) target).getHandle().ping;
		
		ItemStack item = event.getPlayer().getInventory().getItemInHand();
		if (item.getType().equals(Material.NAME_TAG)) {
			if (AdminManager.isAdmin(player)) {
				player.sendMessage("");
				player.sendMessage("§aInformações do player: " + target.getName());
				player.sendMessage("§aGrupo: " + BukkitMain.getPermissionManager().getPlayerGroup(target).fullName());
				player.sendMessage("");
				player.sendMessage("§aPing: §f" + ping);
				player.sendMessage("§aGamemode: §f" + target.getGameMode());
				player.sendMessage("§aFake: §f" + (FakeManager.fake.containsKey(target.getUniqueId()) ? "Ativado" : "Desativado"));
				player.sendMessage("");
			}
		}
	}
}
