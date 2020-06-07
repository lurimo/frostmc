package br.com.frostmc.common.util.worldedit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.worldedit.BO2.FutureBlock;

public class WorldEdit {
	
	protected BukkitMain bukkitMain;

	public WorldEdit(BukkitMain bukkitMain) {
		this.bukkitMain = bukkitMain;
	}

	private HashMap<UUID, Location> firstPositions = new HashMap<>();
	private HashMap<UUID, Location> secondPositions = new HashMap<>();

	private HashMap<Location, FutureBlock> cacheBlocks = new HashMap<>();
	private HashMap<UUID, Location[]> undoLocations = new HashMap<>();

	public List<Location> getLocationsFromTwoPoints(Location location1, Location location2) {
		List<Location> locations = new ArrayList<>();

		int topBlockX = (location1.getBlockX() < location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int bottomBlockX = (location1.getBlockX() > location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());

		int topBlockY = (location1.getBlockY() < location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int bottomBlockY = (location1.getBlockY() > location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());

		int topBlockZ = (location1.getBlockZ() < location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		int bottomBlockZ = (location1.getBlockZ() > location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int z = bottomBlockZ; z <= topBlockZ; z++) {
				for (int y = bottomBlockY; y <= topBlockY; y++) {
					locations.add(new Location(location1.getWorld(), x, y, z));
				}
			}
		}

		return locations;
	}

	public List<Block> getblocksFromTwoPoints(Location location1, Location location2) {
		List<Block> blocks = new ArrayList<>();
		for (Location loc : getLocationsFromTwoPoints(location1, location2))
			blocks.add(loc.getBlock());
		return blocks;
	}

	public HashMap<UUID, Location> getFirstPositions() {
		return firstPositions;
	}

	public HashMap<UUID, Location> getSecondPositions() {
		return secondPositions;
	}

	public HashMap<Location, FutureBlock> getCacheBlocks() {
		return cacheBlocks;
	}

	public HashMap<UUID, Location[]> getUndoLocations() {
		return undoLocations;
	}

	public Location getFistPosition(UUID uuid) {
		return getFirstPositions().get(uuid);
	}

	public Location getSecondPosition(UUID uuid) {
		return getSecondPositions().get(uuid);
	}

	public FutureBlock getCacheBlock(Location location) {
		return getCacheBlocks().get(location);
	}

	public Location[] getUndoLocation(UUID uuid) {
		return getUndoLocations().get(uuid);
	}

	public boolean hasFirstPosition(UUID uuid) {
		return getFirstPositions().containsKey(uuid);
	}

	public boolean hasSecondPosition(UUID uuid) {
		return getSecondPositions().containsKey(uuid);
	}

	public boolean hasCacheBlock(Location location) {
		return getCacheBlocks().containsKey(location);
	}

	public boolean hasUndoPosition(UUID uuid) {
		return getUndoLocations().containsKey(uuid);
	}

	public void setFirstPosition(UUID uuid, Location location) {
		if (hasFirstPosition(uuid))
			getFirstPositions().replace(uuid, location);
		else
			getFirstPositions().put(uuid, location);
	}

	public void setSecondPosition(UUID uuid, Location location) {
		if (hasSecondPosition(uuid))
			getSecondPositions().replace(uuid, location);
		else
			getSecondPositions().put(uuid, location);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void setCacheBlock(Location location, Block block) {
		getCacheBlocks().put(location, bukkitMain.getBO2().new FutureBlock(location, block.getType().getId(), block.getData()));
	}

	public void setUndoPosition(UUID uuid, Location[] locations) {
		getUndoLocations().put(uuid, locations);
	}

	public void removeCacheBlock(Location location) {
		getCacheBlocks().remove(location);
	}

}
