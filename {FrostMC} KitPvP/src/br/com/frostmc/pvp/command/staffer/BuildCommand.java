package br.com.frostmc.pvp.command.staffer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.fake.FakeManager;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.pvp.command.BaseCommand;

public class BuildCommand extends BaseCommand implements Listener {

	public BuildCommand() {
		super("build", "Change the option of build in the server.", Arrays.asList("construir"));
	}
	
	public static HashMap<UUID, BuildModes> buildModes = new HashMap<UUID, BuildModes>();
	
	public enum BuildModes {
		ON, OFF;
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
				player.sendMessage("�8[�6�lBUILD�8] �fUtilize o comando: �7/build (on/off)");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("on")) {
				if (buildModes.get(player.getUniqueId()).equals(BuildModes.ON)) {
					player.sendMessage("�8[�6�lBUILD�8] �fO modo build j� est� ativado.");
					return true;
				}
				buildModes.put(player.getUniqueId(), BuildModes.ON);
				sendWarning(player.getName() + " ativou o modo BUILD.");
				player.sendMessage("�8[�6�lBUILD�8] �fO seu modo build foi habilitado.");
				return true;
			} else if (args[0].equalsIgnoreCase("off")) {
				if (buildModes.get(player.getUniqueId()).equals(BuildModes.OFF)) {
					player.sendMessage("�8[�6�lBUILD�8] �fO modo build j� est� desativado.");
					return true;
				}
				buildModes.put(player.getUniqueId(), BuildModes.OFF);
				sendWarning(player.getName() + " desativou o modo build");
				player.sendMessage("�8[�6�lBUILD�8] �fO seu modo build foi desabilitado.");
				return true;
			}
		} else {
			return true;
		}
		return true;
	}

	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Material bucket = event.getBucket();
		if (bucket.toString().contains("LAVA") || bucket.toString().contains("WATER")) {
			if (buildModes.get(player.getUniqueId()).equals(BuildModes.OFF) || FakeManager.fake.containsKey(player.getUniqueId())) {
				event.setCancelled(true);
				return; 
			} else {
				event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (buildModes.get(player.getUniqueId()).equals(BuildModes.OFF) || FakeManager.fake.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			return;
		} else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (buildModes.get(player.getUniqueId()).equals(BuildModes.OFF) || FakeManager.fake.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			return;
		} else {
			event.setCancelled(false);
		}
	}

}