package br.com.frostmc.common.command.we;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;

@SuppressWarnings("deprecation")
public class SetCommand extends BaseCommand {

	public SetCommand() {
		super("set", "Fill blocks in determinated locations.");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {

			Player player = getPlayer(commandSender);

			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				return true;
			}

			if (args.length != 1) {
				player.sendMessage("§3§lWORLDEDIT §fUtilize o comando: /set (idblock)");
				return false;
			}

			if (!isInteger(args[0])) {
				player.sendMessage("§c§lWORLDEDIT §fO bloco precisa ser um número.");
				return false;
			}

			if (!BukkitMain.getWorldEdit().hasFirstPosition(player.getUniqueId())) {
				player.sendMessage("§c§lWORLDEDIT §fA primeira localização não foi setada.");
				return false;
			}

			if (!BukkitMain.getWorldEdit().hasSecondPosition(player.getUniqueId())) {
				player.sendMessage("§c§lWORLDEDIT §fA segunda localização não foi setada.");
				return false;
			}

			Integer blockId = Integer.valueOf(args[0]);
			if (Material.getMaterial(blockId) == null) {
				player.sendMessage("§c§lWORLDEDIT §fO bloco com o id " + blockId + " não foi encontrado!");
				return false;
			}

			Location firstLocation = BukkitMain.getWorldEdit().getFistPosition(player.getUniqueId());
			Location secondLocation = BukkitMain.getWorldEdit().getSecondPosition(player.getUniqueId());
			Material blockMaterial = Material.getMaterial(blockId);

			BukkitMain.getWorldEdit().setUndoPosition(player.getUniqueId(), new Location[] { firstLocation, secondLocation });
			for (Block block : BukkitMain.getWorldEdit().getblocksFromTwoPoints(firstLocation, secondLocation)) {
				BukkitMain.getWorldEdit().setCacheBlock(block.getLocation(), block);
				BukkitMain.getBO2().setBlockFast(block.getLocation(), blockMaterial, (byte) 0);
			}
			player.sendMessage("§a§lWORLDEDIT §fVocê setou o bloco " + blockMaterial.name().toLowerCase() + " de (" + firstLocation.getBlockX() + ", " + firstLocation.getBlockY() + ", " + firstLocation.getBlockZ() + ") até (" + secondLocation.getBlockX() + "," + secondLocation.getBlockY() + "," + secondLocation.getBlockZ() + ") " + BukkitMain.getWorldEdit().getblocksFromTwoPoints(firstLocation, secondLocation).size() + " §fblocos.");
		} else {
			return true;
		}
		return true;
	}

}
