package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class Miner implements Listener {

	@EventHandler
	public void asd(BlockPlaceEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (!FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.MINER)) {
			return;
		}
		if (e.getBlock().getType() != Material.STONE_PICKAXE && e.getBlock().getType() != Material.IRON_PICKAXE) {
			return;
		}
	}
	
	@EventHandler
	private void asd(BlockBreakEvent e) {
		if (FrostHG.state==State.INICIANDO)
			return;
		if (!FrostHG.getManager().getKitAPI().hasKit(e.getPlayer(), Kits.MINER)) {
			return;
		}
		if ((e.getBlock().getType().equals(Material.IRON_ORE))) {
			for (int x = -3; x < 3; x++) {
				for (int z = -3; z < 3; z++) {
					for (int y = -3; y < 3; y++) {
						Block b = e.getBlock().getLocation().clone().add(x, y, z).getBlock();
						if (b.getType().equals(Material.IRON_ORE)) {
							b.breakNaturally();
							if (new Random().nextInt(100) <= 10)
								e.getPlayer().giveExp(1);
						}
					}
				}
			}
		} else if ((e.getBlock().getType().equals(Material.COAL_ORE))) {
			for (int x = -3; x < 3; x++) {
				for (int z = -3; z < 3; z++) {
					for (int y = -3; y < 3; y++) {
						Block b = e.getBlock().getLocation().clone().add(x, y, z).getBlock();
						if (b.getType().equals(Material.COAL_ORE)) {
							b.breakNaturally();
							if (new Random().nextInt(100) <= 10)
								e.getPlayer().giveExp(1);
						}
					}
				}
			}
		} else if ((e.getBlock().getType().equals(Material.GOLD_ORE))) {
			for (int x = -3; x < 3; x++) {
				for (int z = -3; z < 3; z++) {
					for (int y = -3; y < 3; y++) {
						Block b = e.getBlock().getLocation().clone().add(x, y, z).getBlock();
						if (b.getType().equals(Material.GOLD_ORE)) {
							b.breakNaturally();
							if (new Random().nextInt(100) <= 10)
								e.getPlayer().giveExp(1);
						}
					}
				}
			}
		} else if ((e.getBlock().getType().equals(Material.DIAMOND_ORE))) {
			for (int x = -3; x < 3; x++) {
				for (int z = -3; z < 3; z++) {
					for (int y = -3; y < 3; y++) {
						Block b = e.getBlock().getLocation().clone().add(x, y, z).getBlock();
						if (b.getType().equals(Material.DIAMOND_ORE)) {
							b.breakNaturally();
							if (new Random().nextInt(100) <= 10)
								e.getPlayer().giveExp(1);
						}
					}
				}
			}
		}
	}

}
