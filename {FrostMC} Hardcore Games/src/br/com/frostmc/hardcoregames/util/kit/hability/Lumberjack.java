package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Lumberjack implements Listener {

	@EventHandler
	private void onPlayerLumberJack(BlockBreakEvent e) {
		if (FrostHG.state == State.INICIANDO) {
			return;
		}
		Player player = e.getPlayer();
		Block block = e.getBlock();
		ItemStack item = player.getItemInHand();
		if ((FrostHG.getManager().getKitAPI().hasKit(player, Kits.LUMBERJACK)) && (item.getType() == Material.WOOD_AXE)) {
			World w = player.getWorld();
			Double y = Double.valueOf(block.getLocation().getY() + 1.0D);
			Location wood_location = new Location(w, block.getX(), y.doubleValue(), block.getZ());
			while (wood_location.getBlock().getType() == Material.LOG) {
				wood_location.getBlock().breakNaturally();
				y = Double.valueOf(y.doubleValue() + 1.0D);
				wood_location.setY(y.doubleValue());
			}
			while (wood_location.getBlock().getType() == Material.LOG_2) {
				wood_location.getBlock().breakNaturally();
				y = Double.valueOf(y.doubleValue() + 1.0D);
				wood_location.setY(y.doubleValue());
			}
		}
	}

}
