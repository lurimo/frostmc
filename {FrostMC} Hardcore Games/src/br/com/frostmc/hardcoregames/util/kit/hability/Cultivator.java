package br.com.frostmc.hardcoregames.util.kit.hability;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.CocoaPlant;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Cultivator implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void place(BlockPlaceEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		Player player = e.getPlayer();
		if (e.getBlock().getType() == Material.SAPLING && FrostHG.getManager().getKitAPI().hasKit(player, Kits.CULTIVATOR)) {
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().generateTree(e.getBlock().getLocation(), TreeType.TREE);
		} else if (e.getBlock().getType() == Material.CROPS && FrostHG.getManager().getKitAPI().hasKit(player, Kits.CULTIVATOR)) {
			e.getBlock().setData((byte) 7);
		} else if (e.getBlock().getType() == Material.COCOA && FrostHG.getManager().getKitAPI().hasKit(player, Kits.CULTIVATOR)) {
			CocoaPlant bean = (CocoaPlant)e.getBlock().getState().getData();
            if (bean.getSize() != CocoaPlant.CocoaPlantSize.LARGE) {
                bean.setSize(CocoaPlant.CocoaPlantSize.LARGE);
                e.getBlock().setData(bean.getData());
            }
		}
	}
	
}
