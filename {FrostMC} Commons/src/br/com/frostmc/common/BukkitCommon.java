package br.com.frostmc.common;

import org.bukkit.*;

public abstract class BukkitCommon {

	private BukkitMain main;

	public BukkitCommon(BukkitMain main) {
		this.main = main;
	}

	public abstract void onEnable();

	public abstract void onDisable();

	public Server getServer() {
		return this.main.getServer();
	}

	public BukkitMain getPlugin() {
		return this.main;
	}
}
