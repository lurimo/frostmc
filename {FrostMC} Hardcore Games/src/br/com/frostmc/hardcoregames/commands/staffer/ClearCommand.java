package br.com.frostmc.hardcoregames.commands.staffer;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.BaseCommand;

public class ClearCommand extends BaseCommand {

	public ClearCommand() {
		super("clear", "clear the inventory/drops/mobs", Arrays.asList("limpar"));
	}

	public enum ClearModes {
		INVENTORY, DROPS, MOBS;
	}
	
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				player.sendMessage(Strings.getPermission());
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("");
				player.sendMessage("§b§lLIMPAR-COMANDOS");
				player.sendMessage("");
				player.sendMessage("§f/clear inventory (player) §8- §7Limpe seu inventario ou de um jogador.");
				player.sendMessage("§f/clear drops §8- §7Limpe todos os drops.");
				player.sendMessage("§f/clear mobs §8- §7Mate todos os mobs.");
				player.sendMessage("");
				return true;
			}
			if (args.length == 1) {
				ClearModes clearModes = ClearModes.INVENTORY;
				try {
					clearModes = ClearModes.valueOf(args[0].toUpperCase());
				} catch (Exception exception) {
					player.sendMessage("§4§lERRO §fNão foi póssivel encontrar o modo citado.");
					return true;
				}
				
				if (clearModes.equals(ClearModes.INVENTORY)) {
					player.getInventory().clear();
					player.sendMessage("§b§lCLEAR §fO seu inventario foi limpo.");
					return true;
				} else if (clearModes.equals(ClearModes.DROPS)) {
					int drops = 0;
					for (Player todos : FrostHG.getManager().getOnlinePlayers()) {
						for(Entity entity : todos.getWorld().getEntities()) {
							if(entity instanceof Item) {
								drops++;
								entity.remove();
							}
						}
					}
					player.sendMessage("§b§lCLEAR §fForam removidos: §a" + drops + " drops.");
					return true;
				} else if (clearModes.equals(ClearModes.MOBS)) {
					int mobs = 0;
					World world = Bukkit.getWorld("world");
					for (LivingEntity mob : world.getEntitiesByClass(LivingEntity.class)) {
			             for (Entity entity : mob.getLocation().getChunk().getEntities()) {
			                  if (((entity instanceof LivingEntity)) && (!(entity instanceof Player))) {
			                      mobs++;  
			                	  entity.remove();
			                  }
			             }
					}
					player.sendMessage("§b§lCLEAR §fForam removidos: §a" + mobs + " mobs.");
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
