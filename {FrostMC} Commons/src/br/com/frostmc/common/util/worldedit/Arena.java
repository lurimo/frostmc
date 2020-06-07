package br.com.frostmc.common.util.worldedit;

import org.bukkit.Location;
import org.bukkit.Material;

@SuppressWarnings("deprecation")
public class Arena {

	private BO2 bo2;

	public Arena(BO2 bo2) {
		this.bo2 = bo2;
	}

	public void normal(Location location, int size, int height, int blockId) {
		Material type = Material.getMaterial(blockId);
		for (int y = 0; y <= height; y++) {
			if (y == 0) {
				for (int x = -size; x <= size; x++) {
					for (int z = -size; z <= size; z++) {
						Location loc = location.clone().add(x, y, z);
						bo2.setBlockFast(location.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), type.getId(), (byte) 0);
					}
				}
			} else {
				for (int x = -size; x <= size; x++) {
					Location loc = location.clone().add(x, y, size);
					Location loc2 = location.clone().add(x, y, -size);
					bo2.setBlockFast(location.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), type.getId(), (byte) 0);
					bo2.setBlockFast(location.getWorld(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), type.getId(), (byte) 0);
				}
				for (int z = -size; z <= size; z++) {
					Location loc = location.clone().add(size, y, z);
					Location loc2 = location.clone().add(-size, y, z);
					bo2.setBlockFast(location.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), type.getId(), (byte) 0);
					bo2.setBlockFast(location.getWorld(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), type.getId(), (byte) 0);
				}
			}
		}
	}

	public void circle(Location location, int range, int height, Material type) {
		int x1 = location.getBlockX(), y1 = location.getBlockY(), z1 = location.getBlockZ();

		for (int y = 0; y < height; y++) {
			if (y == 0) {
				for (int r = 0; r <= range; r++) {
					for (int i = 0; i < 360; i++) {
						Location loc = location.clone().add(x1 + (Math.cos(Math.toRadians(i)) * r), y1 + y, z1 + (Math.sin(Math.toRadians(i)) * r));
						bo2.setBlockFast(location.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), type.getId(), (byte) 0);
					}
				}
			} else {
				for (int i = 0; i < 360; i++) {
					Location loc = location.clone().add(x1 + (Math.cos(Math.toRadians(i)) * range), y1 + y, z1 + (Math.sin(Math.toRadians(i)) * range));
					bo2.setBlockFast(location.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), type.getId(), (byte) 0);
				}
			}
		}

	}
}
