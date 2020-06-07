package br.com.frostmc.gladiator.battle1v1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.util.protection.Protection;

public class GladiatorListener implements Listener {
	
	private GladiatorManager manager;
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void playerInteractEvent(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Player target = (Player) event.getRightClicked();
		if(!(event.getRightClicked() instanceof Player)) {
			return;
		}
		if(!manager.inWarp.contains(player.getUniqueId())) {
			return;
		}
		if(player.getItemInHand().equals(manager.blaze_rod)) {
			if(manager.cooldown.contains(player.getUniqueId())) {
				player.sendMessage("§c§l1V1 §fAguarde um pouco para desafiar novamente!");
				return;
			}
			manager.invite.add(target.getUniqueId());
			manager.invited.add(player.getUniqueId());
			if(manager.invite.contains(player.getUniqueId()) && manager.invited.contains(target.getUniqueId())) {
				//manager.startGladiator(player, target);
				manager.startMatch(player, target);
				GladiatorGenerator.spawnArena(player, target, new Location(target.getWorld(), 0, 0, 0), Material.GLASS);
				manager.invite.remove(target.getUniqueId());
				manager.invited.remove(player.getUniqueId());
				Protection.removeProtection(player);
				Protection.removeProtection(target);
				target.sendMessage("§a§l1V1 §f" + player.getName() + " aceitou seu desafio!");
				player.sendMessage("§a§l1V1 §fVocê aceitou o desafio do player " + target.getName());
				return;
			}
			player.sendMessage("§a§l1V1 §fVocê desafiou o player " + target.getName());
			target.sendMessage("§a§l1V1 §fVocê foi desafiado por " + player.getName());
			manager.cooldown.add(player.getUniqueId());
			new BukkitRunnable() {
				public void run() {
					if (manager.cooldown.contains(player.getUniqueId())) {
						manager.cooldown.remove(player.getUniqueId());
						manager.invite.remove(target.getUniqueId());
						manager.invited.remove(player.getUniqueId());
						cancel();
					}
				}
			}.runTaskLater(FrostGladiator.getInstance(), 250L);
			return;
		}
	}
	
	public static Integer task = null;
	
	@SuppressWarnings({ "static-access" })
	@EventHandler
	public void playerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(!manager.inWarp.contains(player.getUniqueId())) {
			return;
		}
		if(player.getItemInHand().equals(manager.gray_color)) {
			if(manager.fast.contains(player)) {
				return;
			}
			event.setCancelled(true);
			manager.fast.add(player);
			player.getInventory().remove(manager.gray_color);
			player.getInventory().setItem(5, manager.green_color);
			player.updateInventory();
			player.sendMessage("§a§l1V1 §fVocê entrou na fila do 1v1 rápido!");
			if(manager.fast.size() == 2) {
				Player player_1 = manager.fast.get(0);
				Player player_2 = manager.fast.get(1);
				if(player_1 == null) {
					manager.fast.remove(player_1);
				}
				if(player_2 == null) {
					manager.fast.remove(player_2);
				}
				if(player_1 != null && player_2 != null) {
					//manager.startGladiator(player_1, player_2);
					manager.startMatch(player_1, player_2);
					GladiatorGenerator.spawnArena(player_1, player_2, new Location(player_2.getWorld(), 0, 0, 0), Material.GLASS);
					manager.fast.remove(player_1);
					manager.fast.remove(player_2);
					player_1.sendMessage("§a§l1V1 §fVocê irá batalhar contra " + player_2.getName());
					player_2.sendMessage("§a§l1V1 §fVocê irá batalhar contra " + player_1.getName());
				}
			} else {
				task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostGladiator.getInstance(), new Runnable() {
					public void run() {
						if(manager.fast.contains(player) && player.getInventory().getItem(5).equals(manager.green_color)) {
							manager.fast.remove(player);
							player.getInventory().remove(manager.green_color);
							player.getInventory().setItem(5, manager.gray_color);
							player.updateInventory();
							player.sendMessage("§c§l1V1 §fNão foi encontrado ninguém para batalhar com você!");
							cancelTask();
						}
					}
				}, 200L, 200L));
			}
			return;
		} else if(player.getItemInHand().equals(manager.green_color)) {
			if(!manager.fast.contains(player)) {
				return;
			}
			manager.fast.remove(player);
			cancelTask();
			player.getInventory().remove(manager.green_color);
			player.getInventory().setItem(5, manager.gray_color);
			player.updateInventory();
			player.sendMessage("§c§l1V1 §fVocê saiu da fila do 1v1 rápido!");
			return;
		}
	}
	
	public static void cancelTask() {
		if (task == null)
			return;
		
        Bukkit.getServer().getScheduler().cancelTask(task.intValue());
        task = null;
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void asd(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if(manager.inWarp.contains(e.getPlayer().getUniqueId())) {
			if (manager.fighting.containsKey(p.getUniqueId())) {
				p.sendMessage("§c§l1V1 §fComandos na warp 1v1 não são permitidos em batalha.");
				e.setCancelled(true);
				return;
			}
		}
	}
}
