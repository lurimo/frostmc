package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Demoman implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Block b = event.getClickedBlock();
		if ((event.getAction() == Action.PHYSICAL) && (b != null) && (b.hasMetadata("Placer")) && (b.getType() == Material.STONE_PLATE) && (b.getRelative(BlockFace.DOWN).getType() == Material.GRAVEL)) {
			b.removeMetadata("Placer", FrostHG.getInstance());
			b.setType(Material.AIR);
			b.getWorld().createExplosion(b.getLocation().clone().add(0.5D, 0.5D, 0.5D), 4.0F);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if ((event.getBlock().getType() == Material.STONE_PLATE) && (FrostHG.getManager().getKitAPI().hasKit(event.getPlayer(), Kits.DEMOMAN))) {
			event.getBlock().removeMetadata("Placer", FrostHG.getInstance());
			event.getBlock().setMetadata("Placer", new FixedMetadataValue(FrostHG.getInstance(), event.getPlayer().getName()));
		}
	}

}
