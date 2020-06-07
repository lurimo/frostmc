package br.com.frostmc.hardcoregames.commands.moderators;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;
import br.com.frostmc.hardcoregames.timer.feast.Feast;
import br.com.frostmc.hardcoregames.util.Schematic;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import br.com.frostmc.hardcoregames.util.kit.hability.Gladiator;

public class BuildCommand extends BaseCommand implements Listener {

	public BuildCommand() {
		super("build", "Change the option of build in the server.", Arrays.asList("construir"));
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!BukkitMain.getPermissions().isAdmin(commandSender)) {
				player.sendMessage(Strings.getPermission());
				return false;
			}
			
			if (args.length == 0) {
				player.sendMessage("§3§lBUILD §fUtilize o comando: /build (on/off)");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("on")) {
				if (ServerOptions.BUILD.isActive()) {
					player.sendMessage("§b§lBUILD §fO modo build já está §a§lATIVADO");
					return true;
				}
				ServerOptions.BUILD.setActive(true);
				sendWarning(player.getName() + " ativou o modo build");
				Bukkit.broadcastMessage("§b§lBUILD §fConstrução de blocos foi §a§lATIVADO");
				return true;
			} else if (args[0].equalsIgnoreCase("off")) {
				if (!ServerOptions.BUILD.isActive()) {
					player.sendMessage("§b§lBUILD §fO modo build já está §c§lDESATIVADO");
					return true;
				}
				ServerOptions.BUILD.setActive(false);
				sendWarning(player.getName() + " desativou o modo build");
				Bukkit.broadcastMessage("§b§lBUILD §fConstrução de blocos foi §c§lDESATIVADO");
				return true;
			}
		} else {
			return true;
		}
		return true;
	}

	@EventHandler()
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Material bucket = event.getBucket();
		if (bucket.toString().contains("LAVA")) {
			if (!ServerOptions.BUILD.isActive()) {
				if (!BukkitMain.getPermissions().isBuilder(player)) {
					event.setCancelled(true);
				}
			}
		}

		if (bucket.toString().contains("WATER")) {
			if (!ServerOptions.BUILD.isActive()) {
				if (!BukkitMain.getPermissions().isBuilder(player)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		if (e.isCancelled())
			return;
		if (AdminManager.isAdmin(e.getPlayer()))
			return;
		if (FrostHG.state != State.INICIANDO) {
			if (!ServerOptions.BUILD.isActive()) {
				if (!BukkitMain.getPermissions().isYoutuberPlus(e.getPlayer())) {
					e.setCancelled(true);
				}
			}
			
			if (e.getBlock().getLocation().getBlockY() > 128) {
				if (!Gladiator.gladArena.containsKey(e.getPlayer().getUniqueId())) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("§c§lERRO §fA altura máxima permitida para construção é 128 blocos.");
					return;
				}
			}
			if (FrostHG.getManager().getBorderBlock().contains(e.getBlock())) {
				e.setCancelled(true);
				return;
			}
			if ((FrostHG.state==State.JOGO) && (Schematic.bFeast.contains(e.getBlock()))) {
				if (!Feast.bausSpawned) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("§6§lTORNEIO §fVocê não pode construir no feast até que os baús sejam spawnados.");
					return;
				}
			}
			if ((FrostHG.state==State.JOGO) && (Schematic.Circulo.contains(e.getBlock()))) {
				if (!Feast.bausSpawned) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("§6§lTORNEIO §fVocê não pode construir no feast até que os baús sejam spawnados.");
				}
			}
			Block b = e.getBlock();
			Player p = e.getPlayer();
			if ((FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) || (AdminManager.isAdmin(p)))
				return;
			if (b.getType().equals(Material.STONE)) {
				if (!FrostHG.getManager().isFull(p.getInventory())) {
					e.setCancelled(true);
					e.getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.COBBLESTONE));
				} else {
					for (ItemStack itens : p.getInventory().getContents()) 
						 if ((itens.getType().equals(b.getType())) && (itens.getAmount() != 64)) {
							  e.setCancelled(true);
							  e.getBlock().setType(Material.AIR);
							  p.getInventory().addItem(new ItemStack(Material.COBBLESTONE));
							  break;
						 }
				}
			} else if (b.getType().equals(Material.BROWN_MUSHROOM)) {
				if (!FrostHG.getManager().isFull(p.getInventory())) {
					e.setCancelled(true);
					e.getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.BROWN_MUSHROOM));
				} else {
					for (ItemStack itens : p.getInventory().getContents()) 
						 if ((itens.getType().equals(b.getType())) && (itens.getAmount() != 64)) {
							  e.setCancelled(true);
							  e.getBlock().setType(Material.AIR);
							  p.getInventory().addItem(new ItemStack(Material.BROWN_MUSHROOM));
							  break;
						 }
				}
			} else if (b.getType().equals(Material.RED_MUSHROOM)) {
				if (!FrostHG.getManager().isFull(p.getInventory())) {
					e.setCancelled(true);
					e.getBlock().setType(Material.AIR);
					p.getInventory().addItem(new ItemStack(Material.RED_MUSHROOM));
				} else {
					for (ItemStack itens : p.getInventory().getContents()) 
					 if ((itens.getType().equals(b.getType())) && (itens.getAmount() != 64)) {
						  e.setCancelled(true);
						  e.getBlock().setType(Material.AIR);
						  p.getInventory().addItem(new ItemStack(Material.RED_MUSHROOM));
						break;
					}
				}
			}
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlaceBlock(BlockPlaceEvent e) {
		if (AdminManager.isAdmin(e.getPlayer()))
			return;
		if (FrostHG.state != State.INICIANDO) {
			if (!ServerOptions.BUILD.isActive()) {
				if (!BukkitMain.getPermissions().isYoutuberPlus(e.getPlayer())) {
					e.setCancelled(true);
				}
			}
			if (e.getBlock().getLocation().getBlockY() > 128) {
				if (!Gladiator.gladArena.containsKey(e.getPlayer().getUniqueId())) {
					e.setCancelled(true);
					e.getPlayer().sendMessage("§c§lERRO §fA altura máxima permitida para construção é 128 blocos.");
					return;
				}
			}
			if (FrostHG.getManager().getBorderBlock().contains(e.getBlock())) {
				e.setCancelled(true);
				return;
			}
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

}
