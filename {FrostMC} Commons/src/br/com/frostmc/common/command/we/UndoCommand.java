package br.com.frostmc.common.command.we;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.command.BaseCommand;
import br.com.frostmc.common.util.worldedit.BO2.FutureBlock;

public class UndoCommand extends BaseCommand {

	public UndoCommand() {
		super("undo", "Undo your shit.");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {

			Player player = getPlayer(commandSender);
			
			if (!BukkitMain.getPermissions().isYoutuberPlus(player)) {
				return true;
			}

			if (args.length != 0) {
				player.sendMessage("§3§lWORLDEDIT §fUtilize o comando: /undo");
				return false;
			}

			if (!BukkitMain.getWorldEdit().hasUndoPosition(player.getUniqueId())) {
				player.sendMessage("§c§lWORLDEDIT §fVocê não tem nada para desfazer.");
				return false;
			}

			Location firstLocation = BukkitMain.getWorldEdit().getUndoLocation(player.getUniqueId())[0];
			Location secondLocation = BukkitMain.getWorldEdit().getUndoLocation(player.getUniqueId())[1];

			for (Location location : BukkitMain.getWorldEdit().getLocationsFromTwoPoints(firstLocation, secondLocation)) {
				FutureBlock real = BukkitMain.getWorldEdit().getCacheBlock(location);
				BukkitMain.getBO2().setBlockFast(location, Material.getMaterial(real.getId()), real.getData());
				BukkitMain.getWorldEdit().removeCacheBlock(location);
			}
			player.sendMessage("§a§lWORLDEDIT §fVocê desfez o set das localizações (" + firstLocation.getBlockX() + "," + firstLocation.getBlockY() + "," + firstLocation.getBlockZ() + ") até (" + secondLocation.getBlockX() + "," + secondLocation.getBlockY() + "," + secondLocation.getBlockZ() + ")§f");
		} else {
			return true;
		}
		return true;
	}
}
